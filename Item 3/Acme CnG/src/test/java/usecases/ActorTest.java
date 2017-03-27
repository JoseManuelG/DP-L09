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

		// Registro completo sin errores

		this.template("test1", "test1", "test@acme.com", "test1", "tested", "testPhone", null);
	}

	@Test
	public void RegisterNegativeTest2() {

		// Registro sin username

		this.template("", "test1", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest3() {

		// Registro sin password

		this.template("test1", "", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest4() {

		// Registro sin email

		this.template("test1", "test1", "", "test1", "tested", "testPhone", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest5() {

		// Registro sin nombre

		this.template("test1", "test1", "test@acme.com", "", "tested", "testPhone", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest6() {

		// Registro sin apellidos

		this.template("test1", "test1", "test@acme.com", "test1", "", "testPhone", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest7() {

		// Registro sin telefono

		this.template("test1", "test1", "test@acme.com", "test1", "tested", "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driver() {
	//		final Object testingData[][] = {
	//			{
	//				"test1", "test1", "test@acme.com", "test1", "tested", "testPhone", null
	//			}, {
	//				"", "test1", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class
	//			}, {
	//				"test1", "", "test@acme.com", "test1", "tested", "testPhone", ConstraintViolationException.class
	//			}, {
	//				"test1", "test1", "", "test1", "tested", "testPhone", ConstraintViolationException.class
	//			}, {
	//				"test1", "test1", "test@acme.com", "", "tested", "testPhone", ConstraintViolationException.class
	//			}, {
	//				"test1", "test1", "test@acme.com", "test1", "", "testPhone", ConstraintViolationException.class
	//			}, {
	//				"test1", "test1", "test@acme.com", "test1", "tested", "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	//	}

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
