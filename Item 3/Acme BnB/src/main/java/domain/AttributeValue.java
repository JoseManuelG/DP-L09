
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"attribute_id", "property_id"}))
public class AttributeValue extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	value;


	@SafeHtml
	@NotBlank
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	//Relationships

	private Attribute	attribute;
	private Property	property;


	@Valid
	@ManyToOne(optional = false)
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}
