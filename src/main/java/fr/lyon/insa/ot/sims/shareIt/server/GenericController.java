package fr.lyon.insa.ot.sims.shareIt.server;

import org.springframework.beans.factory.annotation.Autowired;

import fr.lyon.insa.ot.sims.shareIt.server.services.ExchangeService;
import fr.lyon.insa.ot.sims.shareIt.server.services.MessageService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductCategoryService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductService;
import fr.lyon.insa.ot.sims.shareIt.server.services.SharerService;
import fr.lyon.insa.ot.sims.shareIt.server.services.UserEventService;


public abstract class GenericController {
	
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
	
}
