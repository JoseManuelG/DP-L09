
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Commentable extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private boolean	banned;


	public boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final boolean banned) {
		this.banned = banned;
	}

	// Relationships ----------------------------------------------------------

}
