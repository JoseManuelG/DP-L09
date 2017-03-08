
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ConfigurationController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView save() {
		Configuration configuration = configurationService.findOne();
		ModelAndView result = this.createEditModelAndView(configuration);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(Configuration configuration, BindingResult binding) {
		ModelAndView result;
		Configuration res;

		res = configurationService.reconstruct(configuration, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(configuration);
			System.out.println(binding.getAllErrors().toString());
		} else {
			try {
				configurationService.save(res);
				result = new ModelAndView("redirect:../../");

			} catch (Throwable oops) {
				result = createEditModelAndView(configuration, "configuration.commit.error");
			}

		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Configuration configuration) {
		ModelAndView result;

		result = createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Configuration configuration, String message) {
		ModelAndView result;
		result = new ModelAndView("configuration/administrator/edit");

		result.addObject("configuration", configuration);
		result.addObject("message", message);
		result.addObject("requestURI", "configuration/administrator/edit.do");
		return result;
	}

}
