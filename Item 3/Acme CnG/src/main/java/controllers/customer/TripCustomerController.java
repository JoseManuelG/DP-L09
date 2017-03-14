
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Trip;

@Controller
@RequestMapping("/trip/customer")
public class TripCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService	tripService;


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

		//Selecciona tiles para el título segun sea offer o request:
		type = trip.getType().toLowerCase();

		result = new ModelAndView("trip/create/" + type);
		result.addObject("trip", trip);
		result.addObject("message", message);

		return result;

	}
}
