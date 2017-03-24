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
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionSystemException;

import services.TripService;
import utilities.AbstractTest;
import domain.Trip;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(Parameterized.class)
public class TripTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private TripService	tripService;

	// Parameters -------------------------------------------------------------

	@Parameter(value = 0)
	// true is offer, false is request, type of the new post.
	public boolean		offer;

	@Parameter(value = 1)
	// User to log in
	public String		username;

	@Parameter(value = 2)
	// Days after (positive) or before (negative) today for the new post.
	public Integer		days;

	@Parameter(value = 3)
	// Description of the new post.
	public String		description;

	@Parameter(value = 4)
	// Destination of the new post.
	public String		destination;

	@Parameter(value = 5)
	// Gps latitude for the destination of the new post.
	public Double		destinationLat;

	@Parameter(value = 6)
	// Gps longitude for the destination of the new post.
	public Double		destinationLon;

	@Parameter(value = 7)
	// Origin of the new post.
	public String		origin;

	@Parameter(value = 8)
	// Gps latitude for the origin of the new post.
	public Double		originLat;

	@Parameter(value = 9)
	// Gps longitude for the origin of the new post.
	public Double		originLon;

	@Parameter(value = 10)
	// Title of the new post.
	public String		title;

	@Parameter(value = 11)
	// Error expected in this test. Null mean no error expected.
	public Class<?>		expected;


	@Parameters
	// Note 4
	public static Collection<Object[]> data() {
		final Collection<Object[]> params = new ArrayList<>();
		/*
		 * [0] En este test se crea un "offer" correctamente, sin rellenar los campos opcionales.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
		});

		/*
		 * [1] En este test se crea un "request" correctamente, sin rellenar los campos opcionales.
		 */
		params.add(new Object[] {
			false, "customer1", 1, "test", "test", null, null, "test", null, null, "test", null
		});

		/*
		 * [2] En este test se crea un "request" correctamente, rellenando los campos opcionales.
		 */
		params.add(new Object[] {
			false, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
		});

		/*
		 * [3] En este test se crea un "offer" correctamente, rellenando solo dos de los campos opcionales.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, 50.18, "test", null, null, "test", null
		});

		/*
		 * [4] En este test se crea un "offer" correctamente, rellenando solo dos de los campos opcionales.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "test", 54.23, 15.95, "test", null
		});

		/*
		 * [5] En este test se crea un "offer" correctamente, rellenando los campos opcionales.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", null
		});

		/*
		 * [6] En este test un admin intenta crear un "offer". Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "admin", 1, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
		});

		/*
		 * [7] En este test se crea un "offer" con fecha 5 dias anterior a hoy. Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "customer1", -5, "test", "test", null, null, "test", null, null, "test", IllegalArgumentException.class
		});

		/*
		 * [8] En este test se crea un "offer" con el campo description vacio. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "", "test", null, null, "test", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [9] En este test se crea un "offer" con el campo destination vacio. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "", null, null, "test", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [10] En este test se crea un "offer" con sola una coordenada de destino. Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 20.15, null, "test", null, null, "test", IllegalArgumentException.class
		});

		/*
		 * [11] En este test se crea un "offer" con sola una coordenada de destino. Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, 19.14, "test", null, null, "test", IllegalArgumentException.class
		});

		/*
		 * [12] En este test se crea un "offer" con el campo origin vacio. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [13] En este test se crea un "offer" con sola una coordenada de origen. Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "test", 20.15, null, "test", IllegalArgumentException.class
		});

		/*
		 * [14] En este test se crea un "offer" con sola una coordenada de origen. Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "test", null, 40.75, "test", IllegalArgumentException.class
		});

		/*
		 * [15] En este test se crea un "offer" con el campo title vacio. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", null, null, "test", null, null, "", TransactionSystemException.class
		});

		/*
		 * [16] En este test se crea un "offer" con una coordenada fuera de rango. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", -90.1, 50.18, "test", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [17] En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", -90., 50.18, "test", null, null, "test", null
		});

		/*
		 * [18] En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", -89.99, 50.18, "test", null, null, "test", null
		});

		/*
		 * [19] En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 89.99, 50.18, "test", null, null, "test", null
		});

		/*
		 * [20] En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 90., 50.18, "test", null, null, "test", null
		});

		/*
		 * [21] En este test se crea un "offer" con una coordenada fuera de rango. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 90.01, 50.18, "test", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [22] En este test se crea un "offer" con una coordenada fuera de rango. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, -180.01, "test", null, null, "test", TransactionSystemException.class
		});

		/*
		 * [23] En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, -180., "test", null, null, "test", null
		});

		/*
		 * [24] En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, -179.99, "test", null, null, "test", null
		});

		/*
		 * [25] En este test se crea un "offer" correctamente con una coordenada cerca del limite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, 179.99, "test", null, null, "test", null
		});

		/*
		 * [26] En este test se crea un "offer" correctamente con una coordenada en el límite de rango.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, 180., "test", 54.23, 15.95, "test", null
		});

		/*
		 * [27] En este test se crea un "offer" con una coordenada fuera de rango. Se espera un TransactionSystemException.
		 */
		params.add(new Object[] {
			true, "customer1", 1, "test", "test", 10.30, 180.01, "test", 54.23, 15.95, "test", TransactionSystemException.class
		});

		/*
		 * [28] En este test un usuario no conectado intenta crear un "offer". Se espera un IllegalArgumentException.
		 */
		params.add(new Object[] {
			true, null, 1, "test", "test", 10.30, 50.18, "test", 54.23, 15.95, "test", IllegalArgumentException.class
		});

		return params;
	}

	// Tests ------------------------------------------------------------------

	// Test para los casos de uso en los que interviene TripService. Para este A+ hemos realizado solo el test parametrizado  de post new offer/request.

	/*
	 * An actor who is authenticated as a customer must be able to:
	 * - Post an offer in which he or she advertises that he's going to move from a place to another place and would like to share his or her car with someone else.
	 * - Post a request in which he or she informs that he or she wishes to move from a place to another one and would like to find someone with whom he or she can share the trip.
	 */

	@Test
	@Transactional
	public void parameterizedTestPost() {
		Class<?> caught;
		Trip trip;
		long day;

		caught = null;
		try {

			this.authenticate(this.username);

			day = 24 * 60 * 60 * 100;
			if (this.offer)
				trip = this.tripService.createOffer();
			else
				trip = this.tripService.createRequest();

			trip.setDepartureTime(new Date(System.currentTimeMillis() + this.days * day));
			trip.setDescription(this.description);
			trip.setDestination(this.destination);
			trip.setDestinationLat(this.destinationLat);
			trip.setDestinationLon(this.destinationLon);
			trip.setOrigin(this.origin);
			trip.setOriginLat(this.originLat);
			trip.setOriginLon(this.originLon);
			trip.setTitle(this.title);

			this.tripService.save(trip);
			this.tripService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(this.expected, caught);
	}
}
