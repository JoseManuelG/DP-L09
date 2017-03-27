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

@Controller
@RequestMapping("/about")
public class AboutController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AboutController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView result;
		String name, vat;

		name = "Acme CnG";
		vat = "ESB123456789";

		result = new ModelAndView("about/about");

		result.addObject("name", name);
		result.addObject("vat", vat);

		return result;
	}
}
