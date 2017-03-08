
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Lessor;
import domain.SocialIdentity;
import domain.Tenant;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;
	
	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired 
	private AuditorService auditorService;
	
	@Autowired
	private AdministratorService administratorService;


	//Supported Services--------------------------------------------------------------------

	//Constructor--------------------------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		result = actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	//Simple CRUD methods------------------------------------------------------------
	public Actor findOne(int actorId) {
		return actorRepository.findOne(actorId);
	}

	public void save(Actor actor) {
		if (actor instanceof Tenant) {
			tenantService.save((Tenant) actor);
		} else if (actor instanceof Lessor) {
			lessorService.save((Lessor) actor);
		} else if (actor instanceof Auditor) {
			auditorService.save((Auditor) actor);
		} else if (actor instanceof Administrator) {
			administratorService.save((Administrator) actor);
		}
		
	}

	// Other Business Methods -------------------------------------------------------------

	public void setActorCollections(Actor actor) {
		actor.setSocialIdentities(new HashSet<SocialIdentity>());
	}

	public Integer getMinimumSocialIdentitiesPerActor() {
		//Dashboard-17
		Integer res = actorRepository.getMinimumSocialIdentitiesPerActor();
		return res;
	}

	public Double getAverageSocialIdentitiesPerActor() {
		//Dashboard-17
		Double res = actorRepository.getAverageSocialIdentitiesPerActor();
		return res;
	}

	public Integer getMaximumSocialIdentitiesPerActor() {
		//Dashboard-17
		Integer res = actorRepository.getMaximumSocialIdentitiesPerActor();
		return res;
	}

	public void reconstruct(Actor result, Actor origin, ActorForm actorForm) {
		UserAccount userAccount;

		userAccount = new UserAccount();
		// Setear lo que viene del formulario:

		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		userAccount.setUsername(actorForm.getUserAccount().getUsername());

		result.setUserAccount(userAccount);
		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPicture(actorForm.getPicture());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());

		// Setear lo que no viene del formulario:

		userAccount.setId(origin.getUserAccount().getId());
		userAccount.setVersion(origin.getUserAccount().getVersion());
		userAccount.setAuthorities(origin.getUserAccount().getAuthorities());

		result.setId(origin.getId());
		result.setVersion(origin.getVersion());
		result.setSocialIdentities(origin.getSocialIdentities());

	}

}
