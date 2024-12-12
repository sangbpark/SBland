package com.sbland.common.objectmapper.serializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class StringTimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String timestamp = parser.getText();
        try {
            return LocalDateTime.parse(timestamp); 
        } catch (Exception e) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(Long.parseLong(timestamp)),
                    ZoneId.of("Asia/Seoul")
            );
        }
    }
}