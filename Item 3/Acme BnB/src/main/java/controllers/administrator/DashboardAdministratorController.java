
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttributeService;
import services.AuditService;
import services.BookService;
import services.FinderService;
import services.InvoiceService;
import services.LessorService;
import services.PropertyService;
import services.TenantService;
import controllers.AbstractController;
import domain.Lessor;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BookService			bookService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private TenantService		tenantService;

	@Autowired
	private InvoiceService		invoiceService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private AttributeService	attributeService;


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/administrator/dashboard");
		//Queries 1-11
		result.addObject("AverageAcceptedBooksPerLessor", bookService.getAverageAcceptedBooksPerLessor());
		result.addObject("AverageDeniedBooksPerLessor", bookService.getAverageDeniedBooksPerLessor());
		result.addObject("AverageAcceptedBooksPerTenant", bookService.getAverageAcceptedBooksPerTenant());
		result.addObject("AverageDeniedBooksPerTenant", bookService.getAverageDeniedBooksPerTenant());
		result.addObject("LessorWithMoreAcceptedBooks", lessorService.getLessorWithMoreAcceptedBooks());
		result.addObject("LessorWithMoreDeniedBooks", lessorService.getLessorWithMoreDeniedBooks());
		result.addObject("LessorWithMorePendingBooks", lessorService.getLessorWithMorePendingBooks());
		result.addObject("TenantWithMoreAcceptedBooks", tenantService.getTenantWithMoreAcceptedBooks());
		result.addObject("TenantWithMoreDeniedBooks", tenantService.getTenantWithMoreDeniedBooks());
		result.addObject("TenantWithMorePendingBooks", tenantService.getTenantWithMorePendingBooks());
		result.addObject("LessorWithMaxAcceptedVersusTotalBooksRatio", lessorService.getLessorWithMaxAcceptedVersusTotalBooksRatio());
		result.addObject("LessorWithMinAcceptedVersusTotalBooksRatio", lessorService.getLessorWithMinAcceptedVersusTotalBooksRatio());
		result.addObject("TenantWithMaxAcceptedVersusTotalBooksRatio", tenantService.getTenantWithMaxAcceptedVersusTotalBooksRatio());
		result.addObject("TenantWithMinAcceptedVersusTotalBooksRatio", tenantService.getTenantWithMinAcceptedVersusTotalBooksRatio());
		result.addObject("AverageResultsPerFinder", finderService.getAverageResultsPerFinder());
		result.addObject("MinimumResultsPerFinder", finderService.getMinimumResultsPerFinder());
		result.addObject("MaximumResultsPerFinder", finderService.getMaximumResultsPerFinder());
		//Queries 17-21 
		result.addObject("MinimumSocialIdentitiesPerActor", actorService.getMinimumSocialIdentitiesPerActor());
		result.addObject("AverageSocialIdentitiesPerActor", actorService.getAverageSocialIdentitiesPerActor());
		result.addObject("MaximumSocialIdentitiesPerActor", actorService.getMaximumSocialIdentitiesPerActor());
		result.addObject("MinimumInvoicesPerTenant", invoiceService.getMinimumInvoicesPerTenant());
		result.addObject("AverageInvoicesPerTenant", invoiceService.getAverageInvoicesPerTenant());
		result.addObject("MaximumInvoicesPerTenant", invoiceService.getMaximumInvoicesPerTenant());
		result.addObject("TotalDueMoneyOfInvoices", invoiceService.getTotalDueMoneyOfInvoices());
		result.addObject("AverageRequestsWithAuditsVersusNoAudits", bookService.getAverageRequestsWithAuditsVersusNoAudits());
		result.addObject("MinimumAuditsPerProperty", auditService.getMinimumAuditsPerProperty());
		result.addObject("AverageAuditsPerProperty", auditService.getAverageAuditsPerProperty());
		result.addObject("MaximumAuditsPerProperty", auditService.getMaximumAuditsPerProperty());

		result.addObject("requestURI", "dashboard/administrator/dashboard.do");

		return result;
	}

	@RequestMapping(value = "/lessors", method = RequestMethod.GET)
	public ModelAndView lessors() {
		ModelAndView result;

		result = new ModelAndView("lessor/list");

		result.addObject("lessors", lessorService.findAll());

		result.addObject("requestURI", "dashboard/administrator/lessors.do");

		return result;
	}

	@RequestMapping(value = "/propertiesOrderedByAudits", method = RequestMethod.GET)
	public ModelAndView propertiesOrderedByAudits(@RequestParam(required = true) int lessorId) {
		ModelAndView result;
		Lessor lessor;

		result = new ModelAndView("property/list");
		lessor = lessorService.findOne(lessorId);

		result.addObject("properties", propertyService.findPropertiesByLessorByNumberOfAudits(lessorId));
		result.addObject("requestURI", "dashboard/administrator/propertiesOrderedByAudits.do?lessorId=" + lessorId);
		result.addObject("lessor", lessor);
		result.addObject("queryNumber", 1);

		return result;
	}

	@RequestMapping(value = "/propertiesOrderedByBooks", method = RequestMethod.GET)
	public ModelAndView propertiesOrderedByBooks(@RequestParam(required = true) int lessorId) {
		ModelAndView result;
		Lessor lessor;

		result = new ModelAndView("property/list");
		lessor = lessorService.findOne(lessorId);

		result.addObject("properties", propertyService.findPropertiesByLessorOrderedByRequestNumber(lessorId));
		result.addObject("requestURI", "dashboard/administrator/propertiesOrderedByBooks.do?lessorId=" + lessorId);
		result.addObject("lessor", lessor);
		result.addObject("queryNumber", 2);

		return result;
	}

	@RequestMapping(value = "/propertiesOrderedByAcceptedBooks", method = RequestMethod.GET)
	public ModelAndView propertiesOrderedByAcceptedBooks(@RequestParam(required = true) int lessorId) {
		ModelAndView result;
		Lessor lessor;

		result = new ModelAndView("property/list");
		lessor = lessorService.findOne(lessorId);

		result.addObject("properties", propertyService.findPropertiesByLessorWithAcceptedBooks(lessorId));
		result.addObject("requestURI", "dashboard/administrator/propertiesOrderedByAcceptedBooks.do?lessorId=" + lessorId);
		result.addObject("lessor", lessor);
		result.addObject("queryNumber", 3);

		return result;
	}

	@RequestMapping(value = "/propertiesOrderedByDeniedBooks", method = RequestMethod.GET)
	public ModelAndView propertiesOrderedByDeniedBooks(@RequestParam(required = true) int lessorId) {
		ModelAndView result;
		Lessor lessor;

		result = new ModelAndView("property/list");
		lessor = lessorService.findOne(lessorId);

		result.addObject("properties", propertyService.findPropertiesByLessorWithDenieBooks(lessorId));
		result.addObject("requestURI", "dashboard/administrator/propertiesOrderedByDeniedBooks.do?lessorId=" + lessorId);
		result.addObject("lessor", lessor);
		result.addObject("queryNumber", 4);

		return result;
	}

	@RequestMapping(value = "/propertiesOrderedByPendingBooks", method = RequestMethod.GET)
	public ModelAndView propertiesOrderedByPendingBooks(@RequestParam(required = true) int lessorId) {
		ModelAndView result;
		Lessor lessor;

		result = new ModelAndView("property/list");
		lessor = lessorService.findOne(lessorId);

		result.addObject("properties", propertyService.findPropertiesByLessorWithPendingBooks(lessorId));
		result.addObject("requestURI", "dashboard/administrator/propertiesOrderedByPendingBooks.do?lessorId=" + lessorId);
		result.addObject("lessor", lessor);
		result.addObject("queryNumber", 5);

		return result;
	}

	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public ModelAndView attributes() {
		ModelAndView result;

		result = new ModelAndView("attribute/administrator/list");

		result.addObject("attributes", attributeService.getAttributesByFrequence());

		result.addObject("dashboard", "");

		result.addObject("requestURI", "dashboard/administrator/attributes.do");

		return result;
	}
}
