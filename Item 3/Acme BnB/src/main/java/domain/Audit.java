
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(indexes = {@Index(columnList = "draftMode")})
public class Audit extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date	writingMoment;
	private String	text;
	private Boolean	draftMode;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWritingMoment() {
		return writingMoment;
	}

	public void setWritingMoment(Date writingMoment) {
		this.writingMoment = writingMoment;
	}

	@SafeHtml
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@NotNull
	public Boolean getDraftMode() {
		return draftMode;
	}

	public void setDraftMode(Boolean draftMode) {
		this.draftMode = draftMode;
	}


	// Relationships ----------------------------------------------------------

	private Auditor					auditor;
	private Property				property;
	private Collection<Attachment>	attachments;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "audit", cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE
	})
	public Collection<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<Attachment> attachments) {
		this.attachments = attachments;
	}

}
