
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TripRepository;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
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
	private ActorService	actorService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private Validator		validator;


	//Simple CRUD methods-------------------------------------------------------------------
	public Trip create() {
		Customer customer;
		final Trip result = new Trip();
		result.setBanned(false);
		customer = this.customerService.findCustomerByPrincipal();
		Assert.notNull(customer);
		result.setCustomer(customer);

		return result;
	}

	public Trip create(final String type) {
		Trip result;

		result = this.create();
		result.setType(type);

		return result;
	}

	@SuppressWarnings("static-access")
	public Trip findOne(final Integer tripId) {

		Assert.notNull(tripId, "trip.error.id.null");
		Assert.isTrue(tripId > 0, "trip.error.id.invalid");

		Trip result = null;

		final Trip aux = this.tripRepository.findOne(tripId);
		final String loggedAuthority = this.loginService.getPrincipal().getAuthorities().iterator().next().getAuthority();
		if ((aux.getBanned() == true && loggedAuthority == "ADMINISTRATOR"))
			result = aux;
		else if (aux.getBanned() == true && this.customerService.findCustomerByPrincipal().equals(aux.getCustomer()))
			result = aux;
		else if (aux.getBanned() == false)
			result = aux;

		return result;
	}

	public Trip save(final Trip trip) {
		Trip result;

		Assert.notNull(trip, "trip.error.null");
		////////No necesario: nunca se va a llegar al save si las etiquetas
		////////tienen errores, y si llegara, fallaria el repo.save
		//		Assert.hasText(trip.getTitle(), "trip.error.title.notext");
		//		Assert.hasText(trip.getDescription(), "trip.error.description.notext");
		//		Assert.notNull(trip.getDepartureTime(), "trip.error.departureTime.null");
		//		Assert.hasText(trip.getOrigin(), "trip.error.origin.notext");
		//		Assert.hasText(trip.getDestination(), "trip.error.destination.notext");
		//		Assert.notNull(trip.getType(), "trip.error.type.null");
		//////////////////////////////////////////////////////////
		Assert.isTrue((trip.getOriginLat() == null && trip.getOriginLon() == null) || ((!(trip.getOriginLat() == null) && !(trip.getOriginLon() == null))), "trip.error.originCoords");
		Assert.isTrue((trip.getDestinationLat() == null && trip.getDestinationLon() == null) || ((!(trip.getDestinationLat() == null) && !(trip.getDestinationLon() == null))), "trip.error.destinationCoords");
		final Date actualDate = new Date();
		Assert.isTrue(trip.getDepartureTime().after(actualDate), "trip.date.error");
		result = this.tripRepository.save(trip);
		return result;
	}

	public Long count() {
		return this.tripRepository.count();
	}

	public void flush() {
		this.tripRepository.flush();
	}

	//Other Business methods-------------------------------------------------------------------

	public Trip reconstruct(final Trip trip, final BindingResult bindingResult) {
		Trip result;
		// El trip no se puede modificar, por tanto siempre viene con id 0. 
		// Al pasar esto nos podemos ahorrar el preguntar por la id, o incluso meter la id
		// como hidden en la vista. El customer tampoco se pasa por hidden, lo setea el
		// construct con el create. Para el metodo ban no se reconstruye el trip, se pasa 
		// solo la id del trip al metodo y este se encarga de buscarlo modificarlo y guardarlo.
		//		final Trip original;

		//		if (trip.getId() == 0)
		result = this.create();
		//		else {
		//			original = this.tripRepository.findOne(trip.getId());
		//			result = new Trip();
		//			result.setBanned(original.getBanned());
		//		}
		//		result.setCustomer(trip.getCustomer());
		result.setDepartureTime(trip.getDepartureTime());
		result.setDescription(trip.getDescription());
		result.setDestination(trip.getDestination());
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

	public Collection<Trip> findAllValidOffersByKeyWord(final String keyword) {
		Collection<Trip> result;
		Assert.notNull(this.customerService.findCustomerByPrincipal());
		result = this.tripRepository.findAllValidOffersByKeyWord(keyword);

		return result;

	}

	public Collection<Trip> findAllValidRequestsByKeyWord(final String keyword) {
		Collection<Trip> result;
		Assert.notNull(this.customerService.findCustomerByPrincipal());
		result = this.tripRepository.findAllValidRequestsByKeyWord(keyword);

		return result;

	}

	public Trip banTrip(final int tripId) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "Solo el administrador puede banear ofertas o peticiones");

		final Trip aux = this.tripRepository.findOne(tripId);
		Assert.isTrue(!aux.getBanned(), "no puedes banear una oferta/petición ya baneada");

		aux.setBanned(true);

		return this.tripRepository.save(aux);
	}

	public Trip unbanTrip(final int tripId) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "Solo el administrador puede desbanear ofertas o peticiones");

		final Trip aux = this.tripRepository.findOne(tripId);
		Assert.isTrue(aux.getBanned(), "no puedes desbanear una oferta/petición sin banear");

		aux.setBanned(false);

		return this.tripRepository.save(aux);
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

	//01 - Ratio of offers versus requests
	public Double ratioOffersVsRequests() {
		Double result, res1, res2;
		res1 = this.tripRepository.countAllOffers();
		res2 = this.tripRepository.countAllRequests();

		if (res1 != null && res2 != null && res2 > 0)
			result = res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//02 - Average number of offers per customer.
	public Double avgOfferPerCustomer() {
		Double result, res1;
		Long res2;
		res1 = this.tripRepository.countAllOffers();
		res2 = this.customerService.count();

		if (res1 != null && res2 != null && res2 > 0)
			result = res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//02 - Average number of requests per customer.
	public Double avgRequestsPerCustomer() {
		Double result, res1;
		Long res2;
		res1 = this.tripRepository.countAllRequests();
		res2 = this.customerService.count();

		if (res1 != null && res2 != null && res2 > 0)
			result = res1 / res2;
		else
			result = 0.0;
		return result;
	}

	public Double offersAmount() {
		return this.tripRepository.countAllOffers();
	}

	public Double requestsAmount() {
		return this.tripRepository.countAllRequests();
	}

	public void deleteCustomer(final Customer customer) {
		final Collection<Trip> trips = new ArrayList<Trip>();
		final Collection<Trip> trips2 = new ArrayList<Trip>();
		trips.addAll(this.tripRepository.findAllRequestsByPrincipalId(customer.getId()));
		trips.addAll(this.tripRepository.findAllOffersByPrincipalId(customer.getId()));
		for (final Trip trip : trips) {
			trip.setCustomer(null);
			trips2.add(trip);
		}
		this.tripRepository.save(trips2);
	}
}
