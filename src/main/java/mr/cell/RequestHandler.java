package mr.cell;

import java.util.Map;

import mr.cell.domain.Validable;

public interface RequestHandler<V extends Validable> {
	
	Answer process(V value, Map<String, String> urlParams, boolean shouldReturnHtml);

}
