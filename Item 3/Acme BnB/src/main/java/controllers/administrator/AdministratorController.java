
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("tenant/view");

		Administrator administrator = administratorService.findByPrincipal();

		result.addObject("tenant", administrator);
		result.addObject("socialIdentities", administrator.getSocialIdentities());
		result.addObject("requestURI", "administrator/myProfile.do");
		result.addObject("esMiPerfil", true);
		return result;
	}


}
