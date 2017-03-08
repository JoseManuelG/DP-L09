
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("select case when count(b) > 0 then true else false end from Book b where b.creditCard.id = ?1")
	public boolean existsCreditCardForAnyBook(int creditCardId);

	//Find all the books for a given property 
	@Query("select p.books from Property p where p.id=?1")
	public Collection<Book> findBooksForPropertyId(int propertyId);

	//Dashboard-01/02
	@Query("select count(b) from Book b where b.state='ACCEPTED'")
	public Double countAcceptedBooks();

	//Dashboard-01/02
	@Query("select count(b) from Book b where b.state='DENIED'")
	public Double countDeniedBooks();

	//Returns the average number of requests for properties that have at least an audit record
	@Query("select avg(p.books.size) from Property p where p.audits is not empty")
	public Double getAverageRequestsWithAudits();

	//Returns  the average number of requests for properties that do not have any audits
	@Query("select avg(p.books.size) from Property p where p.audits is empty")
	public Double getAverageRequestsWithoutAudits();
}
