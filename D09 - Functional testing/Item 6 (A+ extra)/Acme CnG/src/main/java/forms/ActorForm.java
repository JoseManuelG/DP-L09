
package forms;

import security.UserAccount;

public class ActorForm {

	private String		typeOfActor;
	private String		confirmPassword;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private Boolean		acepted;
	private UserAccount	userAccount;


	//Constructor
	public ActorForm() {
		super();
	}
	//attributes------------

	public String getTypeOfActor() {
		return this.typeOfActor;
	}

	public void setTypeOfActor(final String typeOfActor) {
		this.typeOfActor = typeOfActor;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Boolean getAcepted() {
		return this.acepted;
	}
	public void setAcepted(final Boolean acepted) {
		this.acepted = acepted;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
