package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("user_updated")
public class UserUpdatedEvent extends UserEvent{

	public UserUpdatedEvent (){
		
	}
	
}
