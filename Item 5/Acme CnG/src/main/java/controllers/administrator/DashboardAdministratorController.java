
package controllers.administrator;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CommentService;
import services.MessageService;
import services.TripService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private TripService				tripService;

	private static DecimalFormat	df2	= new DecimalFormat(".##");


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/administrator/dashboard");

		//Queries TripService
		result.addObject("RatioOffersVsRequests", DashboardAdministratorController.df2.format(this.tripService.ratioOffersVsRequests()));
		result.addObject("AvgOfferPerCustomer", DashboardAdministratorController.df2.format(this.tripService.avgOfferPerCustomer()));
		result.addObject("AvgRequestsPerCustomer", DashboardAdministratorController.df2.format(this.tripService.avgRequestsPerCustomer()));

		//Queries ApplicationService

		result.addObject("AvgApplicationVsCustomerAndRequest", DashboardAdministratorController.df2.format(this.applicationService.avgApplicationVsCustomerAndRequest()));
		result.addObject("CustomerWithMoreApplicationsAccepted", this.applicationService.customerWithMoreApplicationsAccepted());
		result.addObject("CustomerWithMoreApplicationsDenied", this.applicationService.customerWithMoreApplicationsDenied());

		//Queries CommentService
		result.addObject("AvgCommentsPerActor", DashboardAdministratorController.df2.format(this.commentService.avgCommentsPerActor()));
		result.addObject("AvgCommentsPerOffer", DashboardAdministratorController.df2.format(this.commentService.avgCommentsPerOffer()));
		result.addObject("AvgCommentsPerRequest", DashboardAdministratorController.df2.format(this.commentService.avgCommentsPerRequest()));
		result.addObject("AvgCommentsByActors", DashboardAdministratorController.df2.format(this.commentService.avgCommentsByActors()));
		result.addObject("AvgeActorWritingComments", this.commentService.avgActorWritingComments());
		//Queries CommentService
		result.addObject("AvgMessagesSentPerActor", DashboardAdministratorController.df2.format(this.messageService.avgMessagesSentPerActor()));
		result.addObject("MinMessagesSentPerActor", this.messageService.minMessagesSentPerActor());
		result.addObject("MaxMessagesSentPerActor", this.messageService.maxMessagesSentPerActor());
		result.addObject("AvgMessagesReceivedPerActor", DashboardAdministratorController.df2.format(this.messageService.avgMessagesReceivedPerActor()));
		result.addObject("MinMessagesReceivedPerActor", this.messageService.minMessagesReceivedPerActor());
		result.addObject("MaxMessagesReceivedPerActor", this.messageService.maxMessagesReceivedPerActor());
		result.addObject("ActorSentMoreMessages", this.messageService.actorSentMoreMessages());
		result.addObject("ActorReceivedMoreMessages", this.messageService.actorReceivedMoreMessages());

		result.addObject("requestURI", "dashboard/administrator/dashboard.do");

		return result;
	}

}
