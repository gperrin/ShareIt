package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;

import java.util.Date;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public class MessageBuilder {
	private Message message = new Message ();
	
	public MessageBuilder id ( long id ){
		message.setId(id);
		return this;
	}
	public MessageBuilder sender ( Sharer sender ){
		message.setSender(sender);
		return this;
	}
	public MessageBuilder receiver ( Sharer receiver ){
		message.setReceiver(receiver);
		return this;
	}
	public MessageBuilder message ( String message ){
		this.message.setMessage(message);
		return this;
	}
	public MessageBuilder date ( Date date ){
		this.message.setDate(date);
		return this;
	}
	public Message build ( ){
		return message;
	}
}
