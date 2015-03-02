package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface ExchangeRepository extends CrudRepository<Exchange, Integer> {
	public Collection<Exchange> findByBorrower(Sharer borrower);
	public Collection<Exchange> findByLender (Sharer lender);
}
