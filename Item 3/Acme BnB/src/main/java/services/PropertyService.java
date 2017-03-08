
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PropertyRepository;
import domain.AttributeValue;
import domain.Audit;
import domain.Book;
import domain.Lessor;
import domain.Property;

@Service
@Transactional
public class PropertyService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private PropertyRepository	propertyRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private BookService			bookService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private FinderService		finderService;

	// Validator --------------------------------------------------------------------
	@Autowired
	private Validator			validator;


	// Constructor --------------------------------------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD Methods ------------------------------------------------------------

	public Property create(int lessorId) {
		Property result;
		result = new Property();
		result.setAttributeValues(new ArrayList<AttributeValue>());
		result.setAudits(new ArrayList<Audit>());
		result.setBooks(new ArrayList<Book>());
		result.setLastUpdate(new Date(System.currentTimeMillis() - 100));
		result.setLessor(lessorService.findOne(lessorId));
		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;
		result = propertyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Property findOne(int propertyId) {
		Property result;
		result = propertyRepository.findOne(propertyId);
		return result;
	}

	public Property save(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(lessorService.findByPrincipal().equals(property.getLessor()));
		property.setLastUpdate(new Date(System.currentTimeMillis() - 100));
		Property result;
		result = propertyRepository.save(property);
		return result;
	}

	public void delete(Property property) {
		Assert.notNull(property, "La propiedad no puede ser nula");
		Assert.isTrue(property.getId() != 0, "La propiedad debe estar antes en la base de datos");
		propertyRepository.exists(property.getId());
		Assert.isTrue(lessorService.findByPrincipal().equals(property.getLessor()));
		if(!property.getBooks().isEmpty())
			bookService.removePropertyFromBooks(property.getBooks());
		if(!property.getAudits().isEmpty())
			auditService.deleteAuditsForProperty(property);
		
		Lessor lessor = property.getLessor();
		Collection<Property> properties = lessor.getLessorProperties();
		properties.remove(property);
		lessor.setLessorProperties(properties);
		lessorService.save(lessor);
		finderService.removeProperty(property);
		propertyRepository.delete(property);

	}
	// Other Bussiness Methods --------------------------------------------------------

	public Collection<Property> findPropertiesByLessor(Lessor lessor) {
		Collection<Property> result = propertyRepository.findPropertiesByLessorId(lessor.getId());
		return result;
	}

	public List<Property> findPropertiesByLessorByNumberOfAudits(int lessorId) {
		//Dashboard-12
		List<Property> properties, result;

		properties = propertyRepository.findPropertiesByLessorId(lessorId);
		result = propertyRepository.findPropertiesByLessorByNumberOfAudits(lessorId);
		for (Property p : properties) {
			if (!result.contains(p))
				result.add(p);
		}
		return result;

	}

	public List<Property> findPropertiesByLessorOrderedByRequestNumber(int lessorId) {
		//Dashboard-13
		List<Property> properties, result;

		properties = propertyRepository.findPropertiesByLessorId(lessorId);
		result = propertyRepository.findPropertiesByLessorIdOrderedByRequestNumber(lessorId);
		for (Property p : properties) {
			if (!result.contains(p))
				result.add(p);
		}
		return result;
	}

	public List<Property> findPropertiesByLessorWithAcceptedBooks(int lessorId) {
		//Dashboard-14
		List<Property> properties, result;

		properties = propertyRepository.findPropertiesByLessorId(lessorId);
		result = propertyRepository.findPropertiesByLessorIdWithAcceptedBooks(lessorId);
		for (Property p : properties) {
			if (!result.contains(p))
				result.add(p);
		}
		return result;
	}

	public List<Property> findPropertiesByLessorWithDenieBooks(int lessorId) {
		//Dashboard-15
		List<Property> properties, result;

		properties = propertyRepository.findPropertiesByLessorId(lessorId);
		result = propertyRepository.findPropertiesByLessorIdWithDeniedBooks(lessorId);
		for (Property p : properties) {
			if (!result.contains(p))
				result.add(p);
		}
		return result;
	}

	public List<Property> findPropertiesByLessorWithPendingBooks(int lessorId) {
		//Dashboard-16
		List<Property> properties, result;

		properties = propertyRepository.findPropertiesByLessorId(lessorId);
		result = propertyRepository.findPropertiesByLessorIdWithPendingBooks(lessorId);
		for (Property p : properties) {
			if (!result.contains(p))
				result.add(p);
		}
		return result;
	}

	public List<Audit> findAuditsByProperty(Property property) {

		return propertyRepository.findAuditsByProperty(property.getId());
	}

	public Property reconstruct(Property property, BindingResult bindingResult) {
		Property result, original;

		if (property.getId() == 0) {
			int lessorId = lessorService.findByPrincipal().getId();
			result = this.create(lessorId);
		} else {
			original = propertyRepository.findOne(property.getId());
			result = new Property();
			result.setAttributeValues(original.getAttributeValues());
			result.setAudits(original.getAudits());
			result.setBooks(original.getBooks());
			result.setLastUpdate(original.getLastUpdate());
			result.setId(original.getId());
			result.setLessor(original.getLessor());
			result.setVersion(original.getVersion());
		}
		result.setName(property.getName());
		result.setRate(property.getRate());
		result.setDescription(property.getDescription());
		result.setAddress(property.getAddress());
		validator.validate(result, bindingResult);
		return result;

	}

	public Collection<Property> findAllOrdered() {
		return propertyRepository.findAllOrdered();
	}

}
