package mr.cell.handler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import mr.cell.dao.PostDao;
import mr.cell.payload.EmptyPayload;

public class PostsGetPostCommentsHandler extends AbstractRequestHandler<EmptyPayload> {

	private final PostDao dao;
	
	public PostsGetPostCommentsHandler(PostDao dao) {
		super(EmptyPayload.class);
		this.dao = dao;
	}

	@Override
	protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, boolean shoudlReturnHtml) {
		UUID post = UUID.fromString(urlParams.get(":uuid"));
		if(!dao.existsPost(post)) {
			return new Answer(HTTP_BAD_REQUEST);
		}
		
		try {
			return new Answer(HTTP_OK, getJsonMapper().dataToJson(dao.getAllCommentsOn(post), true));
		} catch (IOException e) {
			return new Answer(HTTP_BAD_REQUEST);
		}
	}

}
