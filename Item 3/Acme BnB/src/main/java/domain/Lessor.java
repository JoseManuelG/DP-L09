
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Lessor extends Customer {

	// Attributes -------------------------------------------------------------

	private double	totalFee;


	@Min(0)
	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}


	// Relationships ----------------------------------------------------------

	private CreditCard				creditCard;
	private Collection<Property>	lessorProperties;
	private Collection<Book>		books;


	@Valid
	@OneToOne
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "lessor", cascade = CascadeType.REMOVE)
	public Collection<Property> getLessorProperties() {
		return lessorProperties;
	}

	public void setLessorProperties(Collection<Property> lessorProperties) {
		this.lessorProperties = lessorProperties;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "lessor", cascade = CascadeType.REFRESH)
	public Collection<Book> getBooks() {
		return books;
	}

	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

}
