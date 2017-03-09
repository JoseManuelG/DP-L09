
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Apply>	applies;
	private Collection<Trip>	trips;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	public Collection<Apply> getApplies() {
		return this.applies;
	}

	public void setApplies(final Collection<Apply> applies) {
		this.applies = applies;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}
}
