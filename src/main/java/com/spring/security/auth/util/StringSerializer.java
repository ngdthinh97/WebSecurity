package com.spring.security.auth.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (string != null) {
            String string2 = new String(string);
            jsonGenerator.writeString(string2);
        }
    }
}
