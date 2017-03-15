
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Attachment;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private MessageRepository	messageRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ActorService		actorService;
	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods------------------------------------------------------------------
	public Message create(final Actor recipient) {
		final Message result = new Message();

		result.setRecipient(recipient);
		result.setRecipientName(recipient.getName());
		final Actor sender = this.actorService.findActorByPrincipal();
		result.setSender(sender);
		result.setSenderName(sender.getName());
		result.setSendingMoment(new Date(System.currentTimeMillis() - 100));

		result.setIsSender(false);
		return result;
	}

	public Message findOne(final int messageId) {

		//Assert.isNull(messageId, "No Puedes Encontrar un mensaje sin ID");
		//Assert.isTrue(messageId <= 0, "La Id no es valida");

		final Message result = this.messageRepository.findOne(messageId);

		return result;
	}

	public Message save(final Message message, final Collection<Attachment> attachments) {
		Message result;
		Message copyMessage;
		Message savedCopyMessage;

		//TODO: el assert de getSender sobra porque se pone el principal desde el servicio,
		// y no lo puede modificar de ninguna forma el usuario, y los dos del final igual,
		// no pueden ser modificados por el usuario, los hace el servicio, el que si que hace
		// falta es el de notNull recipient porque eso es lo que se puede cambiar en la vista

		Assert.notNull(message.getRecipient(), "El mensaje debe tener un destinatario");
		//		Es recipientName del message no el name del recipient, aun asi esto lo hace el @NotNull y @NotBlank
		//		Assert.notNull(message.getRecipient().getName(), "El mensaje debe tener el nombre del destinatario");
		//		Assert.hasText(message.getRecipient().getName(), "El mensaje debe tener el nombre del destinatario");

		Assert.notNull(message.getSender(), "El mensaje debe tener un remitente");
		//		Es senderName del message no el name del sender, aun asi esto lo hace el @NotNull y @NotBlank
		//		Assert.notNull(message.getSender().getName(), "El mensaje debe tener el nombre del remitente");
		//		Assert.hasText(message.getSender().getName(), "El mensaje debe tener el nombre del remitente");

		//		Esto lo hace el @NotBlank
		//		Assert.hasText(message.getText(), "El mensaje debe tener un cuerpo");
		//		Assert.hasText(message.getTitle(), "El mensaje debe tener un titulo");

		//		Esto lo hace el @NotNull
		//		Assert.notNull(message.getSendingMoment(), "El mensaje debe tener la fecha de envio");
		final Actor sender = this.actorService.findActorByPrincipal();

		// tenia un ! delante de sender y estaba petando por eso, porque comprobaba lo contrario
		Assert.isTrue(sender.equals(message.getSender()), "El remitente debe ser el mismo que esta conectado");
		Assert.isTrue(message.getId() == 0, "No puedes editar un mensaje");

		// Creamos copia del mensaje en un segundo mensaje;

		copyMessage = this.copyMessage(message);
		result = this.messageRepository.save(message);
		this.attachmentService.AñadirAttachments(attachments, result);
		savedCopyMessage = this.messageRepository.save(copyMessage);
		this.attachmentService.AñadirAttachments(attachments, savedCopyMessage);
		return result;
	}

	private Message copyMessage(final Message message) {
		final Message result = new Message();

		result.setRecipient(message.getRecipient());
		result.setRecipientName(message.getRecipient().getName());

		result.setSender(message.getSender());
		result.setSenderName(message.getSender().getName());
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setSendingMoment(message.getSendingMoment());
		result.setIsSender(true);

		return result;
	}

	public void delete(final Message message) {

		Assert.isNull(message, "El objeto no puede ser nulo");
		Assert.isTrue(message.getId() == 0, "El objeto no puede tener id 0");

		//TODO: no funciona el metodo
		//this.attachmentService.deleteAttachmentsOfMessage(message);
		this.messageRepository.delete(message);

	}
	//Other Bussnisnes methods------------------------------------------------------------
	//Devuelve los mensajes que ha enviado el actor
	public List<Message> findSentMessageOfPrincipal() {
		final int senderId = this.actorService.findActorByPrincipal().getId();
		final List<Message> result = this.messageRepository.findSentMessageOfActor(senderId);
		return result;
	}

	//Devuelve los mensajes que ha recibido el actor	
	public List<Message> findReceivedMessageOfPrincipal() {
		final int recipientId = this.actorService.findActorByPrincipal().getId();
		final List<Message> result = this.messageRepository.findReceivedMessageOfActor(recipientId);
		return result;
	}

	public Message reconstruct(final MessageForm messageForm, final BindingResult binding) {
		final Message result = this.create(messageForm.getRecipient());
		result.setText(messageForm.getText());
		result.setTitle(messageForm.getTitle());
		this.validator.validate(result, binding);

		for (final Attachment a : messageForm.getAttachments())
			this.validator.validate(a, binding);

		return result;

	}
}
