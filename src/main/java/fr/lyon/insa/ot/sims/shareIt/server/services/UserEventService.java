package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.beans.IEventProcessor;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserEventRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserEvent;

@Service
public class UserEventService {

	@Autowired
	private IEventProcessor eventProcessor;
	
	@Autowired
	private UserEventRepository userEventRepository;
	
	public void persistEvent ( UserEvent userEvent){
		userEvent.processEvent(eventProcessor);
		this.userEventRepository.save(userEvent);
	}
	
	public Collection<UserEvent> getEvents ( Sharer sharer ){
		return this.userEventRepository.findByUser(sharer);
	}
	
	public Collection<? extends UserEvent> getEvents ( Sharer sharer, Class<? extends UserEvent> eventClass){
		Collection<UserEvent> allEvents = this.getEvents(sharer);
		Collection<UserEvent> filteredEvents = new ArrayList<>();
		for ( UserEvent event : allEvents ){
			if ( event.getClass() == eventClass){
				filteredEvents.add(event);
			}
		}
		return filteredEvents;
	}
}
