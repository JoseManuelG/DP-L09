
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private CustomerService			customerService;


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

		Assert.isNull(configurationId, "No puedes encontrar una solicitud sin ID");
		Assert.isTrue(configurationId <= 0, "La Id no es válida");

		final Configuration result = this.configurationRepository.findOne(configurationId);

		return result;
	}

	public Configuration save(final Configuration configuration) {
		Configuration result;

		Assert.notNull(configuration, "La soicitud no puede ser nula");

		result = this.configurationRepository.save(configuration);
		return result;
	}

	//Other Business methods-------------------------------------------------------------------

}
