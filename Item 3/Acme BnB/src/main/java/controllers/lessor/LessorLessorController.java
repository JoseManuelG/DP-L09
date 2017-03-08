package controllers.lessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.LessorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/lessor")
public class LessorLessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private LessorService lessorService;

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/fee", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		double fee;
		fee = lessorService.findFeeFromPrincipal();
		result = new ModelAndView("lessor/fee");
		result.addObject("fee", fee);

		return result;
	}

}