/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------

	public CommentAdministratorController() {
		super();
	}

	// Ban 	-------------------------------------------------------------------

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Commentable commentable;

		comment = this.commentService.findOne(commentId);
		commentable = comment.getCommentable();

		this.commentService.banComment(commentId);

		if (commentable instanceof Actor)
			result = new ModelAndView("redirect:/actor/view.do?actorId=" + commentable.getId());
		else
			result = new ModelAndView("redirect:/trip/view.do?tripId=" + commentable.getId());
		return result;
	}

	// Unban 	-------------------------------------------------------------------

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Commentable commentable;

		comment = this.commentService.findOne(commentId);
		commentable = comment.getCommentable();

		this.commentService.unbanComment(commentId);

		if (commentable instanceof Actor)
			result = new ModelAndView("redirect:/actor/view.do?actorId=" + commentable.getId());
		else
			result = new ModelAndView("redirect:/trip/view.do?tripId=" + commentable.getId());

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
