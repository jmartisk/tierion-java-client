package cz.srayayay.tierion.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String json, Class<T> expectedClass) {
		try {
			return mapper.reader(expectedClass).readValue(json);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
