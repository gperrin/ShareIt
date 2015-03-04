package fr.lyon.insa.ot.sims.shareIt.server.beans;

import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAcceptedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAskedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeEndedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeRejectedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.MessageCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductDeletedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserRatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserUpdatedEvent;

public interface IEventProcessor {
	public void processExchangeAcceptedEvent(ExchangeAcceptedEvent exchangeAcceptedEvent);
	public void processExchangeAskedEvent(ExchangeAskedEvent exchangeAskedEvent);
	public void processExchangeRejectedEvent ( ExchangeRejectedEvent exchangeRejectedEvent);
	public void processExchangeEndedEvent ( ExchangeEndedEvent exchangeEndedEvent);
	public void processMessageCreatedEvent ( MessageCreatedEvent messageCreatedEvent);
	public void processProductCreatedEvent ( ProductCreatedEvent productCreatedEvent);
	public void processProductDeletedEvent ( ProductDeletedEvent productDeletedEvent);
	public void processUserCreatedEvent ( UserCreatedEvent userCreatedEvent);
	public void processUserRatedEvent (UserRatedEvent userRatedEvent);
	public void processUserUpdatedEvent (UserUpdatedEvent userUpdatedEvent);
}
