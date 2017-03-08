
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Attributes-----------------------------------
	private double	fee;
	private String	VAT;

	@Min(0)
	public double getFee() {
		return this.fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	@SafeHtml
	@NotBlank
	public String getVAT() {
		return this.VAT;
	}

	public void setVAT(String VAT) {
		this.VAT = VAT;
	}

}
