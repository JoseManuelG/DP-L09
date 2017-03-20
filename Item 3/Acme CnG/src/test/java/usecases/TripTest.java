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


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	@Test
	public void postPositiveTest() {
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
	public void postNegativeTest1() {
		Trip trip;
		long day;

		this.authenticate("administrator1");

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

	// Ancillary methods ------------------------------------------------------

}
