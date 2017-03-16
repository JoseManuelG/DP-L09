
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

	//Find all the sendt messages for a given actor
	@Query("select a from Attachment a where a.message.id=?1")
	public List<Attachment> findAttachmentsOfMessage(int messageId);

}
