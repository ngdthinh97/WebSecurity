package com.spring.security.auth.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NumberSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (number != null) {
            jsonGenerator.writeString(String.valueOf(number.toString()));
        }
    }
}
