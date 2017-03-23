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
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CommentService;
import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CommentService	commentService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	//Post a comment on another actor, on an offer, or a request.

	@Test(expected = IllegalArgumentException.class)
	public void CommentNegativeTest() {
		final Comment comment = null;
		this.commentService.save(comment);
	}

	@Test
	public void CommentPositiveTest() {
		this.template("customer1", 999, "test", 3, "test", null);
	}

	@Test
	public void CommentPositiveTest2() {
		this.template("admin", 999, "test", 3, "test", null);
	}

	@Test
	public void CommentPositiveTest3() {
		this.template("customer1", 1000, "test", 3, "test", null);
	}

	@Test
	public void CommentPositiveTest4() {
		this.template("admin", 1000, "test", 3, "test", null);
	}

	@Test
	public void CommentPositiveTest5() {
		this.template("customer1", 995, "test", 3, "test", null);
	}

	@Test
	public void CommentPositiveTest6() {
		this.template("admin", 995, "test", 3, "test", null);
	}

	@Test
	public void CommentNegativeTest2() {
		this.template("", 995, "test", 3, "test", IllegalArgumentException.class);
	}

	@Test
	public void CommentNegativeTest3() {
		this.template("customer1", 0, "test", 3, "test", ConstraintViolationException.class);
	}

	@Test
	public void CommentNegativeTest4() {
		this.template("customer1", 995, "", 3, "test", ConstraintViolationException.class);
	}

	@Test
	public void CommentNegativeTest5() {
		this.template("customer1", 995, "test", -1, "test", ConstraintViolationException.class);
	}

	@Test
	public void CommentNegativeTest6() {
		this.template("customer1", 995, "test", 6, "test", ConstraintViolationException.class);
	}
	@Test
	public void CommentNegativeTest7() {
		this.template("customer1", 995, "test", 3, "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driver() {
	//		final Object testingData[][] = {
	//			{
	//				//Positivos
	//				"customer1", 999, "test", 3, "test", null
	//			}, {
	//
	//				"admin", 999, "test", 3, "test", null
	//			}, {
	//
	//				"customer1", 1000, "test", 3, "test", null
	//			}, {
	//
	//				"admin", 1000, "test", 3, "test", null
	//			}, {
	//
	//				"customer1", 995, "test", 3, "test", null
	//			}, {
	//
	//				"admin", 995, "test", 3, "test", null
	//			}, {
	//				//Negativos
	//				"", 995, "test", 3, "test", IllegalArgumentException.class
	//			}, {
	//
	//				"customer1", 0, "test", 3, "test", ConstraintViolationException.class
	//			}, {
	//
	//				"customer1", 995, "", 3, "test", ConstraintViolationException.class
	//			}, {
	//
	//				"customer1", 995, "test", -1, "test", ConstraintViolationException.class
	//			}, {
	//
	//				"customer1", 995, "test", 6, "test", ConstraintViolationException.class
	//			}, {
	//
	//				"customer1", 995, "test", 3, "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	//	}

	//Ban a comment that he or she finds inappropriate. 

	@Test
	public void BanCommentPositiveTest() {
		this.template2("admin", 1049, true, null);
	}

	@Test
	public void BanCommentPositiveTest2() {
		this.template2("admin", 1050, false, null);
	}

	@Test
	public void BanCommentNegativeTest() {
		this.template2("customer1", 1049, true, IllegalArgumentException.class);
	}

	@Test
	public void BanCommentNegativeTest2() {
		this.template2("customer1", 1050, false, IllegalArgumentException.class);
	}

	//	@Test
	//	public void driver2() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 1049, true, null
	//			}, {
	//				"admin", 1050, false, null
	//			}, {
	//				"customer1", 1049, true, IllegalArgumentException.class
	//			}, {
	//				"customer1", 1050, false, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template2((String) testingData[i][0], (int) testingData[i][1], (boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	//	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String username, final int commentableId, final String title, final int stars, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Comment comment;
			this.authenticate(username);
			comment = this.commentService.create(commentableId);
			comment.setTitle(title);
			comment.setStars(stars);
			comment.setText(text);
			this.commentService.save(comment);
			this.commentService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template2(final String username, final int commentId, final boolean banned, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			if (banned)
				this.commentService.banComment(commentId);
			else
				this.commentService.unbanComment(commentId);

			this.commentService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
