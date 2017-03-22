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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SecurityController() {
		super();
	}

	// Register ------------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final ActorForm actorForm = new ActorForm();
		result = new ModelAndView("security/register");
		result.addObject(actorForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		//		final Customer customer = this.customerService.reconstruct(actorForm, binding);
		if (binding.hasErrors())
			result = this.createRegisterModelAndView(actorForm);
		else if (!actorForm.getUserAccount().getPassword().equals(actorForm.getConfirmPassword()))
			result = this.createRegisterModelAndView(actorForm, "security.password.error");
		else if (!((boolean) actorForm.getAcepted()))
			result = this.createRegisterModelAndView(actorForm, "security.terms.error");
		else
			try {
				//				this.customerService.save(customer);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				result = this.createRegisterModelAndView(actorForm, "security.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	private ModelAndView createRegisterModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(actorForm, null);

		return result;
	}

	private ModelAndView createRegisterModelAndView(final ActorForm actorForm, final String string) {
		ModelAndView result;
		result = new ModelAndView();
		result.addObject(actorForm);
		result.addObject("message", string);
		return result;
	}
}
