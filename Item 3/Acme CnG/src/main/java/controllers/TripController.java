
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.TripService;
import domain.Application;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService			tripService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView display(final int tripId) {
		ModelAndView result;

		result = this.createViewModelAndView(tripId);

		return result;
	}

	// Ancillary methods --------------------------------------------------------
	protected ModelAndView createViewModelAndView(final int tripId) {
		ModelAndView result;

		result = this.createViewModelAndView(tripId, null);

		return result;
	}

	public ModelAndView createViewModelAndView(final int tripId, final String message) {
		ModelAndView result;
		Trip trip;
		Collection<Application> applications;
		Boolean isOwner;
		String type;

		trip = this.tripService.findOne(tripId);
		applications = this.applicationService.findAllApplicationsByTrip(tripId);
		isOwner = false;
		try {
			isOwner = this.customerService.findCustomerByPrincipal().equals(trip.getCustomer());
		} catch (final Exception e) {
		}

		type = trip.getType().toLowerCase();

		result = new ModelAndView("trip/view/" + type);
		result.addObject("trip", trip);
		result.addObject("applications", applications);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "trip/view.do?tripId=" + trip.getId());
		result.addObject("message", message);

		return result;

	}
}