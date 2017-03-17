
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t where t.type='OFFER' and (t.title like concat('%', ?1, '%') or t.description like concat('%', ?1, '%') or t.origin like concat('%', ?1, '%') or t.destination like concat('%', ?1, '%'))")
	Collection<Trip> findAllOffersByKeyWord(String keyword);
	
	@Query("select t from Trip t where t.type='REQUEST' and (t.title like concat('%', ?1, '%') or t.description like concat('%', ?1, '%') or t.origin like concat('%', ?1, '%') or t.destination like concat('%', ?1, '%'))")
	Collection<Trip> findAllRequestsByKeyWord(String keyword);
	
	@Query("select t from Trip t where t.type='OFFER' and t.banned = FALSE and t.departureTime > CURRENT_TIMESTAMP and (t.title like concat('%', ?1, '%') or t.description like concat('%', ?1, '%') or t.origin like concat('%', ?1, '%') or t.destination like concat('%', ?1, '%'))")
	Collection<Trip> findAllValidOffersByKeyWord(String keyword);
	
	@Query("select t from Trip t where t.type='REQUEST' and t.banned = FALSE and t.departureTime > CURRENT_TIMESTAMP and (t.title like concat('%', ?1, '%') or t.description like concat('%', ?1, '%') or t.origin like concat('%', ?1, '%') or t.destination like concat('%', ?1, '%'))")
	Collection<Trip> findAllValidRequestsByKeyWord(String keyword);
	
	@Query("select t from Trip t where t.type='OFFER'")
	Collection<Trip> findAllOffers();

	@Query("select t from Trip t where t.type='REQUEST'")
	Collection<Trip> findAllRequests();

	@Query("select t from Trip t where t.type='OFFER' and t.customer.id=?1")
	Collection<Trip> findAllOffersByPrincipalId(int customerId);

	@Query("select t from Trip t where t.type='REQUEST' and t.customer.id=?1")
	Collection<Trip> findAllRequestsByPrincipalId(int customerId);

	//01 - Ratio of offers versus requests. Part1
	@Query("select count(t) from Trip t where t.type='OFFER'")
	Double countAllOffers();

	//01 - Ratio of offers versus requests. Part2
	@Query("select count(t) from Trip t where t.type='REQUEST'")
	Double countAllRequests();

	//03 - Average number of applications per offer or request.
	@Query("select count(t) from Trip t")
	Double countAllTrips();

}
