package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookService;
import services.CreditCardService;
import services.InvoiceService;
import services.TenantService;
import controllers.AbstractController;
import domain.Book;
import domain.Invoice;
import forms.BookForm;

@Controller
@RequestMapping("/book/tenant")
public class BookTenantController extends AbstractController {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private BookService bookService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private CreditCardService creditCardService;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		result = createListModelAndView();
		
		return result;
	}


	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView book(@RequestParam int propertyId) {
		ModelAndView result;
		BookForm bookForm;
		
		bookForm = new BookForm();
		bookForm.setPropertyId(propertyId);
		result = createEditModelAndView(bookForm);
		
		return result;
	}
	

	@RequestMapping(value = "/book", method = RequestMethod.POST, params = "book")
	public ModelAndView edit(BookForm bookForm, BindingResult bindingResult) {
		ModelAndView result;
		Book book;
		
		book = bookService.reconstruct(bookForm, bindingResult);
		if (bindingResult.hasErrors()){
			result = createEditModelAndView(bookForm);
		} else {
			try{
				bookService.save(book);
				result = new ModelAndView("redirect:list.do");
			} catch (IllegalArgumentException e) {
				result = createEditModelAndView(bookForm, e.getMessage());
			}
		}
		
		return result;
	}


	@RequestMapping(value = "/requestInvoice", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int bookId) {
		ModelAndView result;
		Book book;
		Invoice invoice;
		
		book = bookService.findOne(bookId);
		invoice = null;
		try {
			if (book.getInvoice() == null){
				try {
					invoice = invoiceService.create(book);
				} catch (TransactionSystemException e) {
				}
			} else {
				bookService.checkOwnerTenantIsPrincipal(book);
				invoice = book.getInvoice();
			}
			result = new ModelAndView("redirect:/invoice/tenant/view.do?invoiceId="+invoice.getId());
		} catch(IllegalArgumentException e) {
			result = createListModelAndView(e.getMessage());
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(BookForm bookForm) {
		ModelAndView result;

		result = createEditModelAndView(bookForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(BookForm bookForm, String message) {
		ModelAndView result;

		result = new ModelAndView("book/edit");
		if (bookForm.getCreditCard()!=null && 
			bookForm.getCreditCard().getNumber().length()==16){
			try {
				creditCardService.maskCreditCard(bookForm.getCreditCard());
			} catch (TransactionSystemException e) {
			}
		}
		result.addObject("bookForm", bookForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createListModelAndView() {
		ModelAndView result;

		result = createListModelAndView(null);
		
		return result;
	}
	

	protected ModelAndView createListModelAndView(String message) {
		ModelAndView result;
		Collection<Book> books;
		
		books = tenantService.findAllBooksByPrincipal();
		try {
			creditCardService.maskCreditCardsFromBooks(books);
		} catch (TransactionSystemException e) {
		}
		result = new ModelAndView("book/list");
		result.addObject("books",books);
		result.addObject("requestURI","book/tenant/list.do");
		result.addObject("message", message);
		
		return result;
	}
	
}

