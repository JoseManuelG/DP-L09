
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//TODO Tengo que poner  querys para encontrar los mensajes enviados y recibidos de una persona

	//Find all the sended messages for a given actor
	@Query("select m from Message m where m.sender.id=?1 and m.isSender=true")
	public List<Message> findSentMessageOfActor(int senderId);

	//Find all the received messages for a given actor
	@Query("select m from Message m where m.recipient.id=?1 and m.isSender=false")
	public List<Message> findReceivedMessageOfActor(int recipientId);
}
