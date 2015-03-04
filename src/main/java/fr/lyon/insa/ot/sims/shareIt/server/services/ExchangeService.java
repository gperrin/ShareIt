package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.ExchangeRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ExchangeStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;

@Service
public class ExchangeService{

	@Autowired
	ExchangeRepository exchangeRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	public Exchange createExhange(Sharer borrower, Product product, Date startDate, Date endDate) {
		Date today = new Date();
		if ( product.getStatus().equals(ProductStatus.disponible) &&
				borrower != product.getSharer() && 
				startDate.compareTo(endDate) <= 0 &&
				today.getTime()*1000*60*60*24 <= startDate.getTime()){
			Exchange exchange = new Exchange();
			exchange.setBorrower(borrower);
			exchange.setLender(product.getSharer());
			exchange.setProduct(product);
			exchange.setStatus(ExchangeStatus.issued);
			exchange.setStartDate(startDate);
			exchange.setEndDate(endDate);
			this.exchangeRepository.save(exchange);
			return exchange;
		}
		else{
			throw new BusinessLogicException("Required product is currently unavailable");
		}
	}

	public Exchange acceptExchange(Exchange exchange) {
		if ( exchange.getProduct().getStatus().equals(ProductStatus.disponible)
				&& exchange.getStatus().equals(ExchangeStatus.issued)){
			Product product = exchange.getProduct();
			product.setStatus(ProductStatus.emprunte);
			this.productRepository.save(product);
			exchange.setStatus(ExchangeStatus.accepted);
			this.exchangeRepository.save(exchange);
			return exchange;
		}
		else{
			throw new BusinessLogicException("This exchange has already either been accepted or refused");
		}
		
	}

	public Collection<Exchange> findByBorrower(Sharer borrower) {
		Collection<Exchange> exchanges = new ArrayList<>();
		exchanges.addAll(this.exchangeRepository.findByBorrower(borrower));
		return exchanges;
	}

	public Collection<Exchange> findByLender(Sharer lender) {
		Collection<Exchange> exchanges = new ArrayList<>();
		exchanges.addAll(this.exchangeRepository.findByLender(lender));
		return exchanges;
	}

	public Exchange setCompleted(Exchange exchange, boolean objectReturned) {
		if ( exchange.getProduct().getStatus().equals(ProductStatus.emprunte)
				&& exchange.getStatus().equals(ExchangeStatus.accepted)){
			Product product = exchange.getProduct();
			if ( objectReturned){
				product.setStatus(ProductStatus.disponible);
				this.productRepository.save(product);
			}
			else{
				product.setStatus(ProductStatus.perdu);
				this.productRepository.save(product);

			}
			exchange.setStatus(ExchangeStatus.completed);
			return exchange;
		}
		else{
			throw new BusinessLogicException("This exchange has already been completed");
		}
	}

	public Exchange getById(int id) {
		return this.exchangeRepository.findOne(id);
	}

	public Exchange rejectExchange(Exchange exchange) {
		if ( exchange.getStatus().equals(ExchangeStatus.issued)){
			exchange.setStatus(ExchangeStatus.refused);
			this.exchangeRepository.save(exchange);
			return exchange;
		}
		else{
			throw new BusinessLogicException ( "This exchange has already either been accepted or rejected.");	
		}
	}

	public Collection<Exchange> findByBorrower(Sharer borrower,
			ExchangeStatus status) {
		return this.exchangeRepository.findByBorrowerAndStatus(borrower, status);
	}

	public Collection<Exchange> findByLender(Sharer lender,
			ExchangeStatus status) {
		return this.exchangeRepository.findByLenderAndStatus(lender, status);
	}
	
	public boolean rateExchange ( Exchange exchange, Sharer rater, double note ){
		if ( rater.equals(exchange.getBorrower())){
			exchange.setBorrowerRating(note);
			this.exchangeRepository.save(exchange);
			return true;
		}
		else if ( rater.equals(exchange.getLender())){
			exchange.setLenderRating(note);
			this.exchangeRepository.save(exchange);
			return true;
		}
		else{
			throw new BusinessLogicException ( "You need to be involved in an exchange to be allowed to rate it." );
		}
	}
}
