
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	//Supported Services--------------------------------------------------------------------

	@Autowired
	private TripService			tripService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods-------------------------------------------------------------------
	public Comment create() {
		final Comment result = new Comment();
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		result.setActor(actor);
		result.setBanned(false);

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

		Assert.notNull(comment, "La solicitud no puede ser nula");

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

}
