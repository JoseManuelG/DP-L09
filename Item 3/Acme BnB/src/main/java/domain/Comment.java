
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	//Attributes------------------------------
	private String	title;
	private String	text;
	private int		stars;
	private Date	postMoment;

	@SafeHtml
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@Range(min = 0, max = 5)
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostMoment() {
		return postMoment;
	}
	public void setPostMoment(Date dateCreation) {
		this.postMoment = dateCreation;
	}


	//Relationships---------------------------
	private Customer	sender;
	private Commentable	recipient;


	@NotNull
	@Valid
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	public Customer getSender() {
		return this.sender;
	}
	public void setSender(Customer sender) {
		this.sender = sender;
	}
	@NotNull
	@Valid
	@Cascade(CascadeType.REFRESH)
	@ManyToOne(optional = false)
	public Commentable getRecipient() {
		return recipient;
	}
	public void setRecipient(Commentable recipient) {
		this.recipient = recipient;
	}

}
