
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class ActorService {

	// Managed Repository --------------------------------------
	@Autowired
	private ActorRepository			actorRepository;
	@Autowired
	private UserAccountRepository	userAccountRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private MessageService			messageService;
	@Autowired
	private CommentService			commentService;
	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD methods-------------------------------------------------------------------

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public void save(final Actor actor) {
		if (actor instanceof Administrator)
			this.administratorService.save((Administrator) actor);
		//		else if (actor instanceof Customer)
		//			this.customerService.save((Customer) actor);

	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Long count() {
		return this.actorRepository.count();
	}
	// other business methods --------------------------------------

	public Actor findActorByPrincipal() {
		Actor result;
		result = this.actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public void delete() {
		final Actor actor = this.findActorByPrincipal();
		this.messageService.deleteActor(actor);
		this.commentService.deleteAllCommentsOfActor(actor);
		this.actorRepository.delete(actor);
		this.userAccountRepository.delete(actor.getUserAccount().getId());

	}

}
