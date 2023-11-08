package local.example.catalogservice.config;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

@Configuration
public class BaseConfig {


	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter isoDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(isoDateTimeFormatter));
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(isoDateTimeFormatter));
        mapper.registerModule(javaTimeModule);
		return mapper;
	}
}
