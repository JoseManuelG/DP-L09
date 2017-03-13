package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

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

			@Autowired
			private Validator validator;
			
			//Simple CRUD methods------------------------------------------------------------
			public Attachment create(Message message){
				Attachment result = new Attachment();
				result.setMessage(message);
				return result;
			}
			
			
			public Attachment findOne(Integer AttachmentId){
				Attachment result = attachmentRepository.findOne(AttachmentId);
				return result;
			}
			
			public Collection<Attachment> findAll(){
				Collection<Attachment> result=attachmentRepository.findAll();
				return result;
			}
			public Attachment save(Attachment attachment){
				Attachment result;
				result= attachmentRepository.save(attachment);
				return result;
			}
}
