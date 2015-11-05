package mr.cell;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.util.UUID;

import org.sql2o.Sql2o;

import spark.Request;
import spark.Response;
import mr.cell.dao.PostDao;
import mr.cell.dao.Sql2oPostDao;
import mr.cell.domain.NewCommentPayload;
import mr.cell.domain.NewPostPayload;
import mr.cell.json.JsonMapper;

/**
 * Hello world!
 *
 */
public class BlogService {
	
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;
	
	private static final String TYPE_JSON = "application/json";
	
	private JsonMapper mapper;
	private PostDao dao;
	
	public static void main(String[] args) {
    	BlogService service = new BlogService();
    	service.init();
    }
	
	public BlogService() {
		Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/spark_blog", "root", "");
		mapper = new JsonMapper();
		dao = new Sql2oPostDao(sql2o);
	}
	
	public void init() {
		get("/posts", (req, res) -> getPosts(req, res));
        post("/posts", new PostsCreateHandler(dao));
        post("/posts/:uuid/comments", (req, res) -> createComment(req, res));
        get("/posts/:uuid/comments", (req, res) -> getComments(req, res));
	}
	
	private Object getComments(Request req, Response res) {
		UUID post = UUID.fromString(req.params(":uuid"));
		if(!dao.existsPost(post)) {
			res.status(HTTP_BAD_REQUEST);
			return "";
		}
		
		res.status(HTTP_OK);
		res.type(TYPE_JSON);
		try {
			return mapper.dataToJson(dao.getAllCommentsOn(post), true);
		} catch(IOException e) {
			res.status(HTTP_BAD_REQUEST);
			return "";
		}		
	}

	private Object createComment(Request req, Response res) {
		try {
			System.out.println("ok");
			NewCommentPayload creation = mapper.jsonToData(req.body(), NewCommentPayload.class);
			System.out.println(creation);
			if(!creation.isValid()) {
				res.status(HTTP_BAD_REQUEST);
				return "";
			}
			UUID post = UUID.fromString(req.params(":uuid"));
			System.out.println(post.toString());
			if(!dao.existsPost(post)) {
				System.out.println("error");
				res.status(HTTP_BAD_REQUEST);
				return "";
			}
			
			UUID uuid = dao.createComment(post, creation);
			res.status(HTTP_OK);
			res.type(TYPE_JSON);
			return mapper.dataToJson(uuid, true);
		} catch(IOException e) {
			System.out.println(e.getMessage());
			res.status(HTTP_BAD_REQUEST);
			return "";
		}
	}

	private Object getPosts(Request req, Response res) {
		res.status(HTTP_OK);
    	res.type(TYPE_JSON);
    	try {
    		return mapper.dataToJson(dao.getAllPosts(), true);
    	} catch (IOException e) {
    		res.status(HTTP_BAD_REQUEST);
    		return "";
    	}
	}
	
	private Object createNewPost(Request req, Response res) {
		try {
    		NewPostPayload creation = mapper.jsonToData(req.body(), NewPostPayload.class);
    		if(!creation.isValid()) {
    			res.status(HTTP_BAD_REQUEST);
    			return "";
    		}
    		
    		UUID uuid = dao.createPost(creation);
    		res.status(HTTP_OK);
    		res.type(TYPE_JSON);
    		return mapper.dataToJson(uuid, true);        		
    	} catch(IOException e) {
			res.status(HTTP_BAD_REQUEST);
			return "";
		}
	}
}
