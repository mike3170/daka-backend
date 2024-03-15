package com.stit.punch.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

// LocalDateTime utc -> Asia/Taipei
public class MyLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	// yyyy-MM-ddTHH:mm:ssZ
	private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
		.append(DateTimeFormatter.ISO_LOCAL_DATE)
		.appendLiteral('T')
		.append(DateTimeFormatter.ISO_LOCAL_TIME)
		.appendLiteral('Z')
		.toFormatter()
		.withZone(ZoneOffset.UTC);

	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		try {
			String str = jp.getText();

			// yyyy-MM-dd'T'HH:mm:ss'Z'
			LocalDateTime ldtUTC = LocalDateTime.parse(str, DATE_FORMATTER);
			LocalDateTime ldtTW
				= ldtUTC
					.atZone(ZoneId.of("UTC"))
					.withZoneSameInstant(ZoneId.of("Asia/Taipei"))
					.toLocalDateTime();

			return ldtTW;
		} catch (DateTimeParseException | IOException e) {
			return null;
		}
	}

}
