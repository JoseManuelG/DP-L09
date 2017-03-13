package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

		
		
		//Simple CRUD methods------------------------------------------------------------
		public Message create(Actor sender, Actor recipient){
			Message result = new Message();
			
			result.setRecipient(recipient);
			result.setRecipientName(recipient.getName());

			result.setSender(sender);
			result.setSenderName(sender.getName());
			
			
			//TODO Falta los attachments habria que settearlo
			//result.setAttachments(new Collection<Attachments>());
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
		
		
		
		public Message Save(Message message){
			Message result;
			
			/*TODO
			 * Creamos copia del mensaje en un segundo mensaje poniendole el isCopy a true y guardariamos ambos;
			 * 
			 */
			result=messageRepository.save(message);
			return result;
		}
}
