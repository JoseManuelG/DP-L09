
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends Commentable {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	departureTime;
	private String	origin;
	private String	destination;
	private Double	originLat;
	private Double	originLon;
	private Double	destinationLat;
	private Double	destinationLon;
	private boolean	isOffer;


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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(final Date departureTime) {
		this.departureTime = departureTime;
	}

	@NotBlank
	@SafeHtml
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	@NotBlank
	@SafeHtml
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(final String destination) {
		this.destination = destination;
	}

	public Double getOriginLat() {
		return this.originLat;
	}

	public void setOriginLat(final Double originLat) {
		this.originLat = originLat;
	}

	public Double getOriginLon() {
		return this.originLon;
	}

	public void setOriginLon(final Double originLon) {
		this.originLon = originLon;
	}

	public Double getDestinationLat() {
		return this.destinationLat;
	}

	public void setDestinationLat(final Double destinationLat) {
		this.destinationLat = destinationLat;
	}

	public Double getDestinationLon() {
		return this.destinationLon;
	}

	public void setDestinationLon(final Double destinationLon) {
		this.destinationLon = destinationLon;
	}

	public boolean getIsOffer() {
		return this.isOffer;
	}

	public void setIsOffer(final boolean isOffer) {
		this.isOffer = isOffer;
	}


	// Relationships ----------------------------------------------------------

	private Customer			customer;
	private Collection<Apply>	applies;


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
	@OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
	public Collection<Apply> getApplies() {
		return this.applies;
	}

	public void setApplies(final Collection<Apply> applies) {
		this.applies = applies;
	}

}
