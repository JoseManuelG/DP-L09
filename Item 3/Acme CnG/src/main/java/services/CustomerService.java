package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Customer;

import repositories.CustomerRepository;
import security.LoginService;

@Service
@Transactional
public class CustomerService {
	
	//Managed Repository--------------------------------------------------------------------
	
	@Autowired
	private CustomerRepository	customerRepository;
	
	
	//Other Business methods-------------------------------------------------------------------
	
	public Customer findCustomerByPrincipal() {
		Customer result;
		result = customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

}
