package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	
	//TODO Tengo que poner querys para encontrar los mensajes enviados y recibidos de una persona
}
