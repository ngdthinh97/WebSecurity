package com.spring.security.auth.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms.ParsedBucket;

import java.io.IOException;

public class ParsedBucketSerializer extends JsonSerializer<ParsedBucket> {

    @Override
    public void serialize(ParsedBucket bucket, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        try {
            Number keyAsNumber = bucket.getKeyAsNumber();
            jsonGenerator.writeNumber((Short) keyAsNumber);
        } catch (NumberFormatException e) {
            String keyAsString = bucket.getKeyAsString();
            jsonGenerator.writeString(keyAsString);
        }

    }
}
