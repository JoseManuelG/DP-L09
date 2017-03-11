
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Apply extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	application;


	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^DENIED$")
	public String getApplication() {
		return this.application;
	}

	public void setApplication(final String application) {
		this.application = application;
	}


	// Relationships ----------------------------------------------------------

	private Customer	customer;
	private Trip		trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
