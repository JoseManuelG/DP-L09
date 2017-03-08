package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import controllers.AbstractController;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/tenant")
public class InvoiceTenantController extends AbstractController {
	
	@Autowired
	private InvoiceService invoiceService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int invoiceId) {
		ModelAndView result;
		Invoice invoice;
		
		invoice = invoiceService.findOne(invoiceId);
		invoiceService.checkOwnerIsPrincipal(invoice);
		
		result = new ModelAndView("invoice/view");
		result.addObject("invoice",invoice);
		
		return result;
	}
}