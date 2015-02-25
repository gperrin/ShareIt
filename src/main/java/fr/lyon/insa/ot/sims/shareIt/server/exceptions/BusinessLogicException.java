package fr.lyon.insa.ot.sims.shareIt.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Business logic exception")
public class BusinessLogicException extends RuntimeException {
	public BusinessLogicException (String message){
		super(message);
	}
}
