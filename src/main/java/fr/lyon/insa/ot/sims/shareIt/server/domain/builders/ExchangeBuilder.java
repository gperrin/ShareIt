package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;
import java.util.Date;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ExchangeStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;


public class ExchangeBuilder {
	private Exchange exchange;
	
	public ExchangeBuilder id ( int id ){
		exchange.setId(id);
		return this;
	}
	public ExchangeBuilder lender ( Sharer lender ){
		exchange.setLender(lender);
		return this;
	}
	public ExchangeBuilder borrower ( Sharer borrower ){
		exchange.setBorrower(borrower);
		return this;
	}
	public ExchangeBuilder product ( Product product ){
		exchange.setProduct(product);
		return this;
	}
	public ExchangeBuilder lenderRating ( double lenderRating){
		exchange.setLenderRating(lenderRating);
		return this;
	}
	public ExchangeBuilder borrowerRating ( double borrowerRating ){
		exchange.setBorrowerRating(borrowerRating);
		return this;
	}
	public ExchangeBuilder status ( ExchangeStatus status){
		exchange.setStatus(status);
		return this;
	}
	public ExchangeBuilder startDate ( Date startDate){
		exchange.setStartDate(startDate);
		return this;
	}
	public ExchangeBuilder endDate ( Date endDate){
		exchange.setEndDate(endDate);
		return this;
	}
	public Exchange build ( ){
		return exchange;
	}
}
