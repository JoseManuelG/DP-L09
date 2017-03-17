
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView createOffer() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findOne();
		result = this.createEditModelAndView(configuration);

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Configuration configuration, final BindingResult binding) {
		ModelAndView result = null;
		Configuration configurationResult;

		configurationResult = this.configurationService.reconstruct(configuration, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(configuration);
		} else
			try {
				this.configurationService.save(configurationResult);
				result = new ModelAndView("redirect:/");
			} catch (final IllegalArgumentException exception) {
				result = this.createEditModelAndView(configuration, exception.getMessage());
			}
		return result;

	}

	// Ancillary methods --------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;

		result = new ModelAndView("configuration/banner");
		result.addObject("configuration", configuration);
		result.addObject("message", message);

		return result;

	}

}
