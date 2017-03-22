
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.TripService;
import controllers.AbstractController;
import domain.Actor;
import domain.Trip;
import forms.SearchForm;

@Controller
@RequestMapping("/trip/customer")
public class TripCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private ActorService	actorService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list/my/offers", method = RequestMethod.GET)
	public ModelAndView listOffers() {
		ModelAndView result;
		Collection<Trip> trips;
		Actor principal;

		trips = this.tripService.findAllOffersByPrincipal();
		principal = this.actorService.findActorByPrincipal();

		result = new ModelAndView("trip/list/my/offers");
		result.addObject("trips", trips);
		result.addObject("principal", principal);
		result.addObject("requestURI", "trip/customer/list/my/offers.do");

		return result;
	}
	@RequestMapping(value = "/list/my/requests", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<Trip> trips;
		Actor princpal;

		trips = this.tripService.findAllRequestsByPrincipal();
		princpal = this.actorService.findActorByPrincipal();

		result = new ModelAndView("trip/list/my/requests");
		result.addObject("trips", trips);
		result.addObject("princpal", princpal);
		result.addObject("requestURI", "trip/customer/list/my/requests.do");

		return result;
	}
	// Search ---------------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		SearchForm searchForm;

		searchForm = new SearchForm();

		result = new ModelAndView("trip/search");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(final SearchForm form, final BindingResult binding) {
		ModelAndView result;
		String type;

		//Selecciona tiles para el título según sea offer o request:
		type = form.getType().toLowerCase();

		result = new ModelAndView("redirect:/trip/customer/search/" + type + ".do?keyword=" + form.getKeyword());

		return result;
	}

	@RequestMapping(value = "/search/offers", method = RequestMethod.GET)
	public ModelAndView listOffers(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Trip> trips;
		Actor principal;

		trips = this.tripService.findAllValidOffersByKeyWord(keyword);
		principal = this.actorService.findActorByPrincipal();

		result = new ModelAndView("trip/search/list");
		result.addObject("trips", trips);
		result.addObject("principal", principal);
		result.addObject("requestURI", "trip/customer/search/offers.do");

		return result;
	}

	@RequestMapping(value = "/search/requests", method = RequestMethod.GET)
	public ModelAndView listRequests(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Trip> trips;
		Actor principal;

		trips = this.tripService.findAllValidRequestsByKeyWord(keyword);
		principal = this.actorService.findActorByPrincipal();

		result = new ModelAndView("trip/search/list");
		result.addObject("trips", trips);
		result.addObject("principal", principal);
		result.addObject("requestURI", "trip/customer/search/requests.do");

		return result;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create/offer", method = RequestMethod.GET)
	public ModelAndView createOffer() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.createOffer();
		result = this.createEditModelAndView(trip);

		return result;
	}
	@RequestMapping(value = "/create/request", method = RequestMethod.GET)
	public ModelAndView createRequest() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.createRequest();
		result = this.createEditModelAndView(trip);

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Trip trip, final BindingResult binding) {
		ModelAndView result = null;
		Trip tripResult;
		String type;

		tripResult = this.tripService.reconstruct(trip, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(trip);
		} else
			try {
				this.tripService.save(tripResult);
				type = tripResult.getType().toLowerCase();
				result = new ModelAndView("redirect:/trip/customer/list/my/" + type + "s.do");
			} catch (final IllegalArgumentException exception) {
				result = this.createEditModelAndView(trip, exception.getMessage());
			}
		return result;

	}

	// Ancillary methods --------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String message) {
		ModelAndView result;
		String type;

		//Selecciona tiles para el título según sea offer o request:
		type = trip.getType().toLowerCase();

		result = new ModelAndView("trip/create/" + type);
		result.addObject("trip", trip);
		result.addObject("message", message);

		return result;

	}

}
