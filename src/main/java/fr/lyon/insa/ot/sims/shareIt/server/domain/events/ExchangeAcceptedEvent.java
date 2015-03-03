package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;

@Entity
@DiscriminatorValue("exchange_accepted")
public class ExchangeAcceptedEvent extends UserEvent{
	
	@OneToOne
	Exchange exchange;
	
	public ExchangeAcceptedEvent (){
		
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((exchange == null) ? 0 : exchange.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExchangeAcceptedEvent))
			return false;
		ExchangeAcceptedEvent other = (ExchangeAcceptedEvent) obj;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		return true;
	}

	
	
}
