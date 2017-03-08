package forms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import domain.CreditCard;


public class BookForm {

	private int propertyId;
	private Date checkInDate;
	private Date checkOutDate;
	private boolean smoker;
	private CreditCard creditCard;
	
	
	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public int getPropertyId() {
		return propertyId;
	}
	
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckInDate() {
		return checkInDate;
	}
	
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public boolean getSmoker() {
		return smoker;
	}
	
	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}
	
}
