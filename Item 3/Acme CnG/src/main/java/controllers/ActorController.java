
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
import domain.Actor;
import domain.Comment;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private CustomerService	customerService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		Collection<Comment> unBannedComments;
		Collection<Comment> bannedComments;
		Collection<Comment> allBannedComments;
		Boolean isAdmin = false;
		//Para quitar el boton de borrar al ver los perfiles de otros----------------
		Boolean myProfile = false;
		if (this.actorService.findActorByPrincipal().getId() == actorId)
			myProfile = true;
		//--------------------------------------------------------------------------
		if (this.actorService.findActorByPrincipal().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"))
			isAdmin = true;

		unBannedComments = this.commentService.findUnbannedCommentsByCommentable(actorId);
		bannedComments = this.commentService.findBannedCommentsByCommentable(actorId, this.actorService.findActorByPrincipal().getId());
		allBannedComments = this.commentService.findBannedCommentsByCommentable(actorId);

		actor = this.actorService.findOne(actorId);

		result = new ModelAndView("actor/view");
		result.addObject("actor", actor);
		result.addObject("unBannedComments", unBannedComments);
		result.addObject("bannedComments", bannedComments);
		result.addObject("allBannedComments", allBannedComments);
		result.addObject("isAdmin", isAdmin);
		result.addObject("myProfile", myProfile);
		result.addObject("requestURI", "actor/view.do");

		return result;
	}
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView myProfile() {
		ModelAndView result;
		result = this.view(this.actorService.findActorByPrincipal().getId());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		this.customerService.delete();
		result = new ModelAndView("redirect:/j_spring_security_logout");
		return result;
	}

}
