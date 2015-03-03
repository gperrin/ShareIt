package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Controller
public class MessageController extends GenericController{
	@RequestMapping(method = RequestMethod.POST, value="/message/")
	@ResponseStatus (HttpStatus.CREATED) public @ResponseBody Message createMessage(
			@RequestParam(required = true, value = "sender") int sender,
			@RequestParam(required = true, value = "receiver") int receiver,
			@RequestParam(required = true, value = "message") String message
			){
		return this.messageService.createMessage(sender, receiver, message);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/contacts/{id}")
	public @ResponseBody Collection<Sharer> getContacts(@PathVariable("id") int userId){
		return this.messageService.findContacts(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/messages/{userId}/{contactId}")
	public @ResponseBody Collection<Message> getMessages(@PathVariable("userId") int userId, @PathVariable("contactId") int contactId){
		return this.messageService.findMessages(userId, contactId);
	}

}
