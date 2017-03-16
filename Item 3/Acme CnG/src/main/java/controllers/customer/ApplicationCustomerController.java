
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import controllers.AbstractController;
import controllers.TripController;
import domain.Application;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services -------------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;

	// Controllers -------------------------------------------------------------
	@Autowired
	private TripController		tripController;


	// Apply ---------------------------------------------------------------
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public ModelAndView apply(final int tripId) {
		ModelAndView result;

		try {
			this.applicationService.create(tripId);
			result = new ModelAndView("redirect:/trip/view.do?tripId=" + tripId);
		} catch (final IllegalArgumentException e) {
			result = this.tripController.createViewModelAndView(tripId, e.getMessage());
		}

		return result;

	}

	// Accept ---------------------------------------------------------------
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		this.applicationService.acceptApply(applicationId);
		result = new ModelAndView("redirect:/trip/view.do?tripId=" + application.getTrip().getId());

		return result;

	}
	// Deny ---------------------------------------------------------------
	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		this.applicationService.denyApply(applicationId);
		result = new ModelAndView("redirect:/trip/view.do?tripId=" + application.getTrip().getId());

		return result;

	}
}
