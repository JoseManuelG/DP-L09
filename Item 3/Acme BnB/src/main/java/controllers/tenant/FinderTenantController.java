
package controllers.tenant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import controllers.AbstractController;
import domain.Finder;
import domain.Property;

@Controller
@RequestMapping("/finder/tenant")
public class FinderTenantController extends AbstractController {

	@Autowired
	private FinderService	finderService;


	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView finder() {
		ModelAndView result;
		Collection<Property> results;
		Finder finder;
		Date oneHourAgo, lastSearch;

		finder = finderService.findByPrincipal();

		oneHourAgo = new Date(System.currentTimeMillis() - 3600000);
		lastSearch = new Date(finder.getCacheMoment().getTime());
		if (lastSearch.after(oneHourAgo)) {
			results = finder.getResults();
		} else {
			results = new ArrayList<Property>();
		}

		result = new ModelAndView("finder/tenant/finder");
		result.addObject("results", results);
		result.addObject("finder", finder);
		result.addObject("requestURI", "finder/tenant/finder.do");

		return result;
	}

	@RequestMapping(value = "/finder", method = RequestMethod.POST, params = "save")
	public ModelAndView search(Finder finder, BindingResult binding) {
		ModelAndView result;
		Finder res;

		res = finderService.reconstruct(finder, binding);
		Collection<Property> results;
		if (binding.hasErrors()) {
			results = new ArrayList<Property>();

			result = new ModelAndView("finder/tenant/finder");
			result.addObject("finder", finder);
			result.addObject("requestURI", "finder/tenant/finder.do");
			result.addObject("results", results);
		} else {
			try {
				finderService.save(res);
				result = finder();

			} catch (Throwable oops) {
				results = new ArrayList<Property>();

				result = new ModelAndView("finder/tenant/finder");
				result.addObject("finder", finder);
				result.addObject("requestURI", "finder/tenant/finder.do");
				result.addObject("results", results);
				result.addObject("message", "finder.commit.error");
			}
		}

		return result;
	}
}
