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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;

@Controller
@RequestMapping("/law")
public class LawController extends AbstractController {
	
	@Autowired
	private ConfigurationService configurationService;
	// Constructors -----------------------------------------------------------

	public LawController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/terms-conditions")
	public ModelAndView terms() {
		ModelAndView result;
		Double amount;
		
		amount = configurationService.findOne().getFee();
		result = new ModelAndView("law/terms-conditions");
		result.addObject("amount", amount);
		return result;
	}
}
