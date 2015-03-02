package fr.lyon.insa.ot.sims.shareIt.server.beans;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateToStringSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public void serialize(Date date, JsonGenerator jGenerator, SerializerProvider sProvider)
			throws IOException, JsonProcessingException {

		jGenerator.writeObject(sdf.format(date));
	}

}
