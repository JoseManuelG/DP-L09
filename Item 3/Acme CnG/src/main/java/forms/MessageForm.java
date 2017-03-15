
package forms;

import java.util.Collection;
import java.util.LinkedList;

import domain.Actor;
import domain.Attachment;

public class MessageForm {

	private String					title;
	private String					text;
	private Actor					recipient;
	private LinkedList<Attachment>	attachments;


	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}
	public Actor getRecipient() {
		return this.recipient;
	}
	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}
	public Collection<Attachment> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final LinkedList<Attachment> attachments) {
		this.attachments = attachments;
	}
	//----Metodos de uso del formulario----
	//Contructor para que la lista no sea nula
	public MessageForm() {
		this.attachments = new LinkedList<Attachment>();
	}
	//Metodo para añadir un nuevo espacio que rellenar el formulario
	public void addAttachmentSpace() {
		this.attachments.add(null);
	}
	//Metodo para eliminar el ultimo attachment del formulario
	public void removeAttachmentSpace() {
		this.attachments.removeLast();
	}
}
