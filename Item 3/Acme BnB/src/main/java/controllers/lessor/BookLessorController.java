package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookService;
import services.LessorService;
import controllers.AbstractController;
import domain.Book;

@Controller
@RequestMapping("/book/lessor")
public class BookLessorController extends AbstractController {

	@Autowired
	private LessorService lessorService ;

	@Autowired
	private BookService bookService ;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return createListModelAndView();
	}

	@RequestMapping(value = "/acceptBook", method = RequestMethod.GET)
	public ModelAndView acceptBook(@RequestParam int bookId) {
		ModelAndView result;
		
		try{
			bookService.acceptBook(bookId);
			result = createListModelAndView();
		} catch (IllegalArgumentException e) {
			result = createListModelAndView(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/denyBook", method = RequestMethod.GET)
	public ModelAndView denyBook(@RequestParam int bookId) {
		ModelAndView result;

		try{
			bookService.denyBook(bookId);
			result = createListModelAndView();
		} catch (IllegalArgumentException e) {
			result = createListModelAndView(e.getMessage());
		}
		
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
		
		books = lessorService.findAllBooksByPrincipal();
		result = new ModelAndView("book/list");
		result.addObject("books",books);
		result.addObject("requestURI","book/lessor/list.do");
		result.addObject("message", message);
		
		return result;
	}
	

}

