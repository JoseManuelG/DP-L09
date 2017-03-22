
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
	//Para saber si escribes nuevo, respondes o reenvias;
	//0= escribir, 1= responder, 2= reenviar
	private Integer					action;


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

	public Integer getAction() {
		return this.action;
	}
	public void setAction(final Integer action) {
		this.action = action;
	}
	//----Metodos de uso del formulario----
	//Contructor para que la lista no sea nula
	public MessageForm() {
		this.attachments = new LinkedList<Attachment>();
		this.action = 0;
	}
	//Metodo para añadir un nuevo espacio que rellenar el formulario
	public void addAttachmentSpace() {
		//añadia un null y le he puesto que añada una ttachment vacio porque añadir 
		//null no tiene mucho sentido
		this.attachments.add(new Attachment());
	}
	//Metodo para eliminar el ultimo attachment del formulario
	public void removeAttachmentSpace() {
		this.attachments.removeLast();
	}

}
