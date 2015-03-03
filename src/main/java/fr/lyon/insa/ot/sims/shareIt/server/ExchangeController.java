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
		return exchange;	
	}
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/reject")
	@ResponseBody Exchange rejectExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException("Exchange", exchangeId);
		exchange = this.exchangeService.rejectExchange(exchange);
		return exchange;
	}
	@RequestMapping(method=RequestMethod.PUT, value="/exchange/{id:[\\d]+}/complete")
	@ResponseBody Exchange completeExchange(@PathVariable("id") int exchangeId,
			@RequestParam("returned") boolean objectReturned){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException ( "Exchange", exchangeId);
		exchange = this.exchangeService.setCompleted(exchange, objectReturned);
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
}
