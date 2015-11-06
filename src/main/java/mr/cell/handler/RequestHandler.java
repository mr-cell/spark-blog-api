package mr.cell.handler;

import java.util.Map;

import mr.cell.payload.Validable;

public interface RequestHandler<V extends Validable> {
	
	Answer process(V value, Map<String, String> urlParams, boolean shouldReturnHtml);

}
