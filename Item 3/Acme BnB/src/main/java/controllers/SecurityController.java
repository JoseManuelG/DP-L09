
package controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.InvoiceService;
import services.LessorService;
import services.TenantService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Lessor;
import domain.Tenant;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private TenantService			tenantService;
	@Autowired
	private LessorService			lessorService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private InvoiceService			invoiceService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		ActorForm actorForm = new ActorForm();
		result = createRegisterModelAndView(actorForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Tenant tenant = null;
		Lessor lessor = null;
		if (actorForm.getTypeOfActor().equals("TENANT")) {
			tenant = tenantService.reconstruct(actorForm, binding);
		} else if (actorForm.getTypeOfActor().equals("LESSOR")) {
			lessor = lessorService.reconstruct(actorForm, binding);
		}
		if (binding.hasErrors()) {
			result = createRegisterModelAndView(actorForm);
		} else if (!actorForm.getUserAccount().getPassword()
					.equals(actorForm.getConfirmPassword())) {
			result = createRegisterModelAndView(actorForm, "security.password.error");
		} else if (!((boolean) actorForm.getAcepted())) {
			result = createRegisterModelAndView(actorForm, "security.terms.error");
		} else {
			try {
				if (actorForm.getTypeOfActor().equals("TENANT")) {

					tenantService.save(tenant);

				} else if (actorForm.getTypeOfActor().equals("LESSOR")) {

					lessorService.save(lessor);

				}

				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createRegisterModelAndView(actorForm, "lessor.commit.error");
			}
		}

		return result;
	}
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;
		Boolean isAdmin;
		ActorForm actorForm;
		
		actor = actorService.findByPrincipal();
		isAdmin = actor instanceof Administrator;
		
		actorForm = new ActorForm();
		actorForm.setName(actor.getName());
		actorForm.setSurname(actor.getSurname());
		actorForm.setEmail(actor.getEmail());
		actorForm.setPhone(actor.getPhone());
		actorForm.setPicture(actor.getPicture());
		actorForm.setUserAccount(actor.getUserAccount());
		
		result = createEditModelAndView(actorForm, isAdmin);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Actor principal, actorResult;
		Boolean isAdmin;
		String actorString;
		
		isAdmin = false;
		principal = actorService.findByPrincipal();
		if (principal instanceof Auditor) {
			actorResult = auditorService.reconstruct(actorForm, (Auditor) principal, binding);
		} else if (principal instanceof Lessor) {
			actorResult = lessorService.reconstruct(actorForm, (Lessor) principal, binding);
		} else if (principal instanceof Tenant) {
			actorResult = tenantService.reconstruct(actorForm, (Tenant) principal, binding);
		} else {
			isAdmin = true;
			actorResult = administratorService.reconstruct(actorForm, (Administrator) principal, binding);
		}

		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm, isAdmin);
		} else if (!actorForm.getUserAccount().getPassword()
					.equals(actorForm.getConfirmPassword())) {
			result = createEditModelAndView(actorForm, isAdmin, "security.password.error");
		}  else {
			try {
				actorService.save(actorResult);
				actorString = principal.getClass().getSimpleName().toLowerCase();
				result = new ModelAndView("redirect:../" + actorString + "/myProfile.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm,isAdmin, "lessor.commit.error");
			}
		}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ActorForm actorForm) {
		ModelAndView result;
		Actor actor = actorService.findByPrincipal();
		Tenant tenant = null;
		Lessor lessor = null;
		Auditor auditor = null;
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(actor.getUserAccount().getAuthorities());
		String aux = authorities.get(0).getAuthority();

		if (aux.equals(Authority.AUDITOR)) {
			auditor = auditorService.findActorByPrincial();

		} else if (aux.equals(Authority.LESSOR)) {
			lessor = lessorService.findByPrincipal();

		} else if (aux.equals(Authority.TENANT)) {
			tenant = tenantService.findByPrincipal();
		}

		try {

			if (aux.equals("TENANT")) {
				invoiceService.deleteAll(tenant);
				tenantService.delete(tenant);

			} else if (aux.equals("LESSOR")) {
				lessorService.delete(lessor);

			} else if (aux.equals("AUDITOR")) {
				auditorService.delete(auditor);

			}
			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (Exception e) {
			result = createEditModelAndView(actorForm, false, "lessor.commit.error");
		}

		return result;

	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createRegisterModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = createRegisterModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		
		result = new ModelAndView("security/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actorForm, Boolean isAdmin) {
		ModelAndView result;

		result = createEditModelAndView(actorForm, isAdmin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actorForm, Boolean isAdmin, String message) {
		ModelAndView result;

		
		result = new ModelAndView("security/edit");
		result.addObject(actorForm);
		result.addObject("isAdmin", isAdmin);
		result.addObject("message", message);

		return result;
	}
}
