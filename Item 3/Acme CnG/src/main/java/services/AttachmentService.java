
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import domain.Attachment;
import domain.Message;

@Service
@Transactional
public class AttachmentService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private AttachmentRepository	attachmentRepository;


	//Supported Services--------------------------------------------------------------------
	//Simple CRUD methods------------------------------------------------------------
	public Attachment create(final Message message) {
		final Attachment result = new Attachment();
		result.setMessage(message);
		return result;
	}

	public Attachment findOne(final Integer AttachmentId) {
		final Attachment result = this.attachmentRepository.findOne(AttachmentId);
		return result;
	}

	public Collection<Attachment> findAll() {
		final Collection<Attachment> result = this.attachmentRepository.findAll();
		return result;
	}

	public Attachment save(final Attachment attachment) {
		Attachment result;
		result = this.attachmentRepository.save(attachment);
		return result;
	}

	public void delete(final Attachment attachment) {
		Assert.isNull(attachment, "El objeto no puede ser nulo");
		Assert.isTrue(attachment.getId() == 0, "El objeto no puede tener id 0");
		this.attachmentRepository.delete(attachment);

	}

	//Other Bussnisnes methods------------------------------------------------------------
	public List<Attachment> findAttachmentsOfMessage(final Message message) {
		final List<Attachment> result = this.attachmentRepository.findAttachmentsOfMessage(message.getId());
		return result;
	}

	public void addAttachments(final Collection<Attachment> attachments, final Message message) {
		final Attachment attachment = this.create(message);
		for (final Attachment a : attachments) {
			attachment.setName(a.getName());
			attachment.setUrl(a.getUrl());
			this.save(attachment);
		}

	}

	public void deleteAttachmentsOfMessage(final Message message) {
		final List<Attachment> aux = this.findAttachmentsOfMessage(message);
		for (final Attachment attachment : aux)
			this.delete(attachment);

	}

}
