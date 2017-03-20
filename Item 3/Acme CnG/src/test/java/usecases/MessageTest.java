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
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.MessageService;
import utilities.AbstractTest;
import domain.Attachment;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	//Caso de uso positivo de escribir un mensaje
	@Test
	public void writeMessagePositiveTest() {
		Message message;
		List<Attachment> attachments;

		this.authenticate("customer1");
		this.messageService.findReceivedMessageOfPrincipal();

		attachments = new ArrayList<Attachment>();
		message = this.messageService.create(994);

		message.setTitle("cottect test title");
		message.setText("correct test text");

		this.messageService.save(message, attachments);

		this.messageService.flush();

		this.unauthenticate();

	}

	//	@Test
	//	public void samplePositiveTest() {
	//		Assert.isTrue(true);
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void sampleNegativeTest() {
	//		Assert.isTrue(false);
	//	}
	//
	//	@Test
	//	public void driver() {
	//		final Object testingData[][] = {
	//			{
	//				"customer1", 33, null
	//			}, {
	//				null, 33, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

	// Ancillary methods ------------------------------------------------------

	//	protected void template(final String username, final int announcementId, final Class<?> expected) {
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			this.authenticate(username);
	//			this.customerService.registerPrincipal(announcementId);
	//			unauthenticate();
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//		this.checkExceptions(expected, caught);
	//	}
}
