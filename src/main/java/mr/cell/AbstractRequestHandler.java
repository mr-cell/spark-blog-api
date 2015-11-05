package mr.cell;

import java.util.Map;

import mr.cell.domain.Validable;
import mr.cell.json.JsonMapper;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

	private static final int HTTP_BAD_REQUEST = 400;

	private Class<V> valueClass;
	private JsonMapper mapper;

	public AbstractRequestHandler(Class<V> valueClass) {
		this.valueClass = valueClass;
		mapper = new JsonMapper();
	}

	public JsonMapper getMapper() {
		return mapper;
	}

	private static boolean shouldReturnHtml(Request request) {
		String accept = request.headers("Accept");
		return accept != null && accept.contains("text/html");
	}

	@Override
	public final Answer process(V value, Map<String, String> urlParams, boolean shouldReturnHtml) {
		if (!value.isValid()) {
			return new Answer(HTTP_BAD_REQUEST);
		} else {
			return processImpl(value, urlParams, shouldReturnHtml);
		}
	}

	protected abstract Answer processImpl(V value, Map<String, String> urlParams, boolean shoudlReturnHtml);

	@Override
	public Object handle(Request request, Response response) throws Exception {
		V value = mapper.jsonToData(request.body(), valueClass);
		Answer answer = process(value, request.params(), shouldReturnHtml(request));
		response.status(answer.getHttpStatus());
		if (shouldReturnHtml(request)) {
			response.type("text/html");
		} else {
			response.type("application/json");
		}
		response.body(answer.getBody());
		return answer.getBody();
	}
}
