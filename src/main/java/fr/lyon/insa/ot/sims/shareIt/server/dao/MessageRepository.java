package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	public Collection<Message> findBySender(Sharer sender);
	public Collection<Message> findByReceiver(Sharer receiver);
}
