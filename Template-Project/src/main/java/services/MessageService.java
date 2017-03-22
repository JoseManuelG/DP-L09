
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
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
	public Message create(final int recipientId) {
		final Message result = new Message();
		final Actor recipient;

		recipient = this.actorService.findOne(recipientId);
		Assert.notNull(recipient);
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

		Assert.notNull(messageId, "No Puedes Encontrar un mensaje sin ID");
		Assert.isTrue(messageId >= 0, "La Id no es valida");

		final Message result = this.messageRepository.findOne(messageId);
		//a�adido  assert para comprobar que el mensaje es suyo,
		//su copia ya sea la de enviado cuando eres el que envia o al recibido
		//el findOne se usa en view, reply, forward... etc asi que cubre todo

		Assert.notNull(result);

		if (result.getSender() == null && result.getRecipient() != null)
			Assert.isTrue(result.getRecipient().equals(this.actorService.findActorByPrincipal()) && !result.getIsSender());
		else if (result.getSender() != null && result.getRecipient() == null)
			Assert.isTrue(result.getSender().equals(this.actorService.findActorByPrincipal()) && result.getIsSender());
		else
			Assert.isTrue((result.getSender().equals(this.actorService.findActorByPrincipal()) && result.getIsSender()) || result.getRecipient().equals(this.actorService.findActorByPrincipal()) && !result.getIsSender());

		return result;
	}

	public Message save(final Message message, final Collection<Attachment> attachments) {
		Message result;
		Message copyMessage;
		Message savedCopyMessage;

		Assert.notNull(message.getRecipient(), "El mensaje debe tener un destinatario");

		Assert.notNull(message.getSender(), "El mensaje debe tener un remitente");

		final Actor sender = this.actorService.findActorByPrincipal();

		Assert.isTrue(sender.equals(message.getSender()), "El remitente debe ser el mismo que esta conectado");
		Assert.isTrue(message.getId() == 0, "No puedes editar un mensaje");

		// Creamos copia del mensaje en un segundo mensaje;

		copyMessage = this.copyMessage(message);
		//Se almacena el mensaje original y sus attachments
		result = this.messageRepository.save(message);
		this.attachmentService.addAttachments(attachments, result);
		//Se almacena el mensaje copia y sus attachments
		savedCopyMessage = this.messageRepository.save(copyMessage);
		this.attachmentService.addAttachments(attachments, savedCopyMessage);
		return result;
	}
	//Simplemente crea un mensaje nuevo y le settea todos los datos del mensaje de entrada menos el isSender.
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

	public void delete(final int messageId) {

		//en vez de pasar message pasas la id para no tocar en el controlador, y en el findOne
		//ya se hacen los asserts
		final Message message = this.findOne(messageId);

		Assert.notNull(message);

		this.attachmentService.deleteAttachmentsOfMessage(message);
		this.messageRepository.delete(message);

	}
	public void delete(final Collection<Message> Messages) {
		this.delete(Messages);
	}
	public void flush() {
		this.messageRepository.flush();
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
		final Message result = this.create(messageForm.getRecipient().getId());
		result.setText(messageForm.getText());
		result.setTitle(messageForm.getTitle());
		this.validator.validate(result, binding);

		if (!binding.hasErrors())
			for (final Attachment a : messageForm.getAttachments()) {
				a.setMessage(result);
				this.validator.validate(a, binding);
			}
		return result;

	}

	public List<Message> findAllMessageOfActor(final int ActorId) {
		return this.messageRepository.findAllMessageOfActor(ActorId);
	}
	//Basicamente te hace el MessageForm relleno del mensaje que has pasado, luego en la vista seleccionarias a quien mandarselo 
	//y despues pasarias el mensaje al save
	public MessageForm forwardMessage(final int messageId) {
		//Lo he cambiado para que pida messageId en vez de message para no tener que 
		//hacer en controlador cosas de servicios 
		final Message message = this.findOne(messageId);
		final MessageForm result = new MessageForm();
		final LinkedList<Attachment> attachments = new LinkedList<Attachment>();
		result.setAction(2);
		attachments.addAll(this.attachmentService.copyAttachments(message));
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setAttachments(attachments);
		return result;
	}
	//Para responder el mensaje, 
	public MessageForm replyMessage(final int messageId) {
		//Lo he cambiado para que pida messageId en vez de actorId para no tener que 
		//hacer en controlador cosas de servicios
		final MessageForm result = new MessageForm();
		result.setAction(1);
		final Message message = this.findOne(messageId);
		Assert.notNull(message.getSender());
		final Actor recipient = this.actorService.findOne(message.getSender().getId());
		result.setRecipient(recipient);
		return result;
	}

	//Dashboard - The average of messages sent per actor.

	public Double avgMessagesSentPerActor() {
		Double result;
		result = this.messageRepository.avgMessagesSentPerActor();
		return result;
	}

	//Dashboard - The min of messages sent per actor. 

	public Long minMessagesSentPerActor() {
		List<Long> messages;
		final Long result;
		messages = this.messageRepository.minMessagesSentPerActor();
		result = messages.iterator().next();
		return result;
	}

	//Dashboard - The maximum number of messages sent per actor
	public Long maxMessagesSentPerActor() {
		List<Long> messages;
		Long result;
		messages = this.messageRepository.maxMessagesSentPerActor();
		if (!messages.isEmpty())
			result = messages.iterator().next();
		else
			result = (long) 0;
		return result;
	}

	//Dashboard - The average of messages received per actor.

	public Double avgMessagesReceivedPerActor() {
		Double result;
		result = this.messageRepository.avgMessagesReceivedPerActor();
		return result;
	}

	//Dashboard - The minimum of messages received per actor.
	public Long minMessagesReceivedPerActor() {
		List<Long> messages;
		final Long result;
		messages = this.messageRepository.minMessagesReceivedPerActor();
		if (!messages.isEmpty())
			result = messages.iterator().next();
		else
			result = (long) 0;

		return result;
	}
	//Dashboard - The maximum number of messages received per actor. Part2
	public Long maxMessagesReceivedPerActor() {
		List<Long> messages;
		final Long result;
		messages = this.messageRepository.maxMessagesReceivedPerActor();
		if (!messages.isEmpty())
			result = messages.iterator().next();
		else
			result = (long) 0;
		return result;
	}

	//Dashboard - The actors who have sent more messages.
	public Actor actorSentMoreMessages() {
		List<Actor> actors;
		final Actor result;
		actors = this.messageRepository.actorSentMoreMessages();
		if (!actors.isEmpty())
			result = actors.iterator().next();
		else
			result = null;
		return result;
	}

	//Dashboard - The actors who have received more messages.
	public Actor actorReceivedMoreMessages() {
		List<Actor> actors;
		final Actor result;
		actors = this.messageRepository.actorSentMoreMessages();
		if (!actors.isEmpty())
			result = actors.iterator().next();
		else
			result = null;
		return result;
	}

	public void deleteActor(final Actor actor) {
		final Collection<Message> messages = new ArrayList<Message>();
		final Collection<Message> messages2 = new ArrayList<Message>();
		messages.addAll(this.messageRepository.findSentMessageOfActor(actor.getId()));
		messages.addAll(this.messageRepository.findReceivedMessageOfActor(actor.getId()));
		this.attachmentService.deleteActor(actor);
		this.messageRepository.delete(messages);
		messages.clear();
		messages2.addAll(this.messageRepository.findReceivedMessageOfActor2(actor.getId()));
		for (final Message message : messages2) {
			message.setRecipient(null);
			messages.add(message);
		}
		//Vaciado de la colleci�n para pasar al segundo for
		messages2.clear();
		//------------------------------------------------
		messages2.addAll(this.messageRepository.findSentMessageOfActor2(actor.getId()));
		for (final Message message : messages2) {
			message.setSender(null);
			messages.add(message);
		}
		this.messageRepository.save(messages);
	}

}