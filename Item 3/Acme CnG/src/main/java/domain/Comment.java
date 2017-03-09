
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	text;
	private int		stars;
	private Date	postMoment;


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

	@NotNull
	@Range(min = 0, max = 5)
	public int getStars() {
		return this.stars;
	}

	public void setStars(final int stars) {
		this.stars = stars;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostMoment() {
		return this.postMoment;
	}
	public void setPostMoment(final Date dateCreation) {
		this.postMoment = dateCreation;
	}


	// Relationships ----------------------------------------------------------

	private Customer	customer;
	private Commentable	commentable;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getSender() {
		return this.customer;
	}

	public void setSender(final Customer sender) {
		this.customer = sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Commentable getRecipient() {
		return this.commentable;
	}

	public void setRecipient(final Commentable recipient) {
		this.commentable = recipient;
	}

}
