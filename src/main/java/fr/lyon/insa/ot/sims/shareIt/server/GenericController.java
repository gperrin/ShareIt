package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;
import fr.lyon.insa.ot.sims.shareIt.server.services.ExchangeService;
import fr.lyon.insa.ot.sims.shareIt.server.services.MessageService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductCategoryService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ProductService;
import fr.lyon.insa.ot.sims.shareIt.server.services.SharerService;


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
	
	
	
}
