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

	// System under test ------------------------------------------------------
	@Autowired
	private TripService	tripService;
	private final int	PAST_OFFER		= 1000;
	private final int	PAST_REQUEST	= 999;
	private final int	FUTURE_OFFER	= 1004;
	private final int	FUTURE_REQUEST	= 1002;
	private final int	BANNED_OFFER	= 1003;
	private final int	BANNED_REQUEST	= 1001;


	// Tests ------------------------------------------------------------------

	// Test para los casos de uso en los que interviene TripService.

	@Test
	public void postOfferPositiveTest() {
		// Caso de uso en el que un usuario intenta crear una nueva oferta correctamente.
		Trip trip;
		long day;

		this.authenticate("customer1");

		day = 24 * 60 * 60 * 100;
		trip = this.tripService.createOffer();

		trip.setDepartureTime(new Date(System.currentTimeMillis() + 5 * day));
		trip.setDescription("Test");
		trip.setDestination("Test");
		trip.setOrigin("Test");
		trip.setTitle("Test");

		this.tripService.save(trip);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void postOfferNegativeTest() {
		Trip trip;
		long day;

		this.authenticate("admin");

		day = 24 * 60 * 60 * 100;
		trip = this.tripService.createOffer();

		trip.setDepartureTime(new Date(System.currentTimeMillis() + 5 * day));
		trip.setDescription("Test");
		trip.setDestination("Test");
		trip.setOrigin("Test");
		trip.setTitle("Test");

		this.tripService.save(trip);

		this.unauthenticate();
	}

	@Test
	public void postRequestPositiveTest() {
		//
		Trip trip;
		long day;

		this.authenticate("customer1");

		day = 24 * 60 * 60 * 100;
		trip = this.tripService.createRequest();

		trip.setDepartureTime(new Date(System.currentTimeMillis() + 5 * day));
		trip.setDescription("Test");
		trip.setDestination("Test");
		trip.setOrigin("Test");
		trip.setTitle("Test");

		this.tripService.save(trip);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void postRequestNegativeTest() {
		Trip trip;
		long day;

		this.authenticate("admin");

		day = 24 * 60 * 60 * 100;
		trip = this.tripService.createRequest();

		trip.setDepartureTime(new Date(System.currentTimeMillis() + 5 * day));
		trip.setDescription("Test");
		trip.setDestination("Test");
		trip.setOrigin("Test");
		trip.setTitle("Test");

		this.tripService.save(trip);

		this.unauthenticate();
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

		this.tripService.banTrip(this.PAST_OFFER);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void banOfferNegativeTest() {
		//

		this.authenticate("customer1");

		this.tripService.findAllOffers();

		this.tripService.banTrip(this.PAST_OFFER);

		this.unauthenticate();
	}

	@Test
	public void banRequestPositiveTest() {
		//

		this.authenticate("admin");

		this.tripService.findAllRequests();

		this.tripService.banTrip(this.PAST_REQUEST);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void banRequestNegativeTest() {
		//

		this.authenticate("customer1");

		this.tripService.findAllRequests();

		this.tripService.banTrip(this.PAST_REQUEST);

		this.unauthenticate();
	}

	// Ancillary methods ------------------------------------------------------

}
