package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;

@Entity
@DiscriminatorValue("exchange_asked")
public class ExchangeAskedEvent extends UserEvent {
	@OneToOne
	private Exchange exchange;
	
	public ExchangeAskedEvent (){
		
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
		if (!(obj instanceof ExchangeAskedEvent))
			return false;
		ExchangeAskedEvent other = (ExchangeAskedEvent) obj;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		return true;
	}
}
