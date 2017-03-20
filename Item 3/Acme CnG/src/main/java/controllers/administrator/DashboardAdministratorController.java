
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CommentService;
import services.MessageService;
import services.TripService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private TripService			tripService;


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/administrator/dashboard");

		//Queries TripService
		result.addObject("RatioOffersVsRequests", this.tripService.ratioOffersVsRequests());
		result.addObject("AvgOfferPerCustomer", this.tripService.avgOfferPerCustomer());
		result.addObject("AvgRequestsPerCustomer", this.tripService.avgRequestsPerCustomer());

		//Queries ApplicationService

		result.addObject("AvgApplicationVsCustomerAndRequest", this.applicationService.avgApplicationVsCustomerAndRequest());
		result.addObject("CustomerWithMoreApplicationsAccepted", this.applicationService.customerWithMoreApplicationsAccepted());
		result.addObject("CustomerWithMoreApplicationsDenied", this.applicationService.customerWithMoreApplicationsDenied());

		//Queries CommentService
		result.addObject("AvgCommentsPerActor", this.commentService.avgCommentsPerActor());
		result.addObject("AvgCommentsPerOffer", this.commentService.avgCommentsPerOffer());
		result.addObject("AvgCommentsPerRequest", this.commentService.avgCommentsPerRequest());
		result.addObject("avgCommentsByActors", this.commentService.avgCommentsByActors());
		result.addObject("AvgeActorWritingComments", this.commentService.avgActorWritingComments());
		//Queries CommentService
		result.addObject("AvgMessagesSentPerActor", this.messageService.avgMessagesSentPerActor());
		result.addObject("MinMessagesSentPerActor", this.messageService.minMessagesSentPerActor());
		result.addObject("MaxMessagesSentPerActor", this.messageService.maxMessagesSentPerActor());
		result.addObject("AvgMessagesReceivedPerActor", this.messageService.avgMessagesReceivedPerActor());
		result.addObject("MinMessagesReceivedPerActor", this.messageService.minMessagesReceivedPerActor());
		result.addObject("MaxMessagesReceivedPerActor", this.messageService.maxMessagesReceivedPerActor());
		result.addObject("ActorSentMoreMessages", this.messageService.actorSentMoreMessages());
		result.addObject("ActorReceivedMoreMessages", this.messageService.actorReceivedMoreMessages());

		result.addObject("requestURI", "dashboard/administrator/dashboard.do");

		return result;
	}

}
