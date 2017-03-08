
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int id);

	//Returns the minimum number of social identities per actor
	@Query("select min(a.socialIdentities.size) from Actor a")
	public Integer getMinimumSocialIdentitiesPerActor();

	//Returns the average number of social identities per actor
	@Query("select avg(a.socialIdentities.size) from Actor a")
	public Double getAverageSocialIdentitiesPerActor();

	//Returns the maximum number of social identities per actor
	@Query("select max(a.socialIdentities.size) from Actor a")
	public Integer getMaximumSocialIdentitiesPerActor();

}
