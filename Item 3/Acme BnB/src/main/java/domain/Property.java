
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "address"),
		@Index(columnList = "rate"),
		@Index(columnList = "name"),
		@Index(columnList = "description")})
public class Property extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private double	rate;
	private String	description;
	private String	address;
	private Date	lastUpdate;

	@SafeHtml
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Min(0)
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	@SafeHtml
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@SafeHtml
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relationships -------------------------------------------------------------

	private Collection<Book>			books;
	private Lessor						lessor;
	private Collection<Audit>			audits;
	private Collection<AttributeValue>	attributeValues;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Book> getBooks() {
		return books;
	}
	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public Lessor getLessor() {
		return lessor;
	}
	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "property", cascade= {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE
		})
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}

	@NotNull
	@Valid
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "property", cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE
	})
	public Collection<AttributeValue> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(Collection<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}
}
