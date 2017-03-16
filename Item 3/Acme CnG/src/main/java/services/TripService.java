
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TripRepository;
import domain.Customer;
import domain.Trip;

@Service
@Transactional
public class TripService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private TripRepository	tripRepository;

	//Supported Services--------------------------------------------------------------------

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private Validator		validator;


	//Simple CRUD methods-------------------------------------------------------------------
	public Trip create() {
		final Trip result = new Trip();
		result.setBanned(false);
		result.setCustomer(this.customerService.findCustomerByPrincipal());

		return result;
	}

	public Trip create(final String type) {
		final Trip result = new Trip();
		result.setType(type);
		result.setBanned(false);
		result.setCustomer(this.customerService.findCustomerByPrincipal());
		return result;
	}

	public Collection<Trip> findAll() {
		return this.tripRepository.findAll();
	}

	public Trip findOne(final Integer tripId) {

		Assert.notNull(tripId, "No Puedes Encontrar un viaje sin ID");
		Assert.isTrue(tripId > 0, "La Id no es valida");

		final Trip result = this.tripRepository.findOne(tripId);

		return result;
	}

	public Trip save(final Trip trip) {
		Trip result;

		Assert.notNull(trip, "El viaje no puede ser nulo");
		Assert.hasText(trip.getTitle(), "El viaje debe tener un título");
		Assert.hasText(trip.getDescription(), "El viaje debe tener una descripción");
		Assert.notNull(trip.getDepartureTime(), "El viaje debe tener un momento de salida");
		Assert.hasText(trip.getOrigin(), "El viaje debe tener un lugar de salida");
		Assert.hasText(trip.getDestination(), "El viaje debe tener un lugar de llegada");
		Assert.isTrue((trip.getOriginLat() == null && trip.getOriginLon() == null) || ((!(trip.getOriginLat() == null) && !(trip.getOriginLon() == null))), "Si se definen las coordenadas del lugar de salida, se deben definir ambas");
		Assert.isTrue((trip.getDestinationLat() == null && trip.getDestinationLon() == null) || ((!(trip.getDestinationLat() == null) && !(trip.getDestinationLon() == null))), "Si se definen las coordenadas del lugar de llegada, se deben definir ambas");
		Assert.notNull(trip.getType(), "El viaje debe ser de algún tipo");

		result = this.tripRepository.save(trip);
		return result;
	}

	public void delete(final Trip trip) {
		Assert.notNull(trip, "El viaje no puede ser nulo");
		Assert.isTrue(this.tripRepository.exists(trip.getId()), "El viaje debe estar en base de datos antes de borrarlo");

		this.tripRepository.delete(trip);
	}

	//Other Business methods-------------------------------------------------------------------

	public Trip reconstruct(final Trip trip, final BindingResult bindingResult) {
		Trip result, original;

		if (trip.getId() == 0)
			result = this.create();
		else {
			original = this.tripRepository.findOne(trip.getId());
			result = new Trip();
			result.setBanned(original.getBanned());
		}
		result.setCustomer(trip.getCustomer());
		result.setDepartureTime(trip.getDepartureTime());
		result.setDescription(trip.getDescription());
		result.setDestinationLat(trip.getDestinationLat());
		result.setDestinationLon(trip.getDestinationLon());
		result.setOrigin(trip.getOrigin());
		result.setOriginLat(trip.getOriginLat());
		result.setOriginLon(trip.getOriginLon());
		result.setTitle(trip.getTitle());
		result.setType(trip.getType());
		this.validator.validate(result, bindingResult);
		return result;

	}

	public Trip createOffer() {
		Trip result;

		final String type = "OFFER";

		result = this.create(type);
		return result;
	}

	public Trip createRequest() {
		Trip result;

		final String type = "REQUEST";

		result = this.create(type);
		return result;
	}

	public Collection<Trip> findByKeyWord(final String keyword) {
		Collection<Trip> result;

		result = this.tripRepository.findByKeyWord(keyword);

		return result;

	}

	public void banTrip(final int tripId) {
		final Trip aux = this.tripRepository.findOne(tripId);
		aux.setBanned(true);
		this.save(aux);
	}

	public Collection<Trip> findAllOffers() {
		Collection<Trip> result;
		result = this.tripRepository.findAllOffers();
		return result;
	}

	public Collection<Trip> findAllRequests() {
		Collection<Trip> result;
		result = this.tripRepository.findAllRequests();
		return result;
	}

	public Collection<Trip> findAllOffersByPrincipal() {
		Collection<Trip> result;
		final Customer customer = this.customerService.findCustomerByPrincipal();
		result = this.tripRepository.findAllOffersByPrincipalId(customer.getId());
		return result;
	}

	public Collection<Trip> findAllRequestsByPrincipal() {
		Collection<Trip> result;
		final Customer customer = this.customerService.findCustomerByPrincipal();
		result = this.tripRepository.findAllRequestsByPrincipalId(customer.getId());
		return result;
	}

}
