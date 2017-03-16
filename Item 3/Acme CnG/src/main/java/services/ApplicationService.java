
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
		final Application result = new Application();
		result.setTrip(this.tripService.findOne(tripId));
		result.setCustomer(this.customerService.findCustomerByPrincipal());
		result.setStatus("PENDING");
		//TODO: Hacer save.
		return result;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {

		// He cambiado las condiciones porque estaban al revés.
		Assert.notNull(applicationId, "No puedes encontrar una solicitud sin ID");
		Assert.isTrue(applicationId > 0, "La Id no es válida");

		final Application result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Application save(final Application application) {
		Application result;

		Assert.notNull(application, "La soicitud no puede ser nula");
		Assert.isTrue(application.getStatus().equals("PENDING") || application.getStatus().equals("ACCEPTED") || application.getStatus().equals("DENIED"), "La solicitud debe tener un estado válido");

		result = this.applicationRepository.save(application);
		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application, "La solicitud no puede ser nula");
		Assert.isTrue(this.applicationRepository.exists(application.getId()), "La solicitud debe estar en la base de datos antes de borrarla");

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
