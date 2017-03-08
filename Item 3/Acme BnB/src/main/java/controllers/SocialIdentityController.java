package controllers;

import java.util.ArrayList;

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
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	@Autowired
	SocialIdentityService socialIdentityService;

	@Autowired
	LoginService loginService;

	@Autowired
	ActorService actorService;
	// Constructors -----------------------------------------------------------
	
	public SocialIdentityController () {
		super();
	}
	// create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save() {
		ModelAndView result= new ModelAndView("socialIdentity/create");
		
		SocialIdentity socialIdentity= socialIdentityService.create();
		
		socialIdentity.setActor(actorService.findByPrincipal());
		result.addObject("socialIdentity",socialIdentity);
		
		//Esto se usa para crear la url del view de la personada logueada dado que no sabe el tipo de usuario que es.
		
		Authority authority=actorService.findByPrincipal().getUserAccount().getAuthorities().iterator().next();
		String authoritytext=authority.getAuthority().toLowerCase();
		result.addObject("typeActor",authoritytext);
		
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save(int socialIdentityId) {
		ModelAndView result= new ModelAndView("socialIdentity/edit");
		SocialIdentity socialIdentity= socialIdentityService.findOne(socialIdentityId);
	
		
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(socialIdentity.getActor().equals(actor), "No puedes cambiar una SocialIdentity que no este asignada a usted");
		result.addObject("socialIdentity",socialIdentity);
		//Esto se usa para crear la url del view de la personada logueada dado que no sabe el tipo de usuario que es.
		
				Authority authority=actorService.findByPrincipal().getUserAccount().getAuthorities().iterator().next();
				String authoritytext=authority.getAuthority().toLowerCase();
				result.addObject("typeActor",authoritytext);
		
		return result;
	
	
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( @Valid SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity);
			System.out.println(binding.getAllErrors().toString());
		} else {
			try {
		//		socialIdentity.setActor(actorService.findByPrincipal());
				socialIdentityService.save(socialIdentity);
				ArrayList<Authority> authorities = new ArrayList<Authority>();
				Actor actor = actorService.findByPrincipal();

				authorities.addAll(actor.getUserAccount().getAuthorities());
				String aux=authorities.get(0).getAuthority().toLowerCase();
				result = new ModelAndView("redirect:../"+aux+"/myProfile.do");
				
		} catch (Throwable oops) {
			result = createEditModelAndView(socialIdentity, "socialIdentity.commit.error");	
			}
			
	}
			return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody ModelAndView save( SocialIdentity socialIdentity) {
		ModelAndView result=null;
			try {
			
				Actor actor = actorService.findByPrincipal();
				SocialIdentity identity=socialIdentityService.findOne(socialIdentity.getId());
				Assert.isTrue(identity.getActor().equals(actor), "No puedes cambiar una SocialIdentity que no este asignada a usted");
				ArrayList<Authority> authorities = new ArrayList<Authority>();
				authorities.addAll(actor.getUserAccount().getAuthorities());
				String aux=authorities.get(0).getAuthority().toLowerCase();
				socialIdentityService.delete(identity);
				result = new ModelAndView("redirect:../"+aux+"/myProfile.do");
				
		} catch (Throwable oops) {
			result = createEditModelAndView(socialIdentity, "socialIdentity.commit.error");	
			}
			
	
			return result;
	}

	
	
	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
	ModelAndView result;

	result = createEditModelAndView(socialIdentity, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result;
		result = new ModelAndView("socialIdentity/edit");

		Authority authority=actorService.findByPrincipal().getUserAccount().getAuthorities().iterator().next();
		String authoritytext=authority.getAuthority().toLowerCase();
		result.addObject("typeActor",authoritytext);
		
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}
	

}

