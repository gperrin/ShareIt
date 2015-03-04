package fr.lyon.insa.ot.sims.shareIt.server.beans;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAcceptedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeAskedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeEndedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ExchangeRejectedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.MessageCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductDeletedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserRatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserUpdatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.services.ExchangeService;
import fr.lyon.insa.ot.sims.shareIt.server.services.MessageService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductCategoryService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductService;
import fr.lyon.insa.ot.sims.shareIt.server.services.SharerService;
import fr.lyon.insa.ot.sims.shareIt.server.services.UserEventService;

@Component
public class EventProcessor implements IEventProcessor {	
	//attention aux event cascades :)
	
	//TODO : mettre un logger et loguer chaque evenement
	@Autowired
	SharerService sharerService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	ExchangeService exchangeService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserEventService userEventService;
	
	
	@Override
	public void processExchangeAcceptedEvent(
			ExchangeAcceptedEvent exchangeAcceptedEvent) {
		
	}

	@Override
	public void processExchangeAskedEvent(ExchangeAskedEvent exchangeAskedEvent) {
		
	}

	@Override
	public void processExchangeRejectedEvent(
			ExchangeRejectedEvent exchangeRejectedEvent) {
		
	}

	@Override
	public void processMessageCreatedEvent(
			MessageCreatedEvent messageCreatedEvent) {
		
	}

	@Override
	public void processProductCreatedEvent(
			ProductCreatedEvent productCreatedEvent) {
		
	}

	@Override
	public void processProductDeletedEvent(
			ProductDeletedEvent productDeletedEvent) {
		
	}

	@Override
	public void processUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
		
	}

	@Override
	public void processUserRatedEvent(UserRatedEvent userRatedEvent) {
		//calculer note moyenne et mettre à jour les stats utilisateur
		Sharer user = userRatedEvent.getUser();
		UserStats userStats = user.getUserStats();
		Collection<UserRatedEvent> userRatings = (Collection<UserRatedEvent>) this.userEventService.getEvents(user, UserRatedEvent.class);
		userRatings.add(userRatedEvent);
		switch ( (int)Math.floor(userRatedEvent.getRating())){
			case 0 : {
				userStats.setNotes01(userStats.getNotes01()+1);
				break;
			}
			case 1 : {
				userStats.setNotes12(userStats.getNotes12()+1);
				break;
			}
			case 2 : {
				userStats.setNotes23(userStats.getNotes23()+1);
				break;
			}
			case 3 : {
				userStats.setNotes34(userStats.getNotes34()+1);
				break;
			}
			case 4 : {
				userStats.setNotes45(userStats.getNotes45()+1);
				break;
			}
		}
		double newMean = 0;
		for ( UserRatedEvent event : userRatings ){
			newMean += event.getRating();
		}
		newMean /= userRatings.size();
		userStats.setAverageNote(newMean);
		user.setUserStats(userStats);
		this.sharerService.updateUser(user);
	}

	@Override
	public void processUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
		
	}

	@Override
	public void processExchangeEndedEvent(ExchangeEndedEvent exchangeEndedEvent) {
		//mettre à jour les stats utilisateur
		Sharer lender = exchangeEndedEvent.getExchange().getProduct().getSharer();
		Sharer borrower = exchangeEndedEvent.getExchange().getBorrower();
		UserStats lenderStats = lender.getUserStats();
		UserStats borrowerStats = borrower.getUserStats();
		lenderStats.setNbLended(lenderStats.getNbLended()+1);
		borrowerStats.setNbBorrowed(borrowerStats.getNbBorrowed()+1);
		if ( exchangeEndedEvent.isObjectReturned() ) {
			borrowerStats.setNbObjectReturned(borrowerStats.getNbObjectReturned()+1);
		}
		lender.setUserStats(lenderStats);
		this.sharerService.updateUser(lender);
		borrower.setUserStats(borrowerStats);
		this.sharerService.updateUser(borrower);
	}

	public SharerService getSharerService() {
		return sharerService;
	}

	public void setSharerService(SharerService sharerService) {
		this.sharerService = sharerService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	public void setProductCategoryService(
			ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	public ExchangeService getExchangeService() {
		return exchangeService;
	}

	public void setExchangeService(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public UserEventService getUserEventService() {
		return userEventService;
	}

	public void setUserEventService(UserEventService userEventService) {
		this.userEventService = userEventService;
	}

	
	
}
