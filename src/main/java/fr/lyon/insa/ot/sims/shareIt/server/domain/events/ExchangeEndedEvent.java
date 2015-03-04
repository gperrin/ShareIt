package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import fr.lyon.insa.ot.sims.shareIt.server.beans.IEventProcessor;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;

@Entity
@DiscriminatorValue("exchange_ended")
public class ExchangeEndedEvent extends UserEvent{
	@OneToOne
	private Exchange exchange;
	boolean objectReturned;
	
	public ExchangeEndedEvent (){
		
	}
	public Exchange getExchange() {
		return exchange;
	}
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}
	public boolean isObjectReturned() {
		return objectReturned;
	}
	public void setObjectReturned(boolean objectReturned) {
		this.objectReturned = objectReturned;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((exchange == null) ? 0 : exchange.hashCode());
		result = prime * result + (objectReturned ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExchangeEndedEvent))
			return false;
		ExchangeEndedEvent other = (ExchangeEndedEvent) obj;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		if (objectReturned != other.objectReturned)
			return false;
		return true;
	}
	@Override
	public void processEvent(IEventProcessor eventProcessor) {
		eventProcessor.processExchangeEndedEvent(this);
	}
	
}
