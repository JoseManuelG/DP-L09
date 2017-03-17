
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

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
		Application result = this.save(aux);
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

}
