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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import forms.ActorForm;

@Controller
@RequestMapping("/law")
public class LawController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public LawController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/register")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("law/register");
		result.addObject(new ActorForm());

		return result;
	}
}
