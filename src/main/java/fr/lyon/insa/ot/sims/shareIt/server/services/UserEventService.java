package fr.lyon.insa.ot.sims.shareIt.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.UserEventRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserEvent;

@Service
public class UserEventService {

	@Autowired
	private UserEventRepository userEventRepository;
	
	public void persistEvent ( UserEvent userEvent){
		this.userEventRepository.save(userEvent);
	}
}
