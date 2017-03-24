
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.commentable.id=?1")
	Collection<Comment> findAllCommentsByCommentableId(int id);

	@Query("select c from Comment c where c.actor.id=?1")
	Collection<Comment> findAllCommentsByActorId(int id);

	@Query("select c from Comment c where c.commentable.id=?1 and c.banned =false")
	Collection<Comment> findUnbannedCommentsByCommentableId(int id);

	@Query("select c from Comment c where c.commentable.id=?1 and c.banned =true")
	Collection<Comment> findBannedCommentsByCommentableId(int id);

	@Query("select c from Comment c where c.commentable.id=?1 and c.actor.id=?2 and c.banned =true")
	Collection<Comment> findBannedCommentsByCommentableId(int id, int myId);

	//06 - Average number of comments per actor, offer, or request Part1
	@Query("select count(c) from Comment c where c.commentable.class = Customer or c.commentable.class = Administrator")
	Double countAllCommentsPerActors();

	//06 - Average number of comments per actor, offer, or request Part1
	@Query("select count(c) from Comment c where c.commentable.class = Trip and c.commentable.type='OFFER'")
	Double countAllCommentsPerOffers();

	//06 - Average number of comments per actor, offer, or request Part1
	@Query("select count(c) from Comment c where c.commentable.class = Trip and c.commentable.type='REQUEST'")
	Double countAllCommentsPerRequests();

	//08 - The actors who have posted ±10% the average number of comments per actor.
	@Query("select c.actor from Comment c where (select count(c2) from Comment c2 where c.actor.id=c2.actor.id)>=?1*0.9 and (select count(c3) from Comment c3 where c.actor.id=c3.actor.id)<=?1*1.1 group by c.actor.id")
	Collection<Actor> averageActorWritingComments(Double double1);
}
