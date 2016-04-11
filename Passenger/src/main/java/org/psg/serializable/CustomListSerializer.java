package org.psg.serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.psg.model.Passenger;
import org.psg.model.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10.04.16.
 */
public class CustomListSerializer extends JsonSerializer<Status>{

    @Override
    public void serialize(Status item, JsonGenerator generator, SerializerProvider provider)
    throws IOException, JsonProcessingException {

        generator.writeObject(item.getStatus());

    }
}
