
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository --------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------
	public Administrator create() {
		final Administrator result = new Administrator();
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result;

		Assert.notNull(administrator);
		result = this.administratorRepository.save(administrator);
		Assert.notNull(result, "error.commit");

		return result;

	}

	// other business methods --------------------------------------

	public Administrator findAdministratorByPrincipal() {
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

}
