package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

}
