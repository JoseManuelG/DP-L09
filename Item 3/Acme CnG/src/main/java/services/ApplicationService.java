
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Customer;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	//Supported Services--------------------------------------------------------------------

	@Autowired
	private TripService				tripService;

	@Autowired
	private CustomerService			customerService;


	//Simple CRUD methods-------------------------------------------------------------------
	public Application create(final int tripId) {
		final Application aux = new Application();
		aux.setTrip(this.tripService.findOne(tripId));
		aux.setCustomer(this.customerService.findCustomerByPrincipal());
		aux.setStatus("PENDING");
		final Application result = this.save(aux);
		return result;
	}
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {

		// He cambiado las condiciones porque estaban al revés.
		Assert.notNull(applicationId, "application.error.id.null");
		Assert.isTrue(applicationId > 0, "application.error.id.invalid");

		final Application result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Application save(final Application application) {
		Application result;

		Assert.notNull(application, "application.error.null");
		Assert.isTrue(application.getStatus().equals("PENDING") || application.getStatus().equals("ACCEPTED") || application.getStatus().equals("DENIED"), "application.error.status.invalid");

		result = this.applicationRepository.save(application);
		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application, "application.error.null");
		Assert.isTrue(this.applicationRepository.exists(application.getId()), "application.error.id.notsaved");

		this.applicationRepository.delete(application);
	}

	public Long count() {
		return this.applicationRepository.count();
	}

	//Other Business methods-------------------------------------------------------------------

	public Application acceptApply(final int applyId) {
		Application aux, result;

		aux = this.applicationRepository.findOne(applyId);

		aux.setStatus("ACCEPTED");
		result = this.save(aux);
		return result;

	}

	public Application denyApply(final int applyId) {
		Application aux, result;

		aux = this.applicationRepository.findOne(applyId);

		aux.setStatus("DENIED");
		result = this.save(aux);
		return result;

	}

	public Collection<Application> findAllApplicationsByTrip(final int tripId) {
		Collection<Application> result;
		result = this.applicationRepository.findAllApplicationsByTripId(tripId);
		return result;
	}

	//03 - Average number of applications per offer or request.
	public Double avgApplicationVsCustomerAndRequest() {
		Double result;
		Long res1, res2;
		res1 = this.count();
		res2 = this.tripService.count();

		if (res1 != null && res2 != null && res2 > 0)
			result = 1.0 * res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//04 - The customer who has more applications accepted.
	public Customer customerWithMoreApplicationsAccepted() {
		List<Customer> customers;
		final Customer result;
		customers = this.applicationRepository.customerWithMoreApplicationsAccepted();
		result = customers.iterator().next();
		return result;
	}
	//05 - The customer who has more applications denied
	public Customer customerWithMoreApplicationsDenied() {
		List<Customer> customers;
		final Customer result;
		customers = this.applicationRepository.customerWithMoreApplicationsDenied();
		result = customers.iterator().next();
		return result;
	}
	public void deleteCustomer(final Customer customer) {
		final Collection<Application> applications = new ArrayList<Application>();
		final Collection<Application> applications2 = new ArrayList<Application>();
		applications.addAll(this.applicationRepository.findAllApplicationByCustomer(customer.getId()));
		for (final Application application : applications) {
			application.setCustomer(null);
			applications2.add(application);
		}
		this.applicationRepository.save(applications2);
	}

}
