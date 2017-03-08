
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Book;
import domain.CreditCard;
import domain.Lessor;

@Service
@Transactional
public class CreditCardService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private BookService				bookService;

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private CustomerService			customerService;


	// Constructor --------------------------------------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;
		result = creditCardRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public CreditCard findOne(int creditCardId) {
		CreditCard result;
		result = creditCardRepository.findOne(creditCardId);
		return result;
	}

	public CreditCard saveForBook(CreditCard creditCard) {
		Assert.notNull(creditCard, "La tarjeta de crédito no puede ser nula");
		//Assert.isTrue((bookService.existsCreditCardForAnyBook(creditCard) && !lessorService.existsCreditCardForAnyLessor(creditCard)) || (!bookService.existsCreditCardForAnyBook(creditCard) && lessorService.existsCreditCardForAnyLessor(creditCard)));
		//Assert.isTrue(expression);
		//Assert.isTrue((lessor.getCreditCard().getId()==creditCard.getId()) || creditCard.getId()==0); 
		CreditCard result;
		result = creditCardRepository.save(creditCard);
		return result;
	}

	public CreditCard saveForLessor(CreditCard creditCard) {
		Assert.notNull(creditCard, "La tarjeta de crédito no puede ser nula");
		Lessor lessor = (Lessor) customerService.findActorByPrincial();
		Assert.isTrue((lessor.getCreditCard() == null && creditCard.getId() == 0) || (lessor.getCreditCard().getId() == creditCard.getId()), "Un lessor no puede tener más de una credit card");
		Assert.isTrue(!(bookService.existsCreditCardForAnyBook(creditCard)), "La credit card de un lessor no puede pertenecer a un book");
		checkCreditCard(creditCard);
		CreditCard result;
		result = creditCardRepository.save(creditCard);
		lessor.setCreditCard(result);
		lessorService.save(lessor);
		return result;

	}
	
	public void delete(CreditCard creditCard){
		Assert.notNull(creditCard, "La tarjeta de crédito no puede ser nula");
		Lessor lessor = (Lessor) customerService.findActorByPrincial();
		Assert.isTrue(lessor.getCreditCard().getId() == creditCard.getId(), "La tarjeta de crédito debe pertenecer al lessor");
		Assert.isTrue(!(bookService.existsCreditCardForAnyBook(creditCard)), "La credit card de un lessor no puede pertenecer a un book");
		lessor.setCreditCard(null);
		lessorService.save(lessor);
		creditCardRepository.delete(creditCard);
	}

	// Other Bussiness Methods --------------------------------------------------------

	public void checkCreditCard(CreditCard creditCard) {
		long today, cardDate, sevenDays;
		Calendar calendar;

		Assert.notNull(creditCard, "creditCard.null.error");
		sevenDays = 7 * 24 * 60 * 60 * 100;
		today = System.currentTimeMillis();
		calendar = new GregorianCalendar(creditCard.getExpirationYear(),
			creditCard.getExpirationMonth(), 1);
		cardDate = calendar.getTimeInMillis();
		
		Assert.isTrue(cardDate > today + sevenDays, "creditCard.expired.error");
	}

	public String getMaskedCreditCardAsString(CreditCard creditCard) {
		String number, mask;
		
		number = creditCard.getNumber();
		mask = "";
		if (number.length()<=4){
			mask = number;
		} else {
			for (int i=0; i<number.length()-4; i++){
				mask += "*";
			}
			mask += number.substring(number.length()-4);
		}

		return mask;
	}

	public void maskCreditCard(CreditCard creditCard) {
		creditCard.setNumber(getMaskedCreditCardAsString(creditCard));
	}

	public void maskCreditCardsFromBooks(Collection<Book> books) {
		for (Book book : books){
			maskCreditCard(book.getCreditCard());
		}
	}
}
