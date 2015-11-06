package mr.cell.handler;

import java.io.IOException;
import java.util.Map;

import mr.cell.dao.PostDao;
import mr.cell.payload.EmptyPayload;

public class PostsGetAllHandler extends AbstractRequestHandler<EmptyPayload> {

	private final PostDao dao;
	
	public PostsGetAllHandler(PostDao dao) {
		super(EmptyPayload.class);
		this.dao = dao;
	}

	@Override
	protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, boolean shoudlReturnHtml) {
		try {
			return new Answer(HTTP_OK, getJsonMapper().dataToJson(dao.getAllPosts(), true));
		} catch (IOException e) {
			return new Answer(HTTP_BAD_REQUEST);
		}
	}

}
