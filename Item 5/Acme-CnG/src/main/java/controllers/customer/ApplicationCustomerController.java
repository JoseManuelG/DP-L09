
package controllers.customer;

import java.util.Collection;

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


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.applicationService.findAllByPrincipal();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do");

		return result;
	}

	// Apply ---------------------------------------------------------------
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public ModelAndView apply(final int tripId) {
		ModelAndView result;

		try {
			this.applicationService.create(tripId);
			result = new ModelAndView("redirect:/application/customer/list.do");
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
		try {
			this.applicationService.acceptApply(applicationId);
			result = new ModelAndView("redirect:/trip/view.do?tripId=" + application.getTrip().getId());
		} catch (final IllegalArgumentException e) {
			result = this.tripController.createViewModelAndView(application.getTrip().getId(), e.getMessage());
		}

		return result;

	}
	// Deny ---------------------------------------------------------------
	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		try {
			this.applicationService.denyApply(applicationId);
			result = new ModelAndView("redirect:/trip/view.do?tripId=" + application.getTrip().getId());
		} catch (final IllegalArgumentException e) {
			result = this.tripController.createViewModelAndView(application.getTrip().getId(), e.getMessage());
		}

		return result;

	}
}