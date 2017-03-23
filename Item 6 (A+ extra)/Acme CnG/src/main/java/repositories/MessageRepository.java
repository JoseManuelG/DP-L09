
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Find all the sended messages for a given actor
	@Query("select m from Message m where m.sender.id=?1 and m.isSender=true")
	public List<Message> findSentMessageOfActor(int senderId);

	//Find all the received messages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 and m.isSender=false")
	public List<Message> findReceivedMessageOfActor(int recipientId);

	//Find all the sended CopyMessages for a given actor
	@Query("select m from Message m where m.sender.id=?1 and m.isSender=false")
	public List<Message> findSentMessageOfActor2(int senderId);

	//Find all the received CopyMessages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 and m.isSender=true")
	public List<Message> findReceivedMessageOfActor2(int recipientId);

	//No probado en vista, pero por queryDataBase.java funciona
	//Find all messages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 or m.sender.id=?1")
	public List<Message> findAllMessageOfActor(int ActorId);

	//09 - The minimum, the average, and the maximum number of messages sent per actor. Part1
	@Query("select count(m) from Message m where m.isSender=true and m.sender is not null")
	Double avgMessagesSentPerActor();

	//09 - The minimum, the average, and the maximum number of messages sent per actor. Part2
	@Query("select count(m) from Message m where m.isSender=true and m.sender is not null")
	List<Long> minMessagesSentPerActor();

	//09 - The minimum, the average, and the maximum number of messages sent per actor. Part2
	@Query("select count(m) from Message m where m.isSender=true and m.sender is not null group by m.sender.id order by count(m) desc")
	List<Long> maxMessagesSentPerActor();

	//10 - The minimum, the average, and the maximum number of messages received per actor. Part1
	@Query("select count(m) from Message m where m.isSender=false and m.recipient is not null")
	Double avgMessagesReceivedPerActor();

	//10 - The minimum, the average, and the maximum number of messages received per actor. Part2
	@Query("select count(m) from Message m where m.isSender=false and m.recipient is not null group by m.recipient.id order by count(m) asc")
	List<Long> minMessagesReceivedPerActor();

	//10 - The minimum, the average, and the maximum number of messages received per actor. Part2
	@Query("select count(m) from Message m where m.isSender=false and m.recipient is not null group by m.recipient.id order by count(m) desc")
	List<Long> maxMessagesReceivedPerActor();

	//11 - The actors who have sent more messages.
	@Query("select m.sender from Message m where m.isSender=true and m.sender is not null group by m.sender order by count(m) desc")
	List<Actor> actorSentMoreMessages();

	//12 - The actors who have received more messages.
	@Query("select m.recipient from Message m where m.isSender=false and m.recipient is not null group by m.recipient order by count(m) desc")
	List<Actor> actorReceivedMoreMessages();

}
