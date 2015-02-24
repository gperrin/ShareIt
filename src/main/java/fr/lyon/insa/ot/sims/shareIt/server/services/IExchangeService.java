package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface IExchangeService {
	
	public Exchange createExhange(Sharer borrower, Sharer lender, Product product);
	public Exchange acceptExchange(Exchange exchange);
	public Exchange rejectExchange(Exchange exchange);
	public Collection<Exchange> findByBorrower(Sharer borrower);
	public Collection<Exchange> findByLender (Sharer lender);
	public Exchange setCompleted (Exchange exchange, boolean objectReturned);
	public Exchange getById(int id);
}
