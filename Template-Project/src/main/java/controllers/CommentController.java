/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Actor;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------

	public CommentController() {
		super();
	}

	// Create ------------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentableId) {
		ModelAndView result;
		final Comment comment = this.commentService.create(commentableId);
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Comment comment, final BindingResult binding) {
		ModelAndView result;
		String aux = null;
		final Comment comment2 = this.commentService.reconstruct(comment, binding);
		if (binding.hasErrors())
			result = this.createCommentModelAndView(comment);
		else
			try {
				this.commentService.save(comment2);
				//				if (comment2.getCommentable() instanceof Trip)
				//					aux = "trip";
				if (comment2.getCommentable() instanceof Actor)
					aux = "actor";

				result = new ModelAndView("redirect:../" + aux + "/view.do?" + aux + "Id=" + comment2.getCommentable().getId());

			} catch (final Throwable oops) {
				result = this.createCommentModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView createCommentModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createCommentModelAndView(comment, null);

		return result;
	}

	private ModelAndView createCommentModelAndView(final Comment comment, final String string) {
		ModelAndView result;
		result = new ModelAndView();
		result.addObject(comment);
		result.addObject("message", string);
		return result;
	}
}
