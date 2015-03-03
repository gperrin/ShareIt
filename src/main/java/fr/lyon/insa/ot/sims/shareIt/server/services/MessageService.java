package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.MessageRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Service
public class MessageService{
	
	@Autowired
	SharerService sharerService;
	
	@Autowired
	MessageRepository messageRepository;

	public Message createMessage(int sender, int receiver, String text) {
		Sharer fullSender = sharerService.getUser(sender);
		Sharer fullReceiver = sharerService.getUser(receiver);
		
		Message message = new Message();
		message.setSender(fullSender);
		message.setReceiver(fullReceiver);
		message.setMessage(text);
		message.setDate(new Date());
		
		messageRepository.save(message);
		
		return null;
	}

	public Collection<Message> findMessages(int user, int contact) {
		Sharer sharer = sharerService.getUser(user);
		Collection<Message> messages = new ArrayList<>();
		
		for(Message m: messageRepository.findBySender(sharer)){
			if(m.getReceiver().getId() == contact){
				messages.add(m);
			}
		}
		
		for(Message m: messageRepository.findByReceiver(sharer)){
			if(m.getSender().getId() == contact){
				messages.add(m);
			}
		}
		
		return messages;
	}

	public Collection<Sharer> findContacts(int userId) {
		Sharer sharer = sharerService.getUser(userId);
		Collection<Sharer> contacts = new ArrayList<>();
		
		for(Message m: messageRepository.findBySender(sharer)){
			if(!contacts.contains(m.getReceiver())){
				contacts.add(m.getReceiver());
			}
		}
		
		for(Message m: messageRepository.findByReceiver(sharer)){
			if(!contacts.contains(m.getSender())){
				contacts.add(m.getSender());
			}
		}
		
		return contacts;
	}
}
