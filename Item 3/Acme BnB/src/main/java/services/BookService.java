
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BookRepository;
import domain.Actor;
import domain.Book;
import domain.CreditCard;
import domain.Invoice;
import domain.Lessor;
import domain.Property;
import domain.Tenant;
import forms.BookForm;

@Service
@Transactional
public class BookService {

	// Managed Repository --------------------------------------
	@Autowired
	private BookRepository		bookRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private LessorService		lessorService;

	@Autowired
	private TenantService		tenantService;

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------
	public Book create(Property property, Tenant tenant) {
		Book result;

		result = new Book();
		result.setProperty(property);
		result.setTenant(tenant);
		result.setState("PENDING");
		result.setPropertyAddress(property.getAddress());
		result.setPropertyName(property.getName());
		result.setLessor(property.getLessor());

		return result;
	}
	public Collection<Book> findAll() {
		Collection<Book> result;

		result = bookRepository.findAll();

		return result;
	}

	public Book findOne(int bookId) {
		Book result;

		result = bookRepository.findOne(bookId);

		return result;
	}

	public Book save(Book book) {
		Book result;

		Assert.notNull(book, "book.null.error");

		checkDayAfter(book);
		checkOwnerTenantIsPrincipal(book);
		checkBookDate(book);
		creditCardService.checkCreditCard(book.getCreditCard());

		calculateTotalAmount(book);
		result = bookRepository.save(book);
		Assert.notNull(result, "book.commit.error");

		return result;
	}
	public void delete(Book book) {
		Assert.notNull(book, "book.null.error");

		Assert.isTrue(bookRepository.exists(book.getId()), "book.exists.error");

		bookRepository.delete(book);
	}

	// Other business methods --------------------------------------

	public boolean existsCreditCardForAnyBook(CreditCard creditCard) {
		boolean result = false;
		result = bookRepository.existsCreditCardForAnyBook(creditCard.getId());
		return result;
	}

	public void acceptBook(int bookId) {
		Book book;

		book = this.findOne(bookId);

		Assert.notNull(book.getLessor().getCreditCard(), "book.null.credit.card.error");
		checkOwnerLessorIsPrincipal(book);
		checkStateIsPending(book);
		checkBookDate(book);
		creditCardService.checkCreditCard(book.getLessor().getCreditCard());

		book.setState("ACCEPTED");
		bookRepository.save(book);
		lessorService.addFee();

	}

	public void denyBook(int bookId) {
		Book book;

		book = this.findOne(bookId);
		checkOwnerLessorIsPrincipal(book);
		checkStateIsPending(book);

		book.setState("DENIED");
		bookRepository.save(book);
	}

	public Book reconstruct(BookForm bookForm, BindingResult bindingResult) {
		Book book;
		Property property;
		Tenant tenant;

		property = propertyService.findOne(bookForm.getPropertyId());
		tenant = tenantService.findByPrincipal();

		book = this.create(property, tenant);
		book.setCheckInDate(bookForm.getCheckInDate());
		book.setCheckOutDate(bookForm.getCheckOutDate());
		book.setCreditCard(bookForm.getCreditCard());
		book.setSmoker(bookForm.getSmoker());
		validator.validate(book, bindingResult);
		return book;
	}

	public Collection<Book> findBooksForProperty(Property property) {
		Collection<Book> result = bookRepository.findBooksForPropertyId(property.getId());
		return result;
	}

	private void checkStateIsPending(Book book) {
		Assert.isTrue(book.getState().equals("PENDING"), "book.pending.error");
	}

	public void checkOwnerLessorIsPrincipal(Book book) {
		Actor principal;
		Lessor owner;

		Assert.notNull(book, "book.exists.error");
		principal = lessorService.findByPrincipal();
		Assert.notNull(principal, "book.principal.error");
		owner = book.getLessor();

		Assert.isTrue(owner.equals(principal), "book.principal.error");
	}

