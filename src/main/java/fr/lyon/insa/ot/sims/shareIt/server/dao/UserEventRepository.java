package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserEvent;

public interface UserEventRepository extends CrudRepository<UserEvent, Integer>{
	public Collection<UserEvent> findByUser ( Sharer user);
}
