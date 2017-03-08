package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttributeService;
import services.AttributeValueService;
import services.PropertyService;
import domain.Actor;
import domain.Attribute;
import domain.AttributeValue;
import domain.Property;

@Controller
@RequestMapping("/attributeValue/lessor")
public class AttributeValueController extends AbstractController {

	@Autowired
	AttributeValueService attributeValueService ;
	@Autowired
	AttributeService attributeService;
	@Autowired
	PropertyService propertyService;

	@Autowired
	ActorService actorService;
	// Constructors -----------------------------------------------------------
	
	public AttributeValueController () {
		super();
	}
	
	
	
	

	// create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody ModelAndView create(int propertyId) {
		ModelAndView result= new ModelAndView("attributeValue/lessor/create");
		Boolean noQuedanMas = false;
		AttributeValue attributeValue= attributeValueService.create();
		Property property=propertyService.findOne(propertyId);
		attributeValue.setProperty(property);
		
		
		Collection<Attribute>attributes= attributeService.findAll();
		for(AttributeValue aux:property.getAttributeValues()){
			attributes.remove(aux.getAttribute());
		}
		if(attributes.isEmpty()){
			noQuedanMas=true;
		}
		
		result.addObject("attributes",attributes);
		result.addObject("attributeValue",attributeValue);
		result.addObject("noQuedanMas",noQuedanMas);

		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save(int attributeValueId) {
		ModelAndView result= new ModelAndView("attributeValue/lessor/edit");
		AttributeValue attributeValue= attributeValueService.findOne(attributeValueId);

		result.addObject("attributeValue",attributeValue);
		
		
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid AttributeValue attributeValue, BindingResult binding) {
		ModelAndView result=null;
		if (binding.hasErrors()) {
			result = createEditModelAndView(attributeValue);
		} else {
			try {
				Assert.notNull(attributeValue.getProperty(),"Un AttributeValue debe estar asignado a una propperty");
				Assert.notNull(attributeValue.getAttribute(),"Un AttributeValue debe estar asignado a un attribute");
				
				Actor actor = actorService.findByPrincipal();
				Assert.isTrue(actor.equals(attributeValue.getProperty().getLessor()), "Solo puedes editar tus propias propiedades");
				
				attributeValueService.save(attributeValue);
				
				result = new ModelAndView("redirect:../../property/view.do?propertyId="+attributeValue.getProperty().getId());
				
		} catch (Throwable oops) {
			result = createEditModelAndView(attributeValue, "attribute.commit.error");	
			}
			
	}
			return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody ModelAndView save( AttributeValue attributeValue) {
		ModelAndView result=null;
			try {
				
				Actor actor = actorService.findByPrincipal();
				Assert.isTrue(actor.equals(attributeValue.getProperty().getLessor()), "Solo puedes editar tus propias propiedades");
				
				attributeValueService.delete(attributeValue);
				
				result = new ModelAndView("redirect:../../property/view.do?propertyId="+attributeValue.getProperty().getId());
				
		} catch (Throwable oops) {
			result = createEditModelAndView(attributeValue, "attribute.commit.error");	
			}
			
	
			return result;
	}

	
	
	protected ModelAndView createEditModelAndView(AttributeValue attributeValue) {
	ModelAndView result;

	result = createEditModelAndView(attributeValue, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(AttributeValue attributeValue, String message) {
		ModelAndView result;
		
		
		Collection<Attribute>attributes= attributeService.findAll();
		for(AttributeValue aux:attributeValue.getProperty().getAttributeValues()){
			attributes.remove(aux.getAttribute());
		}
		result = new ModelAndView("attributeValue/lessor/edit");
		result.addObject("attributes", attributes);
		result.addObject("attributeValue", attributeValue);
		result.addObject("message", message);

		return result;
	}
	

}

