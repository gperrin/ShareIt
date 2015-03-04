package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import fr.lyon.insa.ot.sims.shareIt.server.beans.IEventProcessor;

@Entity
@DiscriminatorValue("user_updated")
public class UserUpdatedEvent extends UserEvent{

	public UserUpdatedEvent (){
		
	}

	@Override
	public void processEvent(IEventProcessor eventProcessor) {
		eventProcessor.processUserUpdatedEvent(this);
	}
	
}
