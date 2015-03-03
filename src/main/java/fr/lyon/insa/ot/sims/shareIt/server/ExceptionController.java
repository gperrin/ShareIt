package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController implements ErrorController{
	
	
	@ExceptionHandler ( ResourceNotFoundException.class )
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Map<String, String> handleResourceNotFound(ResourceNotFoundException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		//exception.printStackTrace();
		return response;
	}
	@ExceptionHandler ( BusinessLogicException.class )
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Map<String, String> handleBusinessLogicException(BusinessLogicException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		//exception.printStackTrace();
		return response;
	}
	@ExceptionHandler ( RuntimeException.class )
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Map<String, String> handleRuntimeException(RuntimeException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		//exception.printStackTrace();
		return response;
	}
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public @ResponseBody HashMap<String, Object> handleNotFound(){
		HashMap<String, Object> result = new HashMap<>();
		result.put("gros fail", true);
		return result;
	}
	@RequestMapping("/error")
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public @ResponseBody HashMap<String, Object> handleBadRequest(){
		HashMap<String, Object> result = new HashMap<>();
		result.put("gros fail", true);
		return result;
	}
}
