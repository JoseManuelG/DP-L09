
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;
import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	//Find all the properties for a given lessor 
	@Query("select p from Property p where p.lessor.id=?1 order by p.books.size desc")
	public List<Property> findPropertiesByLessorId(int lessorId);

	//Dashboard-12
	@Query("select p from Property p where p.lessor.id=?1 order by p.audits.size desc")
	public List<Property> findPropertiesByLessorByNumberOfAudits(int lessorId);

	//Returns every property of a given lessor ordered by number of requests
	@Query("select p from Property p where p.lessor.id=?1 order by p.books.size desc")
	public List<Property> findPropertiesByLessorIdOrderedByRequestNumber(int lessorId);

	//Returns every property of a given lessor with accepted book requests
	@Query("select distinct b.property from Book b where b.lessor.id=?1 and b.state='ACCEPTED' order by b.state desc")
	public List<Property> findPropertiesByLessorIdWithAcceptedBooks(int lessorId);

	//Returns every property of a given lessor with denied book requests
	@Query("select distinct b.property from Book b where b.lessor.id=?1 and b.state='DENIED' order by b.state desc")
	public List<Property> findPropertiesByLessorIdWithDeniedBooks(int lessorId);

	//Returns every property of a given lessor with pending book requests
	@Query("select distinct b.property from Book b where b.lessor.id=?1 and b.state='PENDING' order by b.state desc")
	public List<Property> findPropertiesByLessorIdWithPendingBooks(int lessorId);

	//Returns every audit of a given property
	@Query("select a from Audit a where a.property.id =?1 and a.draftMode=false")
	public List<Audit> findAuditsByProperty(int id);

	@Query("select p from Property p order by p.books.size desc")
	public Collection<Property> findAllOrdered();

}
