
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LessorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Book;
import domain.CreditCard;
import domain.Lessor;
import domain.Property;
import domain.Tenant;
import forms.ActorForm;

@Service
@Transactional
public class LessorService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private LessorRepository		lessorRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private LoginService			loginService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private BookService				bookService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private Validator				validator;


	// Constructor --------------------------------------------------------------------

	public LessorService() {
		super();
	}

	// Simple CRUD Methods ------------------------------------------------------------

	public Lessor create() {
		Lessor result = new Lessor();
		customerService.setCustomerCollections(result);
		result.setLessorProperties(new ArrayList<Property>());
		result.setBooks(new ArrayList<Book>());
		return result;
	}

	public Collection<Lessor> findAll() {
		Collection<Lessor> result = lessorRepository.findAll();
		return result;
	}

	public Lessor findOne(Integer lessorId) {
		Lessor result = lessorRepository.findOne(lessorId);
		return result;
	}

	public Lessor save(Lessor lessor) {
		Assert.notNull(lessor, "SAVE: El lessor no puede ser null");
		Lessor result = lessorRepository.save(lessor);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Lessor lessor) {
		UserAccount principal = loginService.getPrincipal();
		Assert.notNull(lessor, "DELETE: El lessor no puede ser null");
		Assert.isTrue(lessor.getId() != 0, "El lessor debe haber sido guardado");
		Assert.isTrue(principal.equals(lessor.getUserAccount()), "UserAccount no valido");

		bookService.removeLessor(lessor);
		finderService.removeLessorProperties(lessor);
		lessorRepository.delete(lessor);
	}

	public Long count() {
		//Dashboard-01
		return lessorRepository.count();
	}

	// Other Bussiness Methods --------------------------------------------------------

	public boolean existsCreditCardForAnyLessor(CreditCard creditCard) {
		boolean result = false;
		result = lessorRepository.existsCreditCardForAnyLessor(creditCard.getId());
		return result;
	}

	public Lessor findByPrincipal() {
		Lessor result;
		result = lessorRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public Collection<Book> findAllBooksByPrincipal() {
		Lessor principal;
		Collection<Book> result;

		principal = this.findByPrincipal();
		result = lessorRepository.findAllBooksByPrincipal(principal.getId());
		return result;
	}

	public void addFee() {
		Lessor principal;
		double actualFee;

		principal = this.findByPrincipal();
		actualFee = principal.getTotalFee();
		principal.setTotalFee(actualFee + configurationService.findOne().getFee());
	}

	public double findFeeFromPrincipal() {
		Lessor principal;

		principal = findByPrincipal();
		Assert.notNull(principal);

		return principal.getTotalFee();
	}

	public boolean lessorHaveBooksWithTenant(Tenant tenant, Lessor lessor) {
		boolean res = false;
		if (lessorRepository.findAllBooksByTennantAndLessor(tenant.getId(), lessor.getId()) > 0) {
			res = true;
		}
		return res;
	}

	public Lessor reconstruct(ActorForm actorForm, BindingResult binding) {
		Lessor result = create();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(actorForm.getUserAccount().getUsername());
		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(actorForm.getTypeOfActor());
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPicture(actorForm.getPicture());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());

		result.setUserAccount(userAccount);

		validator.validate(result, binding);
		userAccount.setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}
	public Lessor reconstruct(ActorForm actorForm, Lessor lessor, BindingResult binding) {
		Lessor result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();

		result = new Lessor();

		customerService.reconstruct(result, lessor, actorForm);

		// Setear campos propios de tenant.

		result.setBooks(lessor.getBooks());
		result.setCreditCard(lessor.getCreditCard());
		result.setLessorProperties(lessor.getLessorProperties());
		result.setTotalFee(lessor.getTotalFee());

		validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}

	public Lessor getLessorWithMoreAcceptedBooks() {
		//Dashboard-03
		Lessor lessor;
		List<Lessor> lessors;

		lessors = lessorRepository.getLessorWithMoreAcceptedBooks();

		if (lessors.size() != 0) {
			lessor = lessors.get(0);
		} else {
			lessor = null;
		}
		return lessor;
	}

	public Lessor getLessorWithMoreDeniedBooks() {
		//Dashboard-04
		Lessor lessor;
		List<Lessor> lessors;

		lessors = lessorRepository.getLessorWithMoreDeniedBooks();

		if (lessors.size() != 0) {
			lessor = lessors.get(0);
		} else {
			lessor = null;
		}
		return lessor;
	}

	public Lessor getLessorWithMorePendingBooks() {
		//Dashboard-05
		Lessor lessor;

		List<Lessor> lessors;

		lessors = lessorRepository.getLessorWithMorePendingBooks();

		if (lessors.size() != 0) {
			lessor = lessors.get(0);
		} else {
			lessor = null;
		}
		return lessor;
	}

	public Lessor getLessorWithMinAcceptedVersusTotalBooksRatio() {
		//Dashboard-09
		Lessor lessor;
		List<Object[]> lessors;
		Double c;

		lessors = lessorRepository.getLessorsByAcceptedVersusTotalBooksRatio();

		lessor = null;

		if (lessors.size() != 0) {
			c = Double.MAX_VALUE;
			for (Object[] o : lessors) {
				if ((Double) o[1] < c) {
					c = (Double) o[1];
					lessor = (Lessor) o[0];
				}
			}
		}
		return lessor;
	}

	public Lessor getLessorWithMaxAcceptedVersusTotalBooksRatio() {
		//Dashboard-09
		Lessor lessor;
		List<Object[]> lessors;
		Double c;

		lessors = lessorRepository.getLessorsByAcceptedVersusTotalBooksRatio();

		lessor = null;

		if (lessors.size() != 0) {
			c = -1.0;
			for (Object[] o : lessors) {
				if ((Double) o[1] > c) {
					c = (Double) o[1];
					lessor = (Lessor) o[0];
				}
			}
		}
		return lessor;
	}
}
