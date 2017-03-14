package forms;

import java.util.Collection;

import domain.Actor;
import domain.Attachment;

public class MessageForm {
	
	private String	title;
	private String	text;
	private Actor	recipient;
	private Collection<Attachment> attachments;
	


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	public Collection<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	
}
