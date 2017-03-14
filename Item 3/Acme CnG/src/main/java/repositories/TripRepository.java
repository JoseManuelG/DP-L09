package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t where t.title like concat('%', ?1, '%') or t.description like concat('%', ?1, '%') or t.origin like concat('%', ?1, '%') or t.destination like concat('%', ?1, '%')")
	Collection<Trip> findByKeyWord(String keyword);

	@Query("select t from Trip t where t.type='OFFER'")
	Collection<Trip> findAllOffers();
	
	@Query("select t from Trip t where t.type='REQUEST'")
	Collection<Trip> findAllRequests();
}
