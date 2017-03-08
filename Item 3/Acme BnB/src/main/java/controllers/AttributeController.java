package controllers;

import java.util.ArrayList;
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

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.AttributeService;
import services.AttributeValueService;
import domain.Attribute;

@Controller
@RequestMapping("/attribute/administrator")
public class AttributeController extends AbstractController {

	@Autowired
	AttributeService attributeService ;
	@Autowired
	AttributeValueService attributeValueService ;
	@Autowired
	LoginService loginService;

	@Autowired
	ActorService actorService;
	// Constructors -----------------------------------------------------------
	
	public AttributeController () {
		super();
	}
	
	
	
	
	// list-----------------------------------------------------------
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public @ResponseBody ModelAndView list() {
			ModelAndView result= new ModelAndView("attribute/administrator/list");
			Collection<Attribute> attributes= attributeService.findAll();
			result.addObject("requestURI","attribute/administrator/list.do");
			result.addObject("attributes",attributes);
			return result;
		
		
		}
	// create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save() {
		ModelAndView result= new ModelAndView("attribute/administrator/create");
		Attribute attribute= attributeService.create();
		result.addObject("attribute",attribute);
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save(int attributeId) {
		ModelAndView result= new ModelAndView("attribute/administrator/edit");
		Attribute attribute= attributeService.findOne(attributeId);

		result.addObject("attribute",attribute);
		
		
		return result;
	
	
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid Attribute attribute, BindingResult binding) {
		ModelAndView result=null;
		if (binding.hasErrors()) {
			result = createEditModelAndView(attribute);
		} else {
			try {
				ArrayList<Authority>authorities=new ArrayList<Authority>();
				authorities.addAll(loginService.getPrincipal().getAuthorities());
				Assert.isTrue(authorities.get(0).getAuthority().equals(Authority.ADMINISTRATOR),"Para poder Guardar un Attribute debes ser admninistrador");
				attributeService.save(attribute);
				
				result = this.list();
				
		} catch (Throwable oops) {
			result = createEditModelAndView(attribute, "attribute.commit.error");	
			}
			
	}
			return result;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody ModelAndView save( Attribute attribute) {
		ModelAndView result=null;
			try {
				
				ArrayList<Authority>authorities=new ArrayList<Authority>();
				authorities.addAll(loginService.getPrincipal().getAuthorities());
				Assert.isTrue(authorities.get(0).getAuthority().equals(Authority.ADMINISTRATOR),"Para poder Guardar un Attribute debes ser admninistrador");
				attributeService.delete(attribute);
				result =this.list();
				
		} catch (Throwable oops) {
			if(attributeValueService.findAttributeValuesForAttribute(attribute).isEmpty()){
			result = createEditModelAndView(attribute, "attribute.commit.error");	
			}else{
				result = createEditModelAndView(attribute, "attribute.use.error");	
					
			}
			
		}
			return result;
	}

	
	
	protected ModelAndView createEditModelAndView(Attribute attribute) {
	ModelAndView result;

	result = createEditModelAndView(attribute, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(Attribute attribute, String message) {
		ModelAndView result;
		result = new ModelAndView("attribute/administrator/edit");
		result.addObject("attribute", attribute);
		result.addObject("message", message);

		return result;
	}
	

}

