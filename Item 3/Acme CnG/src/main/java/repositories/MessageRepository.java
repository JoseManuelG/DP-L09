
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Find all the sended messages for a given actor
	@Query("select m from Message m where m.sender.id=?1 and m.isSender=true")
	public List<Message> findSentMessageOfActor(int senderId);

	//Find all the received messages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 and m.isSender=false")
	public List<Message> findReceivedMessageOfActor(int recipientId);

	//No probado en vista, pero por queryDataBase.java funciona
	//Find all messages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 or m.sender.id=?1")
	public List<Message> findAllMessageOfActor(int ActorId);
}
