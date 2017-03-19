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
		final Comment comment2 = this.commentService.reconstruct(comment, binding);
		if (binding.hasErrors())
			result = this.createCommentModelAndView(comment);
		else
			try {
				this.commentService.save(comment2);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				result = this.createCommentModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	// Ban 	-------------------------------------------------------------------

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int commentId) {
		ModelAndView result;
		this.commentService.banComment(commentId);
		result = new ModelAndView("redirect:/");
		return result;
	}

	// DisBan 	-------------------------------------------------------------------

	@RequestMapping(value = "/disBan", method = RequestMethod.GET)
	public ModelAndView disBan(@RequestParam final int commentId) {
		ModelAndView result;
		this.commentService.disbanComment(commentId);
		result = new ModelAndView("redirect:/");
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
