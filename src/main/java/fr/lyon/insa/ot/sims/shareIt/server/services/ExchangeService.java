package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;

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
public class ExchangeService implements IExchangeService {

	@Autowired
	ExchangeRepository exchangeRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Exchange createExhange(Sharer borrower, Product product) {
		if ( product.getStatus().equals(ProductStatus.disponible) &&
				borrower != product.getSharer()){
			Exchange exchange = new Exchange();
			exchange.setBorrower(borrower);
			exchange.setLender(product.getSharer());
			exchange.setProduct(product);
			exchange.setStatus(ExchangeStatus.issued);
			this.exchangeRepository.save(exchange);
			return exchange;
		}
		else{
			throw new BusinessLogicException("Required product is currently unavailable");
		}
	}

	@Override
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

	@Override
	public Collection<Exchange> findByBorrower(Sharer borrower) {
		Collection<Exchange> exchanges = new ArrayList<>();
		exchanges.addAll(this.exchangeRepository.findByBorrower(borrower));
		return exchanges;
	}

	@Override
	public Collection<Exchange> findByLender(Sharer lender) {
		Collection<Exchange> exchanges = new ArrayList<>();
		exchanges.addAll(this.exchangeRepository.findByLender(lender));
		return exchanges;
	}

	@Override
	public Exchange setCompleted(Exchange exchange, boolean objectReturned) {
		if ( exchange.getProduct().getStatus().equals(ProductStatus.emprunte)
				&& exchange.getStatus().equals(ExchangeStatus.accepted)){
			Product product = exchange.getProduct();
			if ( objectReturned){
				product.setStatus(ProductStatus.disponible);
				this.productRepository.save(product);
			}
			else{
				//TODO : modifier negativement le rating de l'emprunteur
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

	@Override
	public Exchange getById(int id) {
		return this.exchangeRepository.findOne(id);
	}

	@Override
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

	@Override
	public Collection<Exchange> findByBorrower(Sharer borrower,
			ExchangeStatus status) {
		return this.exchangeRepository.findByBorrowerAndStatus(borrower, status);
	}

	@Override
	public Collection<Exchange> findByLender(Sharer lender,
			ExchangeStatus status) {
		return this.exchangeRepository.findByLenderAndStatus(lender, status);
	}
}
