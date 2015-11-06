package mr.cell.handler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import mr.cell.dao.PostDao;
import mr.cell.payload.NewCommentPayload;

public class PostsCreateCommentHandler extends AbstractRequestHandler<NewCommentPayload> {

	private final PostDao dao;
	
	public PostsCreateCommentHandler(PostDao dao) {
		super(NewCommentPayload.class);
		this.dao = dao;
	}

	@Override
	protected Answer processImpl(NewCommentPayload value, Map<String, String> urlParams, boolean shoudlReturnHtml) {
		UUID post = UUID.fromString(urlParams.get(":uuid"));
		if(!dao.existsPost(post)) {
			return new Answer(HTTP_BAD_REQUEST);
		}		
		try {
			return new Answer(HTTP_OK, getJsonMapper().dataToJson(dao.createComment(post, value), true));
		} catch(IOException e) {
			return new Answer(HTTP_BAD_REQUEST);
		}
	}

}
