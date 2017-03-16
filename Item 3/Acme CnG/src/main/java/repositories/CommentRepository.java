
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.commentable.id=?1 and c.banned =false")
	Collection<Comment> findUnbannedCommentsByCommentableId(int id);

	@Query("select c from Comment c where c.commentable.id=?1 and c.banned =true")
	Collection<Comment> findBannedCommentsByCommentableId(int id);

	@Query("select c from Comment c where c.commentable.id=?1 and c.actor.id=?2 and c.banned =true")
	Collection<Comment> findBannedCommentsByCommentableId(int id, int myId);
}
