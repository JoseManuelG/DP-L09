
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

	//Dashboard-22
	@Query("select distinct a.attribute from AttributeValue a order by a.attribute desc")
	List<Attribute> getAttributesByFrequence();

}
