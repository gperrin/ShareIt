package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import fr.lyon.insa.ot.sims.shareIt.server.beans.IEventProcessor;


@Entity
@DiscriminatorValue("user_created")
public class UserCreatedEvent extends UserEvent {

	public UserCreatedEvent (){
		
	}

	@Override
	public void processEvent(IEventProcessor eventProcessor) {
		eventProcessor.processUserCreatedEvent(this);
	}
	
	
}
