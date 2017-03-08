
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository --------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting Services --------------------------------------
	@Autowired
	private Validator				validator;


	//Simple CRUD methods --------------------------------------
	public Configuration create() {
		Configuration result;
		result = new Configuration();
		return result;
	}
	public Collection<Configuration> findAll() {
		Collection<Configuration> result;
		Assert.notNull(configurationRepository);
		result = configurationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Configuration findOne() {
		Configuration result = null;
		Collection<Configuration> configurations = configurationRepository.findAll();
		result = configurations.iterator().next();
		return result;
	}

	public Configuration save(Configuration configuration) {
		Assert.notNull(configuration);
		Configuration result;
		Assert.notNull(configuration.getFee());
		Assert.isTrue(configuration.getFee() >= 0);
		result = configurationRepository.save(configuration);
		return result;
	}
	public Configuration reconstruct(Configuration configuration, BindingResult binding) {
		Configuration res, old;

		old = findOne();

		res = create();

		res.setFee(configuration.getFee());
		res.setId(old.getId());
		res.setVersion(old.getVersion());
		res.setVAT(old.getVAT());

		validator.validate(res, binding);

		return res;
	}

	//other business methods --------------------------------------

}
