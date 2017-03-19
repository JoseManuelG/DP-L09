
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CustomerService;
import domain.Comment;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int customerId) {
		ModelAndView result;
		Customer customer;
		Collection<Comment> unBannedComments;
		Collection<Comment> bannedComments;
		Collection<Comment> allBannedComments;
		Boolean isAdmin = false;
		if (this.actorService.findActorByPrincipal().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"))
			isAdmin = true;

		unBannedComments = this.commentService.findUnbannedCommentsByCommentable(customerId);
		bannedComments = this.commentService.findBannedCommentsByCommentable(customerId, this.actorService.findActorByPrincipal().getId());
		allBannedComments = this.commentService.findBannedCommentsByCommentable(customerId);

		customer = this.customerService.findOne(customerId);

		result = new ModelAndView("customer/view");
		result.addObject("customer", customer);
		result.addObject("unBannedComments", unBannedComments);
		result.addObject("bannedComments", bannedComments);
		result.addObject("allBannedComments", allBannedComments);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "customer/view.do");

		return result;
	}
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView myProfile() {
		ModelAndView result;
		result = this.view(this.customerService.findCustomerByPrincipal().getId());
		return result;
	}

}
