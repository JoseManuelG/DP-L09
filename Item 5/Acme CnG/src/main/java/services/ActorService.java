
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;

@Service
@Transactional
public class ActorService {

	// Managed Repository --------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private CustomerService			customerService;
	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD methods-------------------------------------------------------------------

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public void save(final Actor actor) {
		if (actor instanceof Customer)
			this.customerService.save((Customer) actor);
		else if (actor instanceof Administrator)
			this.administratorService.save((Administrator) actor);

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

}
