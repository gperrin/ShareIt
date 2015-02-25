package fr.lyon.insa.ot.sims.shareIt.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such resource")
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException (String ressourceName, int requestedId){
		super("No "+ressourceName+" found with id "+requestedId);

		
	}
	
	
}
