
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;
import domain.SocialIdentity;

@Repository
public interface SocialIdentityRepository extends JpaRepository<SocialIdentity, Integer> {
	//Find all the socialIdentities for a given lessor 
		@Query("select l.socialIdentities from Lessor l where l.id=?1")
		public List<Property> findSocialIdentitiesByLessorId(int lessorId);
}
