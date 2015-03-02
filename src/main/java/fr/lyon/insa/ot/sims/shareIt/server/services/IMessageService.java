package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;

public interface IMessageService {
	
	public Message createMessage(int sender, int receiver, String text);
	public Collection<Message> findMessages(int user);

}
