package fr.lyon.insa.ot.sims.shareIt.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ExchangeStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAcceptedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAskedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeEndedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeRejectedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserRatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;

@Controller
public class ExchangeController extends GenericController{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(method = RequestMethod.POST, value = "/product/{id:[\\d]+}/borrow")
	public @ResponseBody Exchange askProduct(@PathVariable("id") int productId,
			@RequestParam("borrower") int borrowerId,
			@RequestParam("startdate") String startDate,
			@RequestParam("enddate") String endDate){
		Sharer borrower = this.sharerService.getUser(borrowerId);
		Product product = this.productService.getProduct(productId);
		if ( borrower == null ) throw new ResourceNotFoundException("user", borrowerId);
		if ( product == null ) throw new ResourceNotFoundException("product", productId);
		Date sDate = null;
		Date eDate = null;
		try {
			sDate = this.dateFormat.parse(startDate);
			eDate = this.dateFormat.parse(endDate);
		} catch (ParseException e) {
			throw new BusinessLogicException(startDate+" or "+endDate+" is not a correct date format.");	
		}
		Exchange exchange = this.exchangeService.createExhange(borrower, product, sDate, eDate);
		ExchangeAskedEvent exchangeAskedEvent = new ExchangeAskedEvent ( );
		exchangeAskedEvent.setUser(borrower);
		exchangeAskedEvent.setExchange(exchange);
		exchangeAskedEvent.setDate(new Date());
		this.userEventService.persistEvent(exchangeAskedEvent);
		return exchange;				
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/borrowed")
	@ResponseBody Collection<Exchange> getBorrowedProducts(@PathVariable("id") int userId,
			@RequestParam( required = false, value = "status") Integer exchangeStatus){
		Sharer borrower = this.sharerService.getUser(userId);
		if ( borrower == null ) throw new ResourceNotFoundException("User", userId);
		Collection<Exchange> exchanges;
		if ( exchangeStatus != null ){
			ExchangeStatus status;
			try{
				status = ExchangeStatus.values()[exchangeStatus];
				exchanges = this.exchangeService.findByBorrower(borrower, status);
			}catch ( IndexOutOfBoundsException e ){
				throw new ResourceNotFoundException("Exchange Status", exchangeStatus);
			}
		}
		else{
			exchanges = this.exchangeService.findByBorrower(borrower);
		}
		if (exchanges != null ){
			return exchanges;
		}
		return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/lended")
	@ResponseBody Collection<Exchange> getLendedProducts(@PathVariable("id") int userId,
			@RequestParam (required = false, value = "status")Integer exchangeStatus){
		Sharer lender = this.sharerService.getUser(userId);
		if ( lender == null ) throw new ResourceNotFoundException("User", userId);
		Collection<Exchange> exchanges ;
		if ( exchangeStatus != null ){
			ExchangeStatus status;
			try{
				status = ExchangeStatus.values()[exchangeStatus];
				exchanges = this.exchangeService.findByLender(lender, status);
			}catch ( IndexOutOfBoundsException e ){
				throw new ResourceNotFoundException("Exchange Status", exchangeStatus);
			}
		}
		else{
			exchanges = this.exchangeService.findByLender(lender);

		}
		if (exchanges != null ){
			return exchanges;
		}
		return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/accept")
	@ResponseBody Exchange acceptExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException ( "Exchange", exchangeId );
		exchange = this.exchangeService.acceptExchange(exchange);
		ExchangeAcceptedEvent exchangeAcceptedEvent = new ExchangeAcceptedEvent ( );
		exchangeAcceptedEvent.setDate(new Date());
		exchangeAcceptedEvent.setExchange(exchange);
		exchangeAcceptedEvent.setUser(exchange.getLender());
		this.userEventService.persistEvent(exchangeAcceptedEvent);
		return exchange;	
	}
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/reject")
	@ResponseBody Exchange rejectExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException("Exchange", exchangeId);
		exchange = this.exchangeService.rejectExchange(exchange);
		ExchangeRejectedEvent exchangeRejectedEvent = new ExchangeRejectedEvent ();
		exchangeRejectedEvent.setDate(new Date());
		exchangeRejectedEvent.setExchange(exchange);
		exchangeRejectedEvent.setUser(exchange.getLender());
		this.userEventService.persistEvent(exchangeRejectedEvent);
		return exchange;
	}
	@RequestMapping(method=RequestMethod.PUT, value="/exchange/{id:[\\d]+}/complete")
	@ResponseBody Exchange completeExchange(@PathVariable("id") int exchangeId,
			@RequestParam("returned") boolean objectReturned){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException ( "Exchange", exchangeId);
		exchange = this.exchangeService.setCompleted(exchange, objectReturned);
		ExchangeEndedEvent exchangeCompletedEvent = new ExchangeEndedEvent();
		exchangeCompletedEvent.setDate(new Date());
		exchangeCompletedEvent.setExchange(exchange);
		exchangeCompletedEvent.setObjectReturned(objectReturned);
		exchangeCompletedEvent.setUser(exchange.getLender());
		this.userEventService.persistEvent(exchangeCompletedEvent);
		return exchange;
	}
	@RequestMapping(method= RequestMethod.GET, value="/exchange/status")
	public @ResponseBody Collection<Map<Integer, ExchangeStatus>> getExchangeStatuses (){
		List<Map<Integer, ExchangeStatus>> result = new ArrayList<>();
		ExchangeStatus[] statusValues = ExchangeStatus.values();
		for ( int i = 0 ; i < statusValues.length ; i++ ){
			Map<Integer, ExchangeStatus>  val = new HashMap<>();
			val.put(i, statusValues[i]);
			result.add(val);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/exchange/{id:[\\d]+}/rate")
	public @ResponseBody void rateExchange(@PathVariable("id") int exchangeId, 
			@RequestParam("user") int userId, @RequestParam("note") double note){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException("Exchange", exchangeId);
		Sharer rater = this.sharerService.getUser(userId);
		if ( rater == null ) throw new ResourceNotFoundException("User", userId);
		this.exchangeService.rateExchange(exchange, rater, note);
		UserRatedEvent userRatedEvent = new UserRatedEvent();
		userRatedEvent.setDate(new Date());
		userRatedEvent.setRater(rater);
		userRatedEvent.setRating(note);
		Sharer user = null;
		if ( exchange.getLender().equals(rater)){
			user = exchange.getBorrower();
		}
		else if ( exchange.getBorrower().equals(rater)){
			user = exchange.getLender();
		}
		userRatedEvent.setUser(user);
		this.userEventService.persistEvent(userRatedEvent);
	}
	
	@RequestMapping ( method = RequestMethod.GET, value = "/exchange/{id:[\\d]+}")
	public @ResponseBody Exchange getExchangeById ( @PathVariable("id") int id ){
		Exchange exchange = this.exchangeService.getById(id);
		if( exchange == null )throw new ResourceNotFoundException("Exchange", id);
		return exchange;
	}
	
	@RequestMapping ( method = RequestMethod.PUT, value = "/exchange/{id:[\\d]+}/confirm")
	public @ResponseBody Exchange confirmExchange ( @PathVariable ( "id" )int id ){
		Exchange exchange = this.exchangeService.getById(id);
		if ( exchange == null ) throw new ResourceNotFoundException("Exchange", id);
		exchange = this.exchangeService.confirmExchange(exchange);
		return exchange;
	}
	
	@RequestMapping ( method = RequestMethod.GET, value = "/exchange/{id:[\\d]+}/awaiting")
	public @ResponseBody int getAwaitingExchangesNumber ( @PathVariable("id") int id ){
		return exchangeService.getNumberOfAwaitingExchanges(id);
	}
}
