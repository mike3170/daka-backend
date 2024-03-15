package com.stit.punch.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// LocalDate utc -> Asia/Taipei
public class MyLocalDateDeserializer extends JsonDeserializer<LocalDate> {
	private static final Logger log = LoggerFactory.getLogger(MyLocalDateDeserializer.class);

	// yyyy-MM-ddTHH:mm:ssZ
	private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
		.append(DateTimeFormatter.ISO_LOCAL_DATE)
		.appendLiteral('T')
		.append(DateTimeFormatter.ISO_LOCAL_TIME)
		.appendLiteral('Z')
		.toFormatter()
		.withZone(ZoneOffset.UTC);

	// yyyy-MM-dd
	private static final DateTimeFormatter DATE_FORMATTER2 = new DateTimeFormatterBuilder()
		.append(DateTimeFormatter.ISO_LOCAL_DATE)
		.toFormatter()
		.withZone(ZoneOffset.UTC);

	
	@Override
	public LocalDate deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		//log.info("*************** {}", jp.getText());
		try {
			String str = jp.getText().trim();
			if (str.length() == 0) {
				return null;
			}

			//if (str.length() > 10 && str.charAt(10) == 'T') {
			//	if (str.endsWith("Z")) {
			//		return LocalDateTime.ofInstant(Instant.parse(str), ZoneOffset.UTC).toLocalDate();
			//	} else {
			//		return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			//	}
			//}

			// yyyy-MM-dd
			try {
				LocalDate ldate = LocalDate.parse(str, DATE_FORMATTER2);
				return ldate;
			} catch (Exception e) {
				// ignore
			}

			// yyyy-MM-dd'T'HH:mm:ss'Z'
			LocalDateTime utcDateTime = LocalDateTime.parse(str, DATE_FORMATTER);
			LocalDate twDate
				= utcDateTime
					.atZone(ZoneId.of("UTC"))
					.withZoneSameInstant(ZoneId.of("Asia/Taipei"))
					.toLocalDate();

			//log.info("twDate ****************** {}", twDate);
			return twDate;

			//return LocalDate.parse(str, DATE_FORMATTER);  // origin
		} catch (DateTimeParseException | IOException e) {
			throw e;
		}
	}

}
