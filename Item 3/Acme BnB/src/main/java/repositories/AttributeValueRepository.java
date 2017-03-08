package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AttributeValue;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {

	//Find all the attribute values for a given property 
	@Query("select p.attributeValues from Property p where p.id=?1")
	public Collection<AttributeValue> findAttributeValuesForPropertyId(int propertyId);
	//Find all the attribute values for a given property 
	@Query("select p from AttributeValue p where attribute_id=?1")
	public Collection<AttributeValue> findAttributeValuesForAttributeId(int attributeId);

}
