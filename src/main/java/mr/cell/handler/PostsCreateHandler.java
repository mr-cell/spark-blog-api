package mr.cell.handler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import mr.cell.dao.PostDao;
import mr.cell.payload.NewPostPayload;

public class PostsCreateHandler extends AbstractRequestHandler<NewPostPayload> {

	private PostDao dao;

	public PostsCreateHandler(PostDao dao) {
		super(NewPostPayload.class);
		this.dao = dao;
	}

	@Override
	protected Answer processImpl(NewPostPayload value, Map<String, String> urlParams, boolean shoudlReturnHtml) {
		UUID uuid = dao.createPost(value);
		try {
			return new Answer(HTTP_OK, getJsonMapper().dataToJson(uuid, true));
		} catch (IOException e) {
			return new Answer(HTTP_BAD_REQUEST);
		}
	}

}
