package mr.cell.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {

	public <T> T jsonToData(String json, Class<T> dataType) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, dataType);
	}
	
	public String dataToJson(Object data, boolean prettify) throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		if(prettify) {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
    	mapper.writeValue(sw, data);
    	return sw.toString();        	
	}
}
