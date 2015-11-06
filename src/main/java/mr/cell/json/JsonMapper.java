package mr.cell.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {

	public <T> T jsonToData(String json, Class<T> dataType) throws AppJsonParseException, AppJsonMappingException, IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, dataType);
		} catch (JsonParseException e) {
			throw new AppJsonParseException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new AppJsonMappingException(e.getMessage(), e);
		}
	}
	
	public String dataToJson(Object data) throws AppJsonMappingException, AppJsonGenerationException, IOException {
		return dataToJson(data, false);
	}
	
	public String dataToJson(Object data, boolean prettify) throws AppJsonMappingException, AppJsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		if(prettify) {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		try {
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (JsonMappingException e) {
			throw new AppJsonMappingException(e.getMessage(), e);
		} catch (JsonGenerationException e) {
			throw new AppJsonGenerationException(e.getMessage(), e);
		}
	}
}
