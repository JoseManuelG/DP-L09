
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supported Services--------------------------------------------------------------------

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods-------------------------------------------------------------------
	public Configuration create() {
		final Configuration result = new Configuration();

		return result;
	}

	//Solo puede haber uno, lo he puesto por si hay problemas con el find one, si no los hay, se puede eliminar,
	//ademas si solo hay uno, siempre nos devolvera una coleccion que tenga solo ese
	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final Integer configurationId) {

		Assert.notNull(configurationId, "No puedes encontrar una solicitud sin ID");
		Assert.isTrue(configurationId > 0, "La Id no es v�lida");

		final Configuration result = this.configurationRepository.findOne(configurationId);

		return result;
	}

	public Configuration save(final Configuration configuration) {
		Configuration result;

		Assert.notNull(configuration, "La soicitud no puede ser nula");
		Assert.isTrue(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"), "Solo el administrador podra modificar la configurcion");
		result = this.configurationRepository.save(configuration);
		return result;
	}
	public void flush() {
		this.configurationRepository.flush();
	}

	//Other Business methods-------------------------------------------------------------------

	public Configuration findOne() {
		Configuration result;

		result = this.findAll().iterator().next();

		return result;
	}

	public Configuration reconstruct(final Configuration configuration, final BindingResult binding) {
		Configuration result, original;

		original = this.findOne();

		result = new Configuration();
		result.setBanner(configuration.getBanner());
		result.setId(original.getId());
		result.setVersion(original.getVersion());

		this.validator.validate(result, binding);

		return result;
	}
}