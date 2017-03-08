
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	//Find all the audits for a given property 
	@Query("select c from Comment c where recipient_id=?1")
	Collection<Comment> findCommentsByCustomerID(int id);

}
