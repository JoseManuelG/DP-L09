
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.PropertyService;
import domain.Actor;
import domain.AttributeValue;
import domain.Audit;
import domain.Auditor;
import domain.Lessor;
import domain.Property;

@Controller
@RequestMapping("/property")
public class PropertyController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	PropertyService	propertyService;

	@Autowired
	ActorService	actorService;

	@Autowired
	AuditService	auditService;


	// Constructors -----------------------------------------------------------

	public PropertyController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> properties;
		properties = propertyService.findAllOrdered();
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("properties", properties);

		return result;
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) Integer propertyId) {
		ModelAndView result;
		Boolean esMiProperty = false;
		Boolean auditorTieneAudit = false;
		result = new ModelAndView("property/view");
		Property property = propertyService.findOne(propertyId);
		Collection<AttributeValue> attributeValues = property.getAttributeValues();
		Collection<Audit> audits = propertyService.findAuditsByProperty(property);
		
		try {
			Actor actor = actorService.findByPrincipal();
			if (actor instanceof Lessor) {
				esMiProperty = actor.equals(property.getLessor());
			} else if (actor instanceof Auditor) {
				auditorTieneAudit = auditService.checkUniqueOrDraft(property.getId(), (Auditor) actor);
			}
		} catch (IllegalArgumentException e) {
		}

		result.addObject("property", property);
		result.addObject("audits", audits);
		result.addObject("auditorTieneAudit", auditorTieneAudit);
		result.addObject("attributeValues", attributeValues);
		result.addObject("esMiProperty", esMiProperty);
		result.addObject("requestURI", "property/view.do?propertyId=" + property.getId());
		return result;
	}

}
