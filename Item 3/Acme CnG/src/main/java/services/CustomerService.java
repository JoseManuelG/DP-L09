
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;


	// Supporting Services --------------------------------------
	//Simple CRUD methods-------------------------------------------------------------------
	public Customer create() {
		final Customer result = new Customer();
		return result;
	}

	public Customer save(final Customer customer) {
		Customer result;

		Assert.notNull(customer, "customer.error.null");
		result = this.customerRepository.save(customer);
		Assert.notNull(result, "customer.error.commit");

		return result;

	}

	//Other Business methods-------------------------------------------------------------------

	public Customer findCustomerByPrincipal() {
		Customer result;
		result = this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

}
