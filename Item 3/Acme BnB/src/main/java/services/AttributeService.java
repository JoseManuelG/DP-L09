
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttributeRepository;
import security.Authority;
import security.LoginService;
import domain.Attribute;

@Service
@Transactional
public class AttributeService {

	//Managed Repository---------------------------—

	@Autowired
	private AttributeRepository	attributeRepository;

	//Supporting services---------------------------—
	@Autowired
	private LoginService		loginService;


	//Constructors------------------------------------

	public AttributeService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public Attribute create() {
		Attribute result;

		result = new Attribute();
		//		Collection<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
		//		result.setAttributeValues(attributeValues);
		return result;
	}

	public Collection<Attribute> findAll() {
		Collection<Attribute> result;

		result = attributeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Attribute findOne(int attributeId) {
		Attribute result;

		result = attributeRepository.findOne(attributeId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public Attribute save(Attribute attribute) {
		Assert.hasText(attribute.getName(), "El atributo debe tener un nombre");
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(loginService.getPrincipal().getAuthorities());
		Assert.isTrue(authorities.get(0).getAuthority().equals(Authority.ADMINISTRATOR), "Para poder Guardar un Attribute debes ser admninistrador");

		Attribute result;
		result = attributeRepository.save(attribute);

		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Attribute attribute) {
		Assert.notNull(attribute, "El attributeo no puede ser nulo");
		Assert.isTrue(attribute.getId() != 0, "El attributeo debe estar en la base de datos");
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(loginService.getPrincipal().getAuthorities());
		Assert.isTrue(authorities.get(0).getAuthority().equals(Authority.ADMINISTRATOR), "Para poder Guardar un Attribute debes ser admninistrador");
		attributeRepository.delete(attribute);
	}

	public List<Attribute> getAttributesByFrequence() {
		//Dashboard-22
		List<Attribute> result, attributes;

		result = attributeRepository.getAttributesByFrequence();
		attributes = attributeRepository.findAll();

		for (Attribute a : attributes) {
			if (!result.contains(a))
				result.add(a);
		}
		return result;
	}

}
