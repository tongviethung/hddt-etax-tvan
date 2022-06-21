package vn.teca.hddt.etax.tvan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParser {

    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static <T> T jsonToObject(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonStr, clazz);
    }

    public static String objectToString(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}
