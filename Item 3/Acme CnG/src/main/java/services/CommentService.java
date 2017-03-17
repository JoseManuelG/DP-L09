
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	//Supported Services--------------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods-------------------------------------------------------------------
	public Comment create(final int commentableId) {
		final Comment result = new Comment();
		Actor actor;
		final Commentable commentable;

		commentable = this.commentableService.findOne(commentableId);
		actor = this.actorService.findActorByPrincipal();

		result.setActor(actor);
		result.setBanned(false);
		result.setCommentable(commentable);
		result.setPostMoment(new Date(System.currentTimeMillis() - 100));
		return result;
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final Integer commentId) {

		Assert.isNull(commentId, "No puedes encontrar una solicitud sin ID");
		Assert.isTrue(commentId <= 0, "La Id no es válida");

		final Comment result = this.commentRepository.findOne(commentId);

		return result;
	}

	public Comment save(final Comment comment) {
		Comment result;
		Date currentMoment;

		currentMoment = new Date(System.currentTimeMillis() - 100);

		Assert.notNull(comment, "El comentario no puede ser nulo");

		comment.setPostMoment(currentMoment);
		comment.setBanned(false);

		result = this.commentRepository.save(comment);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment, "El comentario no puede ser nulo");
		Assert.isTrue(this.commentRepository.exists(comment.getId()), "El comentario debe estar en la base de datos antes de borrarla");

		this.commentRepository.delete(comment);
	}

	public Long count() {
		return this.commentRepository.count();
	}

	//Other Business methods-------------------------------------------------------------------

	public Collection<Comment> findUnbannedCommentsByCommentable(final int commentableId) {
		Collection<Comment> result;
		result = this.commentRepository.findUnbannedCommentsByCommentableId(commentableId);
		return result;
	}

	public Collection<Comment> findBannedCommentsByCommentable(final int commentableId, final int miId) {
		Collection<Comment> result;
		result = this.commentRepository.findBannedCommentsByCommentableId(commentableId, miId);
		return result;
	}
	public Collection<Comment> findBannedCommentsByCommentable(final int id) {
		Collection<Comment> result;
		result = this.commentRepository.findBannedCommentsByCommentableId(id);
		return result;
	}
	public Comment banComment(final int commentId) {
		Comment comment, result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		Assert.isTrue(actor instanceof Administrator, "Solo el administrador puede banear comentarios");

		comment = this.findOne(commentId);

		Assert.isTrue(!comment.getBanned(), "No puedes banear dos veces el mismo comentario");

		comment.setBanned(true);

		result = this.save(comment);

		return result;

	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		final Comment result = this.create(comment.getCommentable().getId());
		result.setText(comment.getText());
		result.setTitle(comment.getTitle());
		result.setStars(comment.getStars());

		this.validator.validate(result, binding);

		return result;
	}

	//06 - Average number of comments per actor,
	public Double avgCommentsPerActor() {
		Double result, res1;
		Long res2;
		res1 = this.commentRepository.countAllCommentsPerActors();
		res2 = this.actorService.count();

		if (res1 != null && res2 != null && res2 > 0)
			result = 1.0 * res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//06 - Average number of comments per offer
	public Double avgCommentsPerOffer() {
		Double result, res1, res2;

		res1 = this.commentRepository.countAllCommentsPerOffers();
		res2 = this.tripService.offersAmount();

		if (res1 != null && res2 != null && res2 > 0)
			result = res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//06 - Average number of comments per request
	public Double avgCommentsPerRequest() {
		Double result, res1, res2;

		res1 = this.commentRepository.countAllCommentsPerRequests();
		res2 = this.tripService.requestsAmount();

		if (res1 != null && res2 != null && res2 > 0)
			result = res1 / res2;
		else
			result = 0.0;
		return result;
	}

	//07 - Average number of comments posted by administrators and customers.
	public Double avgCommentsByActors() {
		Long res1, res2;
		Double result;

		res1 = this.count();
		res2 = this.actorService.count();

		if (res1 != null && res2 != null && res2 > 0)
			result = (double) (res1 / res2);
		else
			result = 0.0;
		return result;
	}

	public Collection<Actor> averageActorWritingComments() {
		Collection<Actor> result;
		result = this.commentRepository.averageActorWritingComments();
		return result;
	}
}
