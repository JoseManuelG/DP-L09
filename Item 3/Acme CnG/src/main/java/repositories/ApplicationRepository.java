
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Customer;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select distinct a from Application a where a.trip.id=?1")
	Collection<Application> findAllApplicationsByTripId(int tripId);

	//03 - Average number of applications per offer or request.
	@Query("select count(a) from Application a")
	Double countAllApplications();

	//04 - The customer who has more applications accepted.
	@Query("select a.customer from Application a where a.status='ACCEPTED' group by a.customer order by count(a) desc")
	Collection<Customer> customerWithMoreApplicationsAccepted();

	//05 - The customer who has more applications denied.
	@Query("select a.customer from Application a where a.status='DENIED' group by a.customer order by count(a) desc")
	Collection<Customer> customerWithMoreApplicationsDenied();
}
