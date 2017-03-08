
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttributeValueRepository;
import domain.Actor;
import domain.Attribute;
import domain.AttributeValue;
import domain.Property;

@Service
@Transactional
public class AttributeValueService {

	//Managed Repository---------------------------—

	@Autowired
	private AttributeValueRepository	attributeValueRepository;

	//Supporting services---------------------------—
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PropertyService propertyService;

	//Constructors------------------------------------

	public AttributeValueService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public AttributeValue create() {
		AttributeValue result;

		result = new AttributeValue();

		return result;
	}

	public Collection<AttributeValue> findAll() {
		Collection<AttributeValue> result;

		result = attributeValueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public AttributeValue findOne(int attributeValueId) {
		AttributeValue result;

		result = attributeValueRepository.findOne(attributeValueId);
		Assert.notNull(result);

		return result;
	}

	public AttributeValue save(AttributeValue attributeValue) {
		Assert.notNull(attributeValue,"El attributeValue no puede ser nulo");
		
		Assert.hasText(attributeValue.getValue(), "El atributeValue debe tener un valor");
		
		Assert.notNull(attributeValue.getProperty(),"Un AttributeValue debe estar asignado a una propperty");
		Assert.notNull(attributeValue.getAttribute(),"Un AttributeValue debe estar asignado a un attribute");
		
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.equals(attributeValue.getProperty().getLessor()), "Solo puedes editar tus propias propiedades");
		
		AttributeValue result;
		result = attributeValueRepository.save(attributeValue);
		propertyService.save(result.getProperty());
		return result;
	}

	public void delete(AttributeValue attributeValue) {
		Assert.notNull(attributeValue, "El attributeValueo no puede ser nulo");
		Assert.isTrue(attributeValue.getId() != 0, "El attributeValueo debe estar en la base de datos");
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.equals(attributeValue.getProperty().getLessor()), "Solo puedes editar tus propias propiedades");
		propertyService.save(attributeValue.getProperty());
		attributeValueRepository.delete(attributeValue);
		
	}

	// Other bussiness methods ----------------------------------------------------------------------------------------
	public Collection<AttributeValue> findAttributeValuesForProperty(Property property) {
		Collection<AttributeValue> result = attributeValueRepository.findAttributeValuesForPropertyId(property.getId());
		return result;
	}
	
	public Collection<AttributeValue> findAttributeValuesForAttribute(Attribute attribute) {
		Collection<AttributeValue> result = attributeValueRepository.findAttributeValuesForAttributeId(attribute.getId());
		return result;
	}
	public void deleteAttributeValuesForProperty(Property property){
		Collection<AttributeValue> avs = attributeValueRepository.findAttributeValuesForPropertyId(property.getId());
		if(!avs.isEmpty()){
			for(AttributeValue av:avs){
				av.setProperty(null);
				av.setAttribute(null);
				attributeValueRepository.delete(av);
			}
		}
	}

}
