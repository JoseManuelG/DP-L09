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

@Service
@Transactional
public class MessageService {

	//Managed Repository--------------------------------------------------------------------
		@Autowired
		private MessageRepository	messageRepository;
		
		//Supported Services--------------------------------------------------------------------
		@Autowired
		private Actorservice actorService;
		@Autowired
		private AttachmentService attachmentService;

		@Autowired
		private Validator validator;
		
		
		//Simple CRUD methods------------------------------------------------------------------
		public Message create(Actor recipient){
			Message result = new Message();
			
			result.setRecipient(recipient);
			result.setRecipientName(recipient.getName());
			Actor sender = actorService.findByPrincipal();
			result.setSender(sender);
			result.setSenderName(sender.getName());
	
			result.setIsSender(false);
			return result;
		}
		
		
		//no se si es necesario el findAll, porque apra las listas se usará querys
		public Collection<Message> findAll(){
			return messageRepository.findAll();
		}
		
		public Message findOne(Integer messageId){
			
			Assert.isNull(messageId, "No Puedes Encontrar un mensaje sin ID");
			Assert.isTrue(messageId<=0,"La Id no es valida");
			
			
			Message result = messageRepository.findOne(messageId);
			
			return result;
		}
		
		
		
		public Message save(Message message,Collection<Attachment> attachments){
			Message result;
			Message copyMessage;
			Message savedCopyMessage;
			
			
			Assert.notNull(message.getRecipient()			,"El mensaje debe tener un destinatario");
			Assert.notNull(message.getRecipient().getName()	,"El mensaje debe tener el nombre del destinatario");
			Assert.hasText(message.getRecipient().getName()	,"El mensaje debe tener el nombre del destinatario");
			Assert.notNull(message.getSender()				,"El mensaje debe tener un remitente");
			Assert.hasText(message.getSender().getName()	,"El mensaje debe tener el nombre del remitente");

			Assert.hasText(message.getText()				,"El mensaje debe tener un cuerpo");
			Assert.hasText(message.getTitle()				,"El mensaje debe tener un titulo");
			Assert.notNull(message.getSendingMoment()		,"El mensaje debe tener la fecha de envio");
			Actor sender = actorService.findByPrincipal();

			Assert.isTrue(!sender.equals(message.getSender()),"El remitente debe ser el mismo que esta conectado");
			
			 // Creamos copia del mensaje en un segundo mensaje poniendole;
			 

			copyMessage = copyMessage(message);
			result=messageRepository.save(message);
			attachmentService.AñadirAttachments(attachments, result);
			savedCopyMessage=messageRepository.save(copyMessage);
			attachmentService.AñadirAttachments(attachments, savedCopyMessage);
			return result;
		}
		
		
		private Message copyMessage(Message message){
			Message result = new Message();
			
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
		
		//Other Bussnisnes methods------------------------------------------------------------
		//Devuelve los mensajes que ha enviado el actor
		public List<Message> findSendedMessageOfPrincipal(){
			int senderId = actorService.findByPrincipal().getId();
			List<Message> result = messageRepository.findSendedMessageOfActor(senderId);
			return result;
		}

		//Devuelve los mensajes que ha recibido el actor	
		public List<Message> findReceivedMessageOfPrincipal(){
			int recipientId = actorService.findByPrincipal().getId();
			List<Message> result = messageRepository.findReceivedMessageOfActor(recipientId);
			return result;
	}
		
		public Message reconstruct(MessageForm messageForm, BindingResult binding) {
			Message result= this.create(messageForm.getRecipient());
			result.setText(messageForm.getText());
			result.setTitle(messageForm.getTitle());
			result.setSendingMoment(new Date(System.currentTimeMillis()-1000));
			validator.validate(result, binding);
			
		}
}
