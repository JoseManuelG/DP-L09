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

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.TripService;
import utilities.AbstractTest;
import domain.Trip;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TripTest extends AbstractTest {

	// Beans for testing proposes
	public static final int	PAST_REQUEST	= 999;
	public static final int	PAST_OFFER		= 1000;
	public static final int	BANNED_REQUEST	= 1001;
	public static final int	FUTURE_REQUEST	= 1002;
	public static final int	BANNED_OFFER	= 1003;
	public static final int	FUTURE_OFFER	= 1004;

	// System under test ------------------------------------------------------
	@Autowired
	private TripService		tripService;


	// Tests ------------------------------------------------------------------

	// Test para los casos de uso en los que interviene TripService.

	@Test
	public void drivePost() {
		final Object testingData[][] = {
			{
				true, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
			}, {
				false, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, 50.18, "test", null, null, "test", null
			}, {
				false, "customer1", 1, "test", "test", null, null, "test", 54.23, 15.95, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
			}, {
				true, "admin", 1, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
			}, {
				true, "customer1", -5, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
			}, {
				true, "customer1", 1, "", "test", null, null, "test", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "", null, null, "test", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", 20.15, null, "test", null, null, "test", IllegalArgumentException.class
			}, {
				true, "customer1", 1, "test", "test", null, 19.14, "test", null, null, "test", IllegalArgumentException.class
			}, {
				true, "customer1", 1, "test", "test", null, null, "", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", null, null, "test", 20.15, null, "test", IllegalArgumentException.class
			}, {
				true, "customer1", 1, "test", "test", null, null, "test", null, 40.75, "test", IllegalArgumentException.class
			}, {
				true, "customer1", 1, "test", "test", null, null, "test", null, null, "", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", -90. - 0.01, 50.18, "test", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", -90., 50.18, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", -90. + 0.01, 50.18, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 90. - 0.01, 50.18, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 90., 50.18, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 90. + 0.01, 50.18, "test", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", 10.30, -180. - 0.01, "test", null, null, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", 10.30, -180., "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, -180. + 0.01, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, 180. - 0.01, "test", null, null, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, 180., "test", 54.23, 15.95, "test", null
			}, {
				true, "customer1", 1, "test", "test", 10.30, 180. + 0.01, "test", 54.23, 15.95, "test", ConstraintViolationException.class
			}, {
				true, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((Boolean) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (String) testingData[i][7],
				(Double) testingData[i][8], (Double) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void searchOfferPositiveTest() {
		//
		String keyword;

		this.authenticate("customer1");

		keyword = "cuervo";
		this.tripService.findAllValidOffersByKeyWord(keyword);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void searchOfferNegativeTest() {
		//
		String keyword;

		this.authenticate("admin");

		keyword = "cuervo";
		this.tripService.findAllValidOffersByKeyWord(keyword);

		this.unauthenticate();
	}

	@Test
	public void searchRequestPositiveTest() {
		//
		String keyword;

		this.authenticate("customer1");

		keyword = "cuervo";
		this.tripService.findAllValidRequestsByKeyWord(keyword);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void searchRequestNegativeTest() {
		//
		String keyword;

		this.authenticate("admin");

		keyword = "cuervo";
		this.tripService.findAllValidRequestsByKeyWord(keyword);

		this.unauthenticate();
	}

	@Test
	public void banOfferPositiveTest() {
		//

		this.authenticate("admin");

		this.tripService.findAllOffers();

		this.tripService.banTrip(TripTest.PAST_OFFER);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void banOfferNegativeTest() {
		//

		this.authenticate("customer1");

		this.tripService.findAllOffers();

		this.tripService.banTrip(TripTest.PAST_OFFER);

		this.unauthenticate();
	}

	@Test
	public void banRequestPositiveTest() {
		//

		this.authenticate("admin");

		this.tripService.findAllRequests();

		this.tripService.banTrip(TripTest.PAST_REQUEST);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void banRequestNegativeTest() {
		//

		this.authenticate("customer1");

		this.tripService.findAllRequests();

		this.tripService.banTrip(TripTest.PAST_REQUEST);

		this.unauthenticate();
	}

	// Ancillary methods ------------------------------------------------------

	protected void templatePost(final Boolean offer, final String username, final Integer days, final String description, final String destination, final Double destinationLat, final Double destinationLon, final String origin, final Double originLat,
		final Double originLon, final String title, final Class<?> expected) {
		/*
		 * Plantilla para testear por de offers y requests.
		 * username -> usuario que se autentica para hacer el post.
		 * offer -> true es offer y false es request
		 * days -> dias a partir de hoy (0 es hoy, -1 ayer, 1 mañana).
		 */
		Class<?> caught;

		caught = null;
		try {
			Trip trip;
			long day;

			this.authenticate(username);

			day = 24 * 60 * 60 * 100;
			if (offer)
				trip = this.tripService.createOffer();
			else
				trip = this.tripService.createRequest();

			trip.setDepartureTime(new Date(System.currentTimeMillis() + days * day));
			trip.setDescription(description);
			trip.setDestination(destination);
			trip.setDestinationLat(destinationLat);
			trip.setDestinationLon(destinationLon);
			trip.setOrigin(origin);
			trip.setOriginLat(originLat);
			trip.setOriginLon(originLon);
			trip.setTitle(title);

			this.tripService.save(trip);
			this.tripService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
