
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Attachment extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private String	url;


	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SafeHtml
	@NotBlank
	@URL
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	//Relationships
	private Audit	audit;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

}
