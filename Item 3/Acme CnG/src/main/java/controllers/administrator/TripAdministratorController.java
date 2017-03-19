
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Trip;

@Controller
@RequestMapping("/trip/administrator")
public class TripAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService	tripService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list/offers", method = RequestMethod.GET)
	public ModelAndView listOffers() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAllOffers();

		result = new ModelAndView("trip/list/offers");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/administrator/list/offers.do");

		return result;
	}

	@RequestMapping(value = "/list/requests", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAllRequests();

		result = new ModelAndView("trip/list/requests");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/administrator/list/requests.do");

		return result;
	}

	// Ban ---------------------------------------------------------------
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView deny(final int tripId) {
		ModelAndView result;
		String type;
		Trip trip;

		trip = this.tripService.banTrip(tripId);
		type = trip.getType().toLowerCase();
		result = new ModelAndView("redirect:/trip/administrator/list/" + type + "s.do");

		return result;

	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(final int tripId) {
		ModelAndView result;
		String type;
		Trip trip;

		trip = this.tripService.unbanTrip(tripId);
		type = trip.getType().toLowerCase();
		result = new ModelAndView("redirect:/trip/administrator/list/" + type + "s.do");

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
