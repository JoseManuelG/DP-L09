
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	sender;
	private String	recipient;
	private Date	sendingMoment;
	private String	title;
	private String	text;


	@NotBlank
	@SafeHtml
	public String getSender() {
		return this.sender;
	}

	public void setSender(final String sender) {
		this.sender = sender;
	}

	@NotBlank
	@SafeHtml
	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final String recipient) {
		this.recipient = recipient;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendingMoment() {
		return this.sendingMoment;
	}

	public void setSendingMoment(final Date sendingMoment) {
		this.sendingMoment = sendingMoment;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Attachment>	attachments;
	private Actor					actor;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "message", cascade = CascadeType.REMOVE)
	public Collection<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<Attachment> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
