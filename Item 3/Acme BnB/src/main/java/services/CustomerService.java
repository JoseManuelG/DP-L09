
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Customer;
import forms.ActorForm;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;
	
	@Autowired
	private ActorService actorService;


	public Customer findActorByPrincial() {
		Customer result;
		result = customerRepository.findByUserAccount(LoginService.getPrincipal().getId());
		return result;
	}

	public void setCustomerCollections(Customer customer) {
	}

	public void reconstruct(Customer result, Customer origin, ActorForm actorForm) {

		actorService.reconstruct(result, origin, actorForm);
		
	}
}
