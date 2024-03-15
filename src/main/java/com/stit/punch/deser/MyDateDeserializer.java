package com.stit.punch.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

// java.util.Date
public class MyDateDeserializer extends JsonDeserializer<Date> {
	private static final SimpleDateFormat ISO_8601 
		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		//= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

	// yyyy-MM-dd
	private static final SimpleDateFormat YYYY_MM_DD 
		= new SimpleDateFormat("yyyy-MM-dd");

	public MyDateDeserializer() {
		// using Zulu time 
		ISO_8601.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		try {
			String str = parser.getText().trim();
			if (str.length() == 0 ) {
				return null;
			}

			try {
				Date date = ISO_8601.parse(str);
				return date;
			} catch (ParseException e) {
				// ignore
			}

			Date date = YYYY_MM_DD.parse(str);
			return date;

		} catch (ParseException | IOException e) {
			return null;
		}
	}

}
