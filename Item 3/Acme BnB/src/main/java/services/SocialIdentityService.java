package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import security.LoginService;
import security.UserAccount;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {
	//Managed Repository----------------------------------
	
		@Autowired
		private SocialIdentityRepository socialIdentityRepository;
		
		//SupportingServices-----------------------------------

		@Autowired
		private LoginService loginService;
		
		//Constructors----------------------------------------
		
		public SocialIdentityService(){
			super();
		}
		
		//Simple Crud methods---------------------------------
		public SocialIdentity create(){
			SocialIdentity result;
			
			result=new SocialIdentity();
			
			return result;
		}
		
		public Collection<SocialIdentity> findAll(){
			Collection<SocialIdentity> result;
			
			result= socialIdentityRepository.findAll();
			Assert.notNull(result,"La SocialIdentity devuelta no puede ser nula");
			
			return result;
		}
		
		public SocialIdentity findOne(int socialIdentityId){
			SocialIdentity result;
			result =socialIdentityRepository.findOne(socialIdentityId);
			return result;
		}

		@SuppressWarnings("static-access")
		public SocialIdentity save(SocialIdentity socialIdentity){
			Assert.notNull(socialIdentity,"La socialIdentity a guardar no puede ser nula");
			
			UserAccount userAccount=socialIdentity.getActor().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"Un usuario solo debe registrar sus propios socialIdentities");
			
			SocialIdentity result;
			result= socialIdentityRepository.save(socialIdentity);
			
			return result;
		}

		@SuppressWarnings("static-access")
		public void delete(SocialIdentity socialIdentity){
			Assert.notNull(socialIdentity,"El socialIdentity a borrar no puede ser nulo");
			Assert.isTrue(socialIdentity.getId() !=0,"El objeto socialIdentity a borrar debe tener una id valida ");
			
			UserAccount userAccount=socialIdentity.getActor().getUserAccount();
			UserAccount loginUserAccount=loginService.getPrincipal();
			Assert.isTrue(userAccount.equals(loginUserAccount),"Un usuario solo debe borrar sus propios socialIdentities");
			
			socialIdentityRepository.delete(socialIdentity);
			
			
		}
		
		//OtherBusinessesModels-------------------------------
		public List<Property> findSocialIdentitiesByLessorId(Lessor  lessor) {
			return socialIdentityRepository.findSocialIdentitiesByLessorId(lessor.getId());
		}
			
		
}
