package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;
import java.util.Date;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ExchangeStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface IExchangeService {
	
	public Exchange createExhange(Sharer borrower,  Product product, Date startDate, Date endDate);
	public Exchange acceptExchange(Exchange exchange);
	public Exchange rejectExchange(Exchange exchange);
	public Collection<Exchange> findByBorrower(Sharer borrower);
	public Collection<Exchange> findByBorrower(Sharer borrower, ExchangeStatus status);
	public Collection<Exchange> findByLender (Sharer lender);
	public Collection<Exchange> findByLender (Sharer lender, ExchangeStatus status);
	public Exchange setCompleted (Exchange exchange, boolean objectReturned);
	public Exchange getById(int id);
}
