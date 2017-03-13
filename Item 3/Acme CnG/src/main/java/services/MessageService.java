package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
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
		
		
		//Simple CRUD methods------------------------------------------------------------
		public Message create(Actor recipient){
			Message result = new Message();
			
			result.setRecipient(recipient);
			result.setRecipientName(recipient.getName());
			Actor sender = actorService.findByPrincipal();
			result.setSender(sender);
			result.setSenderName(sender.getName());
			
			
			
			//TODO Falta el booleano isCopy
			//result.setIsCopy(false);
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
		
		
		
		public Message save(Message message){
			Message result;
			Assert.isNull(message.getRecipient(),"El mensaje debe tener un destinatario");
			Assert.isNull(message.getRecipient().getName(),"El mensaje debe tener el nombre del destinatario");
			Assert.hasText(message.getRecipient().getName(),"El mensaje debe tener el nombre del destinatario");
			Assert.isNull(message.getSender(),"El mensaje debe tener un remitente");
			Assert.hasText(message.getSender().getName(),"El mensaje debe tener el nombre del remitente");

			Assert.hasText(message.getText(), "El mensaje debe tener un cuerpo");
			Assert.hasText(message.getTitle(), "El mensaje debe tener un titulo");

		//	Assert.isNull(message.getSendingMoment(), "El mensaje debe tener la fecha de envio");
			
			/*TODO
			 * Creamos copia del mensaje en un segundo mensaje poniendole el isCopy a true y guardariamos ambos;
			 * 
			 */
			
			message.setSendingMoment(new Date(System.currentTimeMillis()-1000));
			Message copyMessage = copyMessage(message);
			result=messageRepository.save(message);
			messageRepository.save(copyMessage);
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
			
			
			//result.setIsCopy(true);
			return result;
		}
		//Devuelve los mensajes que ha enviado el actor
		public List<Message> findSendedMessageOfActor(int senderId){
			List<Message> result = messageRepository.findSendedMessageOfActor(senderId);
			return result;
		}

		//Devuelve los mensajes que ha recivido el actor	
		public List<Message> findReceivedMessageOfActor(int recipientId){
			List<Message> result = messageRepository.findReceivedMessageOfActor(recipientId);
			return result;
	}
}
