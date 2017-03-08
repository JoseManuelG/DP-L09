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
@RequestMapping("/about")
public class AboutController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public AboutController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView result;
		String name, vat;

		name = "Acme BnB";
		vat = configurationService.findOne().getVAT();

		result = new ModelAndView("about/about");

		result.addObject("name", name);
		result.addObject("vat", vat);

		return result;
	}
}
