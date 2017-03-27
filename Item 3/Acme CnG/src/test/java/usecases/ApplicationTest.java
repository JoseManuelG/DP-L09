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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ApplicationService;
import services.TripService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationTest extends AbstractTest {

	// Beans for testing proposes
	public static final int		PAST_TRIP				= 999;	// OWNER TRIP: CUSTOMER 1
	public static final int		TRIP1_INVALID_APPLY		= 1032;
	public static final int		FUTURE_TRIP				= 1005; // OWNER TRIP: CUSTOMER 2
	public static final int		TRIP2_VALID_APPLY		= 1024;
	public static final int		TRIP2_ACCEPTED_APPLY	= 1036;
	public static final int		TRIP2_DENNIED_APPLY		= 1037;

	// System under test ------------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private ApplicationService	applicationService;


	// Tests ------------------------------------------------------------------

	// Test para los casos de uso en los que interviene ApplicationService.

	@Test
	public void applyPositiveTest() {
		// En este test se crea un apply para un viaje válido.
		this.authenticate("customer1");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.create(ApplicationTest.FUTURE_TRIP);
		this.applicationService.flush();

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void applyNegativeTest() {
		// En este test se intenta crear un apply para un viaje propio.
		this.authenticate("customer2");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.create(ApplicationTest.FUTURE_TRIP);
		this.applicationService.flush();

		this.unauthenticate();
	}

	@Test
	public void acceptPositiveTest() {
		// En este test se acepta un apply correctamente.
		this.authenticate("customer2");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.acceptApply(ApplicationTest.TRIP2_VALID_APPLY);
		this.applicationService.flush();

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void acceptNegativeTest() {
		// En este test se intenta aceptar un apply del viaje de otro customer.
		this.authenticate("customer1");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.acceptApply(ApplicationTest.TRIP2_VALID_APPLY);
		this.applicationService.flush();

		this.unauthenticate();
	}

	@Test
	public void denyPositiveTest() {
		// En este test se rechaza un apply correctamente.
		this.authenticate("customer2");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.denyApply(ApplicationTest.TRIP2_VALID_APPLY);
		this.applicationService.flush();

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void denyNegativeTest() {
		// En este test se intenta rechazar un apply del viaje de otro customer.
		this.authenticate("customer1");

		this.tripService.findOne(ApplicationTest.FUTURE_TRIP);
		this.applicationService.denyApply(ApplicationTest.TRIP2_VALID_APPLY);
		this.applicationService.flush();

		this.unauthenticate();
	}

	// Ancillary methods ------------------------------------------------------

}
