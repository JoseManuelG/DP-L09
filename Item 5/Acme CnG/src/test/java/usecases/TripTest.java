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

	/*
	 * An actor who is authenticated as a customer must be able to:
	 * - Post an offer in which he or she advertises that he's going to move from a place to another place and would like to share his or her car with someone else.
	 * - Post a request in which he or she informs that he or she wishes to move from a place to another one and would like to find someone with whom he or she can share the trip.
	 */
	@Test
	public void postPositiveTest1() {
		/*
		 * En este test se crea un "offer" correctamente, sin rellenar los campos opcionales.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest2() {
		/*
		 * En este test se crea un "request" correctamente, sin rellenar los campos opcionales.
		 */
		this.templatePost(false, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest3() {
		/*
		 * En este test se crea un "request" correctamente, rellenando los campos opcionales.
		 */
		this.templatePost(false, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null);
	}

	@Test
	public void postPositiveTest4() {
		/*
		 * En este test se crea un "offer" correctamente, rellenando solo dos de los campos opcionales.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, 50.18, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest5() {
		/*
		 * En este test se crea un "offer" correctamente, rellenando solo dos de los campos opcionales.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "test", 54.23, 15.95, "test", null);
	}

	@Test
	public void postPositiveTest6() {
		/*
		 * En este test se crea un "offer" correctamente, rellenando los campos opcionales.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null);
	}

	@Test
	public void postNegativeTest1() {
		/*
		 * En este test un admin intenta crear un "offer". Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "admin", 1, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest2() {
		/*
		 * En este test se crea un "offer" con fecha 5 dias anterior a hoy. Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "customer1", -5, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest3() {
		/*
		 * En este test se crea un "offer" con el campo description vacio. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "", "test", null, null, "test", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest4() {
		/*
		 * En este test se crea un "offer" con el campo destination vacio. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "", null, null, "test", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest5() {
		/*
		 * En este test se crea un "offer" con sola una coordenada de destino. Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 20.15, null, "test", null, null, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest6() {
		/*
		 * En este test se crea un "offer" con sola una coordenada de destino. Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, 19.14, "test", null, null, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest7() {
		/*
		 * En este test se crea un "offer" con el campo origin vacio. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest8() {
		/*
		 * En este test se crea un "offer" con sola una coordenada de origen. Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "test", 20.15, null, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest9() {
		/*
		 * En este test se crea un "offer" con sola una coordenada de origen. Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "test", null, 40.75, "test", IllegalArgumentException.class);
	}

	@Test
	public void postNegativeTest10() {
		/*
		 * En este test se crea un "offer" con el campo title vacio. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", null, null, "test", null, null, "", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest11() {
		/*
		 * En este test se crea un "offer" con una coordenada fuera de rango. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", -90.1, 50.18, "test", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postPositiveTest7() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", -90., 50.18, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest8() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", -89.99, 50.18, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest9() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 89.99, 50.18, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest10() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 90., 50.18, "test", null, null, "test", null);
	}

	@Test
	public void postNegativeTest12() {
		/*
		 * En este test se crea un "offer" con una coordenada fuera de rango. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 90.01, 50.18, "test", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest13() {
		/*
		 * En este test se crea un "offer" con una coordenada fuera de rango. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, -180.01, "test", null, null, "test", ConstraintViolationException.class);
	}

	@Test
	public void postPositiveTest11() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, -180., "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest12() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, -179.99, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest13() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, 179.99, "test", null, null, "test", null);
	}

	@Test
	public void postPositiveTest14() {
		/*
		 * En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, 180., "test", 54.23, 15.95, "test", null);
	}

	@Test
	public void postNegativeTest14() {
		/*
		 * En este test se crea un "offer" con una coordenada fuera de rango. Se espera un ConstraintViolationException.
		 */
		this.templatePost(true, "customer1", 1, "test", "test", 10.30, 180.01, "test", 54.23, 15.95, "test", ConstraintViolationException.class);
	}

	@Test
	public void postNegativeTest15() {
		/*
		 * En este test un usuario no conectado intenta crear un "offer". Se espera un IllegalArgumentException.
		 */
		this.templatePost(true, null, 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", IllegalArgumentException.class);
	}

	// En las siguientes lineas se hacen los mismos test propuestos anteriormente con la estructura dada en clase (el array y el for)
	// Estos test dan error al hacer flush en un determinado momento.

	//	public void drivePost() {
	//		final Object testingData[][] = {
	//			{
	//				true, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
	//			}, {
	//				false, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
	//			}, {
	//				false, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, 50.18, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", null, null, "test", 54.23, 15.95, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
	//			}, {
	//				true, "admin", 1, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", -5, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", 1, "", "test", null, null, "test", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "", null, null, "test", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", 20.15, null, "test", null, null, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", null, 19.14, "test", null, null, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", null, null, "", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", null, null, "test", 20.15, null, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", null, null, "test", null, 40.75, "test", IllegalArgumentException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", null, null, "test", null, null, "", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", -90.1, 50.18, "test", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", -90., 50.18, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", -89.99, 50.18, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 89.99, 50.18, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 90., 50.18, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 90.01, 50.18, "test", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, -180.01, "test", null, null, "test", ConstraintViolationException.class
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, -180., "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, -179.99, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, 179.99, "test", null, null, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, 180., "test", 54.23, 15.95, "test", null
	//			}, {
	//				true, "customer1", 1, "test", "test", 10.30, 180.01, "test", 54.23, 15.95, "test", ConstraintViolationException.class
	//			}, {
	//				true, null, 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++) {
	//			this.tripService.flush();
	//			this.templatePost((Boolean) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (String) testingData[i][7],
	//				(Double) testingData[i][8], (Double) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	//		}
	//	}

	/*
	 * An actor who is authenticated as a customer must be able to:
	 * - Search for offers and requests using a single keyword that must appear somewhere in their titles, descriptions, or places.
	 */

	@Test
	public void searchPositiveTest1() {
		/*
		 * En este test un customer busca correctamente offers.
		 */
		this.templateSearch(true, "customer1", null);
	}

	@Test
	public void searchPositiveTest2() {
		/*
		 * En este test un customer busca correctamente requests.
		 */
		this.templateSearch(false, "customer1", null);
	}

	@Test
	public void searchNegativeTest1() {
		/*
		 * En este test un admin intenta buscar offers. Se espera un IllegalArgumentException.
		 */
		this.templateSearch(true, "admin", IllegalArgumentException.class);
	}

	@Test
	public void searchNegativeTest2() {
		/*
		 * En este test un admin intenta buscar requests. Se espera un IllegalArgumentException.
		 */
		this.templateSearch(false, "admin", IllegalArgumentException.class);
	}

	@Test
	public void searchNegativeTest3() {
		/*
		 * En este test un usuario no conectado intenta buscar offers. Se espera un IllegalArgumentException.
		 */
		this.templateSearch(true, null, IllegalArgumentException.class);
	}

	@Test
	public void searchNegativeTest4() {
		/*
		 * En este test un usuario no conectado intenta buscar requests. Se espera un IllegalArgumentException.
		 */
		this.templateSearch(false, null, IllegalArgumentException.class);
	}

	/*
	 * An actor who is authenticated as an administrator must be able to:
	 * Ban an offer or a request that he or she finds inappropriate. Such offers and requests must not be displayed to a general audience,
	 * only to the administrators and the customer who posted it.
	 */

	@Test
	public void banPositiveTest1() {
		/*
		 * En este test un admin banea un offer con fecha futura.
		 */
		this.templateBan(true, true, "admin", TripTest.FUTURE_OFFER, null);
	}

	@Test
	public void banPositiveTest2() {
		/*
		 * En este test un admin banea un offer con fecha pasada.
		 */
		this.templateBan(true, true, "admin", TripTest.PAST_OFFER, null);
	}

	@Test
	public void banPositiveTest3() {
		/*
		 * En este test un admin banea un request con fecha futura.
		 */
		this.templateBan(true, false, "admin", TripTest.FUTURE_REQUEST, null);
	}

	@Test
	public void banPositiveTest4() {
		/*
		 * En este test un admin banea un request con fecha pasada.
		 */
		this.templateBan(true, false, "admin", TripTest.PAST_REQUEST, null);
	}

	@Test
	public void banNegativeTest1() {
		/*
		 * En este test un customer intenta banear un offer. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, true, "customer1", TripTest.FUTURE_OFFER, IllegalArgumentException.class);
	}

	@Test
	public void banNegativeTest2() {
		/*
		 * En este test un customer intenta banear un request. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, false, "customer1", TripTest.FUTURE_REQUEST, IllegalArgumentException.class);
	}

	@Test
	public void banNegativeTest3() {
		/*
		 * En este test un usuario no identificado intenta banear un offer. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, true, null, TripTest.FUTURE_OFFER, IllegalArgumentException.class);
	}

	@Test
	public void banNegativeTest4() {
		/*
		 * En este test un usuario no identificado intenta banear un request. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, false, null, TripTest.FUTURE_REQUEST, IllegalArgumentException.class);
	}

	@Test
	public void banNegativeTest5() {
		/*
		 * En este test un admin banea un offer ya baneado. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, true, "admin", TripTest.BANNED_OFFER, IllegalArgumentException.class);
	}

	@Test
	public void banNegativeTest6() {
		/*
		 * En este test un admin banea un request ya baneado. Se espera un IllegalArgumentException.
		 */
		this.templateBan(true, false, "admin", TripTest.BANNED_REQUEST, IllegalArgumentException.class);
	}

	@Test
	public void unbanPositiveTest1() {
		/*
		 * En este test un admin desbanea un offer baneado.
		 */
		this.templateBan(false, true, "admin", TripTest.BANNED_OFFER, null);
	}

	@Test
	public void unbanPositiveTest2() {
		/*
		 * En este test un admin desbanea un request baneado.
		 */
		this.templateBan(false, false, "admin", TripTest.BANNED_REQUEST, null);
	}

	@Test
	public void unbanNegativeTest1() {
		/*
		 * En este test un customer desbanea un offer baneado. Se espera un IllegalArgumentException.
		 */
		this.templateBan(false, true, "customer1", TripTest.BANNED_OFFER, IllegalArgumentException.class);
	}

	@Test
	public void unbanNegativeTest2() {
		/*
		 * En este test un usuario no autenticado desbanea un request baneado. Se espera un IllegalArgumentException.
		 */
		this.templateBan(false, false, null, TripTest.BANNED_REQUEST, IllegalArgumentException.class);
	}

	@Test
	public void unbanNegativeTest3() {
		/*
		 * En este test un admin desbanea un offer no baneado. Se espera un IllegalArgumentException.
		 */
		this.templateBan(false, true, "admin", TripTest.FUTURE_OFFER, IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templatePost(final Boolean offer, final String username, final Integer days, final String description, final String destination, final Double destinationLat, final Double destinationLon, final String origin, final Double originLat,
		final Double originLon, final String title, final Class<?> expected) {
		/*
		 * Plantilla para testear post de offers y requests.
		 * username -> usuario que se autentica para hacer el post.
		 * offer -> true es offer y false es request
		 * days -> dias a partir de hoy (0 es hoy, -1 ayer, 1 mañana).
		 */
		Class<?> caught;
		Trip trip;
		long day;

		caught = null;
		try {

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

	protected void templateSearch(final Boolean offer, final String username, final Class<?> expected) {
		/*
		 * Plantilla para testear search con distintos loggins.
		 * username -> usuario que se autentica para hacer el post.
		 * offer -> true es offer y false es request
		 */
		Class<?> caught;
		String keyword;

		caught = null;
		try {

			this.authenticate(username);

			keyword = "cuervo";
			if (offer)
				this.tripService.findAllValidOffersByKeyWord(keyword);
			else
				this.tripService.findAllValidRequestsByKeyWord(keyword);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateBan(final Boolean ban, final Boolean offer, final String username, final int tripId, final Class<?> expected) {
		/*
		 * Plantilla para testear ban.
		 * ban -> true es banear, false desbanear.
		 * offer -> true es offer y false es request.
		 * username -> usuario que se autentica para hacer el post.
		 * tripId -> id del trip a banear.
		 */
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			if (offer)
				this.tripService.findAllOffers();
			else
				this.tripService.findAllRequests();

			//			this.tripService.findOne(tripId);

			if (ban)
				this.tripService.banTrip(tripId);
			else
				this.tripService.unbanTrip(tripId);

			this.tripService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
