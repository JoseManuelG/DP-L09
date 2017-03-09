
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	url;


	@NotBlank
	@URL
	@SafeHtml
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	// Relationships ----------------------------------------------------------

}
