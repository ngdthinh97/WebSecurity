package com.spring.security.auth.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

public class BytesRefSerializer extends JsonSerializer<BytesRef> {

    @Override
    public void serialize(BytesRef bytesRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bytesRef != null) {
            jsonGenerator.writeString(bytesRef.utf8ToString());
        }
    }
}
