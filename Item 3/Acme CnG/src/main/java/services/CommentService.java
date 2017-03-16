
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

}
