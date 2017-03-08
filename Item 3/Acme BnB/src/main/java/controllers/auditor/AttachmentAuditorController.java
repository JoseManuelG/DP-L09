package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.AttachmentService;
import services.AuditService;
import controllers.AbstractController;
import domain.Attachment;
import domain.Audit;

@Controller
@RequestMapping("/attachment")
public class AttachmentAuditorController extends AbstractController {

	@Autowired
	private AttachmentService attachmentService ;

	@Autowired
	private AuditService auditService ;
	
	
	// List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int auditId) {
		ModelAndView result;
		Collection<Attachment> attachments;
		Audit audit;
		audit =auditService.findOne(auditId);
		
		attachments = attachmentService.findAllAttachmentsByAudit(audit);
		result = new ModelAndView("attachment/list");
		result.addObject("attachments",attachments);
		result.addObject("audit",audit);
		result.addObject("requestURI","attachment/list.do");
		
		return result;
	}
	
	// Create --------------------------------------------------------------------
		@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int auditId) {
			ModelAndView result;
			Attachment attachment;
			Audit audit;
			
			audit = auditService.findOne(auditId);
			attachment = attachmentService.create(audit);
			attachment.setAudit(audit);
			result = createEditModelAndView(attachment);
			return result;
			
		}
			
		// Edit ---------------------------------------------------------------
		@RequestMapping(value="/auditor/edit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam int attachmentId){
			ModelAndView result;
			Attachment attachment = attachmentService.findOne(attachmentId);
			result = createEditModelAndView(attachment);
			return result;
		}
		
		// Save ---------------------------------------------------------------
		@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
		public @ResponseBody ModelAndView save(Attachment attachment, BindingResult binding) {
			ModelAndView result;
			Attachment attachmentResult;
			
			attachmentResult = attachmentService.reconstruct(attachment, binding);
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(attachment);
			} else {
				try {
					attachmentService.save(attachmentResult);		
					result = new ModelAndView("redirect:../../audit/view.do?auditId="+attachment.getAudit().getId());
				} catch (Throwable oops) {
					result = createEditModelAndView(attachment, "attachment.commit.error");				
				}
			}
				return result;
		}
		
		// Delete -------------------------------------------------------------
		
		@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Attachment attachment, BindingResult binding) {
			ModelAndView result;
				try {			
				attachmentService.delete(attachment);
				result = new ModelAndView("redirect:../../audit/view.do?auditId="+attachment.getAudit().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(attachment, "attachment.commit.error");
			}
				return result;
		}
		
		// View ---------------------------------------------------------------
		
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public ModelAndView view(@RequestParam(required = true) Integer attachmentId) {
			ModelAndView result;
			result = new ModelAndView("attachment/view");
			Attachment attachment  = attachmentService.findOne(attachmentId);
			result.addObject("attachment", attachment);
			return result;
		}
		
		
		// Ancillary methods ------------------------------------------------------
			protected ModelAndView createEditModelAndView(Attachment attachment) {
			ModelAndView result;
				result = createEditModelAndView(attachment, null);
				return result;
		}
			protected ModelAndView createEditModelAndView(Attachment attachment, String message) {
			ModelAndView result;
			result = new ModelAndView("attachment/auditor/edit");
			result.addObject("attachment",attachment);
			result.addObject("message", message);
			return result;
		}
}

