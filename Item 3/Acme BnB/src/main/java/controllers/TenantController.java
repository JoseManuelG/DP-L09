
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import services.TenantService;
import domain.Comment;
import domain.Tenant;

@Controller
@RequestMapping("/tenant")
public class TenantController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TenantService	tenantService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public TenantController() {
		super();
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("tenant/view");

		Tenant tenant = tenantService.findByPrincipal();

		result.addObject("tenant", tenant);
		result.addObject("comments", commentService.findAllCommentsOfACustomer( tenant));
		result.addObject("socialIdentities", tenant.getSocialIdentities());
		result.addObject("requestURI", "tenant/myProfile.do");
		result.addObject("esMiPerfil", true);
		//Bloque de botoneria de comentario Hecho por roldan -->
		result.addObject("puedoComentar",true);
		//<---
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(int tenantId) {
		ModelAndView result;
		result = new ModelAndView("tenant/view");
		boolean puedoComentar = false;
		Tenant tenant = tenantService.findOne(tenantId);
		
		//Bloque de botoneria de comentario Hecho por roldan -->
		try{
			Comment comment= commentService.create();
			comment.setRecipient(tenant);
			comment.setSender(customerService.findActorByPrincial());
			puedoComentar=commentService.validComment(comment);
			}catch (Throwable oops) {
				puedoComentar=false;
			}
		result.addObject("puedoComentar",puedoComentar);
		//<--
		result.addObject("tenant", tenant);
		result.addObject("comments", commentService.findAllCommentsOfACustomer(tenant));
		result.addObject("requestURI", "tenant/view.do");
		result.addObject("socialIdentities", tenant.getSocialIdentities());
		result.addObject("esMiPerfil", false);

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Tenant tenant = (Tenant) customerService.findActorByPrincial();
		result = createEditModelAndView(tenant);
		return result;
	}

	// Save -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid Tenant tenant, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(tenant);
		} else {
			try {
				tenantService.save(tenant);
				result = new ModelAndView("../");

			} catch (Throwable oops) {
				result = createEditModelAndView(tenant, "tenant.commit.error");
			}
		}

		return result;
	}

	// Delete -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Tenant tenant, BindingResult binding) {
		ModelAndView result;

		try {
			tenantService.delete(tenant);
			result = new ModelAndView("../");
		} catch (Throwable oops) {
			result = createEditModelAndView(tenant, "tenant.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Tenant tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Tenant tenant, String message) {
		ModelAndView result;
		result = new ModelAndView("tenant/edit");
		result.addObject("tenant", tenant);
		result.addObject("message", message);

		return result;
	}

}
