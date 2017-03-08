
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AttachmentRepository;
import security.LoginService;
import domain.Attachment;
import domain.Audit;

@Service
@Transactional
public class AttachmentService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private AttachmentRepository	attachmentRepository;


	// Supporting Services ------------------------------------------------------------
	
	@Autowired
	private LoginService	loginService;
	
	@Autowired
	private Validator validator;

	// Constructor --------------------------------------------------------------------

	public AttachmentService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public Attachment create(Audit audit) {
		Attachment result;
		result = new Attachment();
		result.setAudit(audit);
		return result;
	}

	public Collection<Attachment> findAll() {
		Collection<Attachment> result;
		result = attachmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Attachment findOne(int attachmentId) {
		Attachment result;
		result = attachmentRepository.findOne(attachmentId);
		return result;
	}

	@SuppressWarnings("static-access")
	public Attachment save(Attachment attachment) {
		Attachment result;
		
		Assert.notNull(attachment, "El attachment no puede ser nulo");
		Assert.isTrue(loginService.getPrincipal().equals(attachment.getAudit().getAuditor().getUserAccount()));
		Assert.isTrue(attachment.getAudit().getDraftMode());
		result = attachmentRepository.save(attachment);
		return result;
	}
	
	@SuppressWarnings("static-access")
	public void delete(Attachment attachment) {
		Assert.notNull(attachment, "El attachment no puede ser nulo");
		Assert.isTrue(attachment.getId() != 0, "El attachment debe estar antes en la base de datos");
		Assert.isTrue(attachment.getAudit().getDraftMode());
		attachmentRepository.exists(attachment.getId());
		Assert.isTrue(loginService.getPrincipal().equals(attachment.getAudit().getAuditor().getUserAccount()));

		attachmentRepository.delete(attachment);

	}

	

	// Other Bussiness Methods --------------------------------------------------------

	
	public Collection<Attachment> findAllAttachmentsByAudit(Audit audit) {
		Collection<Attachment> result;
		result = attachmentRepository.findAllAttachmentsByAuditId(audit.getId());
		return result;
	}

	public Attachment reconstruct(Attachment attachment, BindingResult binding) {
		Attachment result, original;
		
		if (attachment.getId()==0){
			result = create(attachment.getAudit());
		} else {
			result = new Attachment();
			original = findOne(attachment.getId());
			result.setAudit(original.getAudit());
			result.setVersion(original.getVersion());
		}
		result.setName(attachment.getName());
		result.setUrl(attachment.getUrl());
		
		validator.validate(result, binding);
		
		return result;
	}
}
