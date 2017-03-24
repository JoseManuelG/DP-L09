/*
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

import services.MessageService;
import utilities.AbstractTest;
import domain.Attachment;
import domain.Message;
import forms.MessageForm;

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

	//Caso de uso de escribir un mensaje:
	//test positivo
	@Test
	public void WriteMessageTest1() {
		this.templateWriteMessage("customer1", 994, "test title", "test text", null);
	}
	//sin loguearse
	@Test
	public void WriteMessageTest2() {
		this.templateWriteMessage(null, 994, "test title", "test text", IllegalArgumentException.class);
	}
	//recipient no válido
	@Test
	public void WriteMessageTest3() {
		this.templateWriteMessage("customer1", 1, "test title", "test text", IllegalArgumentException.class);
	}
	//title blank
	@Test
	public void WriteMessageTest4() {
		this.templateWriteMessage("customer1", 994, "", "test text", ConstraintViolationException.class);
	}
	//text blank
	@Test
	public void WriteMessageTest5() {
		this.templateWriteMessage("customer1", 994, "test title", "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driverWriteMessage() {
	//		final Object testingData[][] = {
	//			{
	//				"customer1", 994, "test title", "test text", null
	//			}, {
	//				null, 994, "test title", "test text", IllegalArgumentException.class
	//			}, {
	//				"customer1", 1, "test title", "test text", IllegalArgumentException.class
	//			}, {
	//				"customer1", 994, "", "test text", ConstraintViolationException.class
	//			}, {
	//				"customer1", 994, "test title", "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateWriteMessage((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	//	}

	//Caso de uso de responder a un mensaje:
	//test positivo
	@Test
	public void ReplyMessageTest1() {
		this.templateReplyMessage("admin", 564, "test title", "test text", null);
	}
	//mensaje sin sender(cuenta borrada del sender)
	@Test
	public void ReplyMessageTest2() {
		this.templateReplyMessage("admin", 587, "test title", "test text", IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void ReplyMessageTest3() {
		this.templateReplyMessage("admin", 605, "test title", "test text", IllegalArgumentException.class);
	}
	//sin loguearse
	@Test
	public void ReplyMessageTest4() {
		this.templateReplyMessage(null, 564, "test title", "test text", IllegalArgumentException.class);
	}
	//titulo blank
	@Test
	public void ReplyMessageTest5() {
		this.templateReplyMessage("admin", 564, "", "test text", ConstraintViolationException.class);
	}
	//texto blank
	@Test
	public void ReplyMessageTest6() {
		this.templateReplyMessage("admin", 564, "test title", "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driveReplyMessage() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, "test title", "test text", null
	//			}, {
	//				"admin", 587, "test title", "test text", IllegalArgumentException.class
	//			}, {
	//				"admin", 605, "test title", "test text", IllegalArgumentException.class
	//			}, {
	//				null, 564, "test title", "test text", IllegalArgumentException.class
	//			}, {
	//				"admin", 564, "", "test text", ConstraintViolationException.class
	//			}, {
	//				"admin", 564, "test title", "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateReplyMessage((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	//	}

	//Caso de uso de reenviar un mensaje:
	//test positivo
	@Test
	public void ForwardMessageTest1() {
		this.templateForwardMessage("admin", 564, 993, null);
	}
	//sin loguearse
	@Test
	public void ForwardMessageTest2() {
		this.templateForwardMessage(null, 564, 993, IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void ForwardMessageTest3() {
		this.templateForwardMessage("admin", 605, 993, IllegalArgumentException.class);
	}
	//recipient no válido
	@Test
	public void ForwardMessageTest4() {
		this.templateForwardMessage("admin", 564, 1, IllegalArgumentException.class);
	}

	//	@Test
	//	public void driveForwardMessage() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, 993, null
	//			}, {
	//				null, 564, 993, IllegalArgumentException.class
	//			}, {
	//				"admin", 605, 993, IllegalArgumentException.class
	//			}, {
	//				"admin", 564, 1, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateForwardMessage((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	//	}

	//Caso de uso de borrar un mensaje:
	//test positivo
	@Test
	public void DeleteMessageTest1() {
		this.templateDeleteMessage("admin", 564, null);
	}
	//sin loguearse
	@Test
	public void DeleteMessageTest2() {
		this.templateDeleteMessage(null, 564, IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void DeleteMessageTest3() {
		this.templateDeleteMessage("admin", 605, IllegalArgumentException.class);
	}

	//	@Test
	//	public void driveDeleteMessage() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, null
	//			}, {
	//				null, 564, IllegalArgumentException.class
	//			}, {
	//				"admin", 605, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateDeleteMessage((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

	// Ancillary methods ------------------------------------------------------

	protected void templateWriteMessage(final String username, final int recipientId, final String title, final String text, final Class<?> expected) {
		Class<?> caught;
		Message message;
		Collection<Attachment> attachments;

		caught = null;
		try {
			this.authenticate(username);
			this.messageService.findSentMessageOfPrincipal();

			attachments = new ArrayList<Attachment>();
			message = this.messageService.create(recipientId);

			message.setTitle(title);
			message.setText(text);

			this.messageService.save(message, attachments);
			this.messageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateReplyMessage(final String username, final int messageId, final String title, final String text, final Class<?> expected) {
		Class<?> caught;
		Message message;
		Collection<Attachment> attachments;
		MessageForm messageForm;

		caught = null;
		try {
			this.authenticate(username);
			this.messageService.findReceivedMessageOfPrincipal();

			attachments = new ArrayList<Attachment>();
			messageForm = this.messageService.replyMessage(messageId);
			message = this.messageService.create(messageForm.getRecipient().getId());

			message.setTitle(title);
			message.setText(text);

			this.messageService.save(message, attachments);
			this.messageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateForwardMessage(final String username, final int messageId, final int recipientId, final Class<?> expected) {
		Class<?> caught;
		Message message;
		Collection<Attachment> attachments;
		MessageForm messageForm;
		caught = null;
		try {
			this.authenticate(username);
			this.messageService.findReceivedMessageOfPrincipal();

			messageForm = this.messageService.forwardMessage(messageId);
			attachments = messageForm.getAttachments();
			message = this.messageService.create(recipientId);

			message.setTitle(messageForm.getTitle());
			message.setText(messageForm.getText());

			this.messageService.save(message, attachments);
			this.messageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteMessage(final String username, final int messageId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			this.messageService.findReceivedMessageOfPrincipal();
			this.messageService.delete(messageId);

			this.messageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
