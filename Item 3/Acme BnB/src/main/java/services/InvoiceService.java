
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import domain.Book;
import domain.Invoice;
import domain.Tenant;

@Service
@Transactional
public class InvoiceService {

	// Managed Repository --------------------------------------
	@Autowired
	private InvoiceRepository		invoiceRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private TenantService			tenantService;

	@Autowired
	private BookService				bookService;


	// Simple CRUD methods --------------------------------------
	public Invoice create(Book book) {
		Invoice result;
		Tenant tenant;
		String details, information, maskedNumber;

		bookService.checkOwnerTenantIsPrincipal(book);
		Assert.isNull(book.getInvoice());
		Assert.isTrue(book.getState().equals("ACCEPTED"), "invoice.unaccepted.error");
		maskedNumber = creditCardService.getMaskedCreditCardAsString(book.getCreditCard());
		tenant = book.getTenant();
		details = "Checkin: " + book.getCheckInDate() + ", " + "Checkout: " + book.getCheckOutDate() + ", " + "Address: " + book.getPropertyAddress() + ", " + "Credit Card: " + maskedNumber + ", " + "Amount: " + book.getTotalAmount() + " euros, ";
		if (book.getSmoker()) {
			details += "Smoker";
		} else {
			details += "No smoker";
		}

		information = tenant.getName() + " " + tenant.getSurname();

		result = new Invoice();
		result.setCreationMoment(new Date(System.currentTimeMillis()));
		result.setVAT(configurationService.findOne().getVAT());
		result.setBook(book);
		result.setDetails(details);
		result.setInformation(information);

		result = bookService.addInvoice(book, result);
		tenantService.addInvoice(tenant, result);

		return result;
	}

	public Collection<Invoice> findAll() {
		Collection<Invoice> result;

		result = invoiceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Invoice findOne(int invoiceId) {
		Invoice result;

		result = invoiceRepository.findOne(invoiceId);

		return result;
	}

	public Invoice save(Invoice invoice) {
		Invoice result;

		Assert.notNull(invoice, "invoice.error.null");
		result = invoiceRepository.save(invoice);
		Assert.notNull(result, "invoice.error.commit");

		return result;
	}

	public void delete(Invoice invoice) {
		Assert.notNull(invoice, "invoice.error.null");

		Assert.isTrue(invoiceRepository.exists(invoice.getId()), "invoice.error.exists");

		invoiceRepository.delete(invoice);
	}
	public void deleteAll(Tenant tenant) {
		invoiceRepository.delete(tenant.getInvoices());
	}

	// Other business methods --------------------------------------

	public void checkOwnerIsPrincipal(Invoice invoice) {
		Tenant principal, owner;

		owner = invoice.getBook().getTenant();
		principal = tenantService.findByPrincipal();
		Assert.isTrue(owner.equals(principal));
	}

	public Integer getMinimumInvoicesPerTenant() {
		//Dashboard-18
		Integer res = invoiceRepository.getMinimumInvoicesPerTenant();
		return res;
	}

	public Double getAverageInvoicesPerTenant() {
		//Dashboard-18
		Double res = invoiceRepository.getAverageInvoicesPerTenant();
		return res;
	}

	public Integer getMaximumInvoicesPerTenant() {
		//Dashboard-18
		Integer res = invoiceRepository.getMaximumInvoicesPerTenant();
		return res;
	}

	public Double getTotalDueMoneyOfInvoices() {
		//Dashboard-19
		Double res = invoiceRepository.getTotalDueMoneyOfInvoices();
		return res;
	}

}
