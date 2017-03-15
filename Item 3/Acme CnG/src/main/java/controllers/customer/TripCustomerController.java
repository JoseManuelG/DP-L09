
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Trip;
import forms.SearchForm;

@Controller
@RequestMapping("/trip/customer")
public class TripCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService	tripService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list/my/offers", method = RequestMethod.GET)
	public ModelAndView listOffers() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAllOffersByPrincipal();

		result = new ModelAndView("trip/list/my/offers");
		result.addObject("trips", trips);

		return result;
	}

	@RequestMapping(value = "/list/my/requests", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAllOffersByPrincipal();

		result = new ModelAndView("trip/list/my/requests");
		result.addObject("trips", trips);

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

		result = new ModelAndView("redirect: /trip/customer/search/" + type + ".do?keyword=" + form.getKeyword());

		return result;
	}

	@RequestMapping(value = "/search/offers", method = RequestMethod.GET)
	public ModelAndView listOffers(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findByKeyWord(keyword);

		result = new ModelAndView("trip/search/list");
		result.addObject("trips", trips);

		return result;
	}

	@RequestMapping(value = "/search/requests", method = RequestMethod.GET)
	public ModelAndView listRequests(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findByKeyWord(keyword);

		result = new ModelAndView("trip/search/list");
		result.addObject("trips", trips);

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

		tripResult = this.tripService.reconstruct(trip, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(trip);
		} else
			try {
				this.tripService.save(tripResult);
				result = new ModelAndView("redirect: /"); //TODO donde mandar despues de crear
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
