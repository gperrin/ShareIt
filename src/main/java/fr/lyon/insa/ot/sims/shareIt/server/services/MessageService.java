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
public class MessageService implements IMessageService {
	
	@Autowired
	SharerService sharerService;
	
	@Autowired
	MessageRepository messageRepository;

	@Override
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

	@Override
	public Collection<Message> findMessages(int user) {
		Sharer sharer = sharerService.getUser(user);
		Collection<Message> messages = new ArrayList<>();
		
		messages.addAll(messageRepository.findBySender(sharer));
		messages.addAll(messageRepository.findByReceiver(sharer));
		
		return messages;
	}

}
