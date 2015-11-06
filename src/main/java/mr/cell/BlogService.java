package mr.cell;

import static spark.Spark.get;
import static spark.Spark.post;

import org.sql2o.Sql2o;

import mr.cell.dao.PostDao;
import mr.cell.dao.Sql2oPostDao;
import mr.cell.handler.PostsCreateCommentHandler;
import mr.cell.handler.PostsCreateHandler;
import mr.cell.handler.PostsGetAllHandler;
import mr.cell.handler.PostsGetPostCommentsHandler;

/**
 * Blog Service
 *
 */
public class BlogService {
	
	private PostDao dao;
	
	public static void main(String[] args) {
    	BlogService service = new BlogService();
    	service.init();
    }
	
	public BlogService() {
		Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/spark_blog", "root", "");
		dao = new Sql2oPostDao(sql2o);
	}
	
	public void init() {
		get("/posts", new PostsGetAllHandler(dao));
        post("/posts", new PostsCreateHandler(dao));
        post("/posts/:uuid/comments", new PostsCreateCommentHandler(dao));
        get("/posts/:uuid/comments", new PostsGetPostCommentsHandler(dao));
	}
}
