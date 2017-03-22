
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Customer;
import forms.ActorForm;

@Service
@Transactional
public class CustomerService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private CustomerRepository		customerRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private Validator				validator;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private CommentService			commentService;


	//Simple CRUD methods-------------------------------------------------------------------
	public Customer create() {
		final Customer result = new Customer();
		return result;
	}

	public Customer save(final Customer customer) {
		Customer result;

		Assert.notNull(customer, "customer.error.null");
		customer.setUserAccount(this.userAccountRepository.save(customer.getUserAccount()));
		result = this.customerRepository.save(customer);
		Assert.notNull(result, "customer.error.commit");

		return result;

	}
	public Customer findOne(final int id) {
		Customer result;
		result = this.customerRepository.findOne(id);
		return result;
	}

	public Long count() {
		return this.customerRepository.count();
	}

	public void delete() {
		final Customer customer = this.findCustomerByPrincipal();
		this.applicationService.deleteCustomer(customer);
		this.tripService.deleteCustomer(customer);
		this.messageService.deleteCustomer(customer);
		this.commentService.deleteAllCommentsOfActor(customer);
		this.customerRepository.delete(customer);
		this.userAccountRepository.delete(customer.getUserAccount().getId());

	}
	public void flush() {
		this.customerRepository.flush();
	}
	//Other Business methods-------------------------------------------------------------------

	public Customer findCustomerByPrincipal() {
		Customer result;
		result = this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Customer reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Customer result = this.create();

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername(actorForm.getUserAccount().getUsername());
		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());

		result.setUserAccount(userAccount);

		this.validator.validate(result, binding);
		userAccount.setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}

}