	public void checkOwnerTenantIsPrincipal(Book book) {
		Actor principal;
		Tenant owner;

		Assert.notNull(book, "book.exists.error");
		principal = tenantService.findByPrincipal();
		Assert.notNull(principal, "book.principal.error");
		owner = book.getTenant();

		Assert.isTrue(owner.equals(principal), "book.principal.error");
	}

	public Double getAverageAcceptedBooksPerLessor() {
		//Dashboard-01
		Double res, c1;
		Long c2;

		c1 = bookRepository.countAcceptedBooks();
		c2 = lessorService.count();

		if (c1 != null && c2 != 0) {
			res = c1 / c2;
		} else {
			res = 0.;
		}
		return res;
	}

	public Double getAverageDeniedBooksPerLessor() {
		//Dashboard-01
		Double res, c1;
		Long c2;

		c1 = bookRepository.countDeniedBooks();
		c2 = lessorService.count();

		if (c1 != null && c2 != 0) {
			res = c1 / c2;
		} else {
			res = 0.;
		}
		return res;
	}

	public Double getAverageAcceptedBooksPerTenant() {
		//Dashboard-02
		Double res, c1;
		Long c2;

		c1 = bookRepository.countAcceptedBooks();
		c2 = tenantService.count();

		if (c1 != null && c2 != 0) {
			res = c1 / c2;
		} else {
			res = 0.;
		}
		return res;
	}

	public Double getAverageDeniedBooksPerTenant() {
		//Dashboard-02
		Double res, c1;
		Long c2;

		c1 = bookRepository.countDeniedBooks();
		c2 = tenantService.count();

		if (c1 != null && c2 != 0) {
			res = c1 / c2;
		} else {
			res = 0.;
		}
		return res;
	}

	private void checkDayAfter(Book book) {
		long checkIn, checkOut, aDay;

		checkIn = book.getCheckInDate().getTime();
		checkOut = book.getCheckOutDate().getTime();
		aDay = 24 * 60 * 60 * 100;

		Assert.isTrue(checkOut - checkIn >= aDay, "book.checkDayAfter.error");
	}

	private void checkBookDate(Book book) {
		long checkIn, now;

		checkIn = book.getCheckInDate().getTime();
		now = System.currentTimeMillis();

		Assert.isTrue(checkIn > now, "book.checkDate.error");
	}

	private void calculateTotalAmount(Book book) {
		int days;
		long out, in;

		out = book.getCheckOutDate().getTime();
		in = book.getCheckInDate().getTime();
		days = (int) (out - in) / (1000 * 60 * 60 * 24);

		book.setTotalAmount(days * book.getProperty().getRate());
	}

	public Double getAverageRequestsWithAuditsVersusNoAudits() {
		//Dashboard-20
		Double withAudits;
		Double withoutAudits;
		Double res;

		withAudits = bookRepository.getAverageRequestsWithAudits();
		withoutAudits = bookRepository.getAverageRequestsWithoutAudits();

		if (withAudits == null || withoutAudits == null || withoutAudits == 0.0) {
			res = 0.0;
		} else {
			res = withAudits / withoutAudits;
		}
		return res;
	}
	public Invoice addInvoice(Book book, Invoice invoice) {
		Book result;

		book.setInvoice(invoice);
		result = bookRepository.save(book);

		return result.getInvoice();
	}

	public void removeTenant(Tenant tenant) {

		for (Book book : tenant.getBooks()) {
			book.setTenant(null);
			bookRepository.save(book);
		}

	}

	public void removeLessor(Lessor lessor) {

		for (Book book : lessor.getBooks()) {
			book.setLessor(null);
			book.setProperty(null);
			bookRepository.save(book);
		}

	}
	public void removePropertyFromBooks(Collection<Book> books) {
		for (Book b : books) {
			b.setProperty(null);
			bookRepository.save(b);
		}

	}

}
