package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import services.LessorService;
import services.PropertyService;
import services.SocialIdentityService;
import domain.Comment;
import domain.Commentable;
import domain.Lessor;

@Controller
@RequestMapping("/lessor")
public class LessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private LessorService lessorService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SocialIdentityService socialIdentityService;
	
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private CommentService commentService;


	// Constructors -----------------------------------------------------------

	public LessorController() {
		super();
	}



	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("lessor/view");
		
		
		
		Lessor lessor  =  lessorService.findByPrincipal();
		
	
		result.addObject("lessor", lessor);
		result.addObject("properties", lessor.getLessorProperties());
		result.addObject("comments", commentService.findAllCommentsOfACustomer(lessor));
		result.addObject("socialIdentities",lessor.getSocialIdentities());
		result.addObject("requestURI","lessor/myProfile.do");
		result.addObject("esMiPerfil",true);
		result.addObject("puedoComentar",true);
		return result;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(int lessorId) {
		ModelAndView result;
		result = new ModelAndView("lessor/view");
		
		
		boolean puedoComentar = false;
		Lessor lessor  =  lessorService.findOne(lessorId);
		try{
		Comment comment= commentService.create();
		comment.setRecipient((Commentable) lessor);
		comment.setSender(customerService.findActorByPrincial());
		puedoComentar=commentService.validComment(comment);
		}catch (Throwable oops) {
			puedoComentar=false;
		}
		result.addObject("lessor", lessor);
		result.addObject("properties", propertyService.findPropertiesByLessor(lessor));
		result.addObject("comments", commentService.findAllCommentsOfACustomer(lessor));
		result.addObject("requestURI","lessor/view.do");
		result.addObject("socialIdentities",socialIdentityService.findSocialIdentitiesByLessorId(lessor));
		result.addObject("esMiPerfil",false);
		result.addObject("puedoComentar",puedoComentar);

		return result;
	}

	// Edit ---------------------------------------------------------------
/*
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Lessor lessor  = lessorService.findByPrincipal();
		ActorForm actorForm;
		result = createEditModelAndView(lessor);
		return result;
	}
	
	// Save -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( ActorForm lessor, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(lessor);
		} else {
			try {
				lessorService.save(lessor);
				result = new ModelAndView("../");
			
			} catch (Throwable oops) {
				result = createEditModelAndView(lessor, "lessor.commit.error");
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Lessor lessor, BindingResult binding) {
		ModelAndView result;

		try {			
			lessorService.delete(lessor);
			result = new ModelAndView("../");
		} catch (Throwable oops) {
			result = createEditModelAndView(lessor, "lessor.commit.error");
		}

		return result;
	}
*/
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Lessor lessor) {
		ModelAndView result;

		result = createEditModelAndView(lessor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Lessor lessor, String message) {
		ModelAndView result;
		result = new ModelAndView("lessor/edit");
		result.addObject("lessor", lessor);
		result.addObject("message", message);

		return result;
	}

}