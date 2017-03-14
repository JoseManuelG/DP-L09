package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed Repository --------------------------------------
	@Autowired
	private ActorRepository actorRepository;
	// Supporting Services --------------------------------------
	

	
	
	// other business methods --------------------------------------

	public Actor findActorByPrincipal() {
		Actor result;
		result = actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}


}
