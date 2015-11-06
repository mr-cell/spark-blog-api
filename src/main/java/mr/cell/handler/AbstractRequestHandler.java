package mr.cell.handler;

import java.util.Map;

import mr.cell.json.JsonMapper;
import mr.cell.payload.Validable;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

	protected static final int HTTP_BAD_REQUEST = 400;
	protected static final int HTTP_OK = 200;
	
	protected static final String TYPE_JSON = "application/json";
	protected static final String TYPE_HTML = "text/html";
	
	private static final String HEADER_ACCEPT = "Accept";

	private Class<V> valueClass;
	private JsonMapper mapper;

	public AbstractRequestHandler(Class<V> valueClass) {
		this.valueClass = valueClass;
		mapper = new JsonMapper();
	}

	public JsonMapper getJsonMapper() {
		return mapper;
	}

	private static boolean shouldReturnHtml(Request request) {
		String accept = request.headers(HEADER_ACCEPT);
		return accept != null && accept.contains(TYPE_HTML);
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
			response.type(TYPE_HTML);
		} else {
			response.type(TYPE_JSON);
		}
		response.body(answer.getBody());
		return answer.getBody();
	}
}
