/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import services.ConfigurationService;
import services.CustomerService;
import utilities.AbstractTest;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------

	// Watch a welcome page with a banner that publicises Acme Carn go! 

	@Test
	public void BannerPositiveTest() {
		final String banner = this.configurationService.findOne().getBanner();
		Assert.notNull(banner);
	}

	//  Register as a customer

	@Test
	public void RegisterPositiveTest() {
		final Customer customer = this.customerService.create();
		Customer savedCustomer = null;
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		customer.setUserAccount(userAccount);
		customer.getUserAccount().setUsername("test");
		customer.getUserAccount().setPassword("test");
		customer.setEmail("test@acme.com");
		customer.setName("test");
		customer.setSurname("tested");
		customer.setPhone("testPhone");
		savedCustomer = this.customerService.save(customer);
		Assert.notNull(savedCustomer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void RegisterNegativeTest() {
		final Customer customer = this.customerService.create();
		Customer savedCustomer = null;
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		customer.setUserAccount(userAccount);
		customer.getUserAccount().setUsername("");
		customer.getUserAccount().setPassword("");
		customer.setEmail("");
		customer.setName("");
		customer.setSurname("");
		customer.setPhone("");
		savedCustomer = this.customerService.save(customer);
		Assert.isNull(savedCustomer);
	}

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				"test1", "test1", "test@acme.com", "test1", "tested", "testPhone", null
			}, {
				"", "test1", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class
			}, {
				"test1", "", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class
			}, {
				"test1", "test1", "", "test1", "tested", "testPhone", ConstraintViolationException.class
			}, {
				"test1", "test1", "test@acme.com", "", "tested", "testPhone", ConstraintViolationException.class
			}, {
				"test1", "test1", "test@acme.com", "test1", "", "testPhone", ConstraintViolationException.class
			}, {
				"test1", "test1", "test@acme.com", "test1", "tested", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void template(final String userName, final String password, final String email, final String name, final String surname, final String phone, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Customer customer = this.customerService.create();
			final UserAccount userAccount = new UserAccount();
			final Collection<Authority> authorities = new ArrayList<Authority>();
			final Authority authority = new Authority();
			authority.setAuthority("CUSTOMER");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);
			customer.setUserAccount(userAccount);
			customer.getUserAccount().setUsername(userName);
			customer.getUserAccount().setPassword(password);
			customer.setEmail(email);
			customer.setName(name);
			customer.setSurname(surname);
			customer.setPhone(phone);
			this.customerService.save(customer);
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
