
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	private CommentService	commentService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(final int id) {
		ModelAndView result;
		Customer customer;
		Collection<Comment> unBannedComments;
		Collection<Comment> bannedComments;

		unBannedComments = this.commentService.findUnbannedCommentsByCommentable(id);
		bannedComments = this.commentService.findBannedCommentsByCommentable(id, this.customerService.findCustomerByPrincipal().getId());

		customer = this.customerService.findOne(id);

		result = new ModelAndView("/view");
		result.addObject("customer", customer);
		result.addObject("unBannedComments", unBannedComments);
		result.addObject("bannedComments", bannedComments);

		return result;
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView myProfile() {
		ModelAndView result;
		result = this.view(this.customerService.findCustomerByPrincipal().getId());
		return result;
	}

}
