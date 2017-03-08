
package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.AttributeValueService;
import services.AuditService;
import services.BookService;
import services.CustomerService;
import services.LessorService;
import services.PropertyService;
import controllers.AbstractController;
import domain.Lessor;
import domain.Property;

@Controller
@RequestMapping("/property/lessor")
public class PropertyLessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	PropertyService			propertyService;

	@Autowired
	CustomerService			customerService;

	@Autowired
	LessorService			lessorService;

	@Autowired
	AttributeValueService	attributeValueService;

	@Autowired
	AuditService			auditService;

	@Autowired
	BookService				bookService;


	// Constructors -----------------------------------------------------------

	public PropertyLessorController() {
		super();
	}

	// List --------------------------------------------------------------------

	@RequestMapping(value = "/myProperties", method = RequestMethod.GET)
	public ModelAndView myProperties() {
		ModelAndView result;
		Lessor lessor = (Lessor) customerService.findActorByPrincial();
		Collection<Property> properties = propertyService.findPropertiesByLessor(lessor);

		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/lessor/myProperties.do");
		result.addObject("properties", properties);
		return result;
	}

	// Create --------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Property property = propertyService.create(lessorService.findByPrincipal().getId());
		Lessor lessor = (Lessor) customerService.findActorByPrincial();
		property.setLessor(lessor);
		result = createEditModelAndView(property);
		return result;

	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyId) {
		ModelAndView result;
		Property property = propertyService.findOne(propertyId);
		result = createEditModelAndView(property);
		return result;
	}
	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(Property property, BindingResult binding) {
		ModelAndView result = null;
		Property propertyResult;
		propertyResult = propertyService.reconstruct(property, binding);	
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(property);
		} else {
			try {
				propertyService.save(propertyResult);
				result = new ModelAndView("redirect:../lessor/myProperties.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(property, "property.commit.error");
			}
		}

		return result;
	}

	// Delete -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Property property, BindingResult binding) {
		ModelAndView result;
		Property propertyResult;
		
		propertyResult = propertyService.reconstruct(property, binding);
		try {
			Lessor lessor = (Lessor) customerService.findActorByPrincial();
			property.setLessor(lessor);
			propertyService.delete(propertyResult);
			result = new ModelAndView("redirect:../lessor/myProperties.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(property, "property.commit.error");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Property property) {
		ModelAndView result;

		result = createEditModelAndView(property, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Property property, String message) {
		ModelAndView result;
		result = new ModelAndView("property/lessor/edit");
		result.addObject("property", property);
		result.addObject("message", message);
		return result;
	}

}
