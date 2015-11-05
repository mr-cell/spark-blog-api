package mr.cell.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import mr.cell.domain.Comment;
import mr.cell.domain.NewCommentPayload;
import mr.cell.domain.NewPostPayload;
import mr.cell.domain.Post;
import mr.cell.uuid.RandomUuidGenerator;
import mr.cell.uuid.UuidGenerator;

public class Sql2oPostDao implements PostDao {
	
	private Sql2o sql2o;
	private UuidGenerator generator;
	
	public Sql2oPostDao(Sql2o sql2o) {
		this.sql2o = sql2o;
		generator = new RandomUuidGenerator();
	}
	
	@Override
	public UUID createPost(NewPostPayload postPayload) {
		try(Connection conn = sql2o.beginTransaction()) {
			UUID postUuid = generator.generate();
			conn.createQuery("INSERT INTO posts(uuid, title, content, publishing_date) VALUES (:uuid, :title, :content, :publishingDate)")
				.addParameter("uuid", postUuid.toString())
				.addParameter("title", postPayload.getTitle())
				.addParameter("content", postPayload.getContent())
				.addParameter("publishingDate", new Date())
				.executeUpdate();
			postPayload.getCategories().forEach((category) -> {
				conn.createQuery("INSERT INTO posts_categories(post_uuid, category) VALUES (:postUuid, :category)")
					.addParameter("postUuid", postUuid.toString())
					.addParameter("category", category)
					.executeUpdate();
			});
			conn.commit();
			return postUuid;
		}
	}

	@Override
	public UUID createComment(UUID post, NewCommentPayload commentPayload) {
		try(Connection conn = sql2o.open()) {
			UUID commentUuid = generator.generate();
			conn.createQuery("INSERT INTO comments(uuid, post_uuid, author, content, approved, submission_date) "
					+ "VALUES (:uuid, :postUuid, :author, :content, :approved, :submissionDate)")
				.addParameter("uuid", commentUuid.toString())
				.addParameter("postUuid", post.toString())
				.addParameter("author", commentPayload.getAuthor())
				.addParameter("content", commentPayload.getContent())
				.addParameter("approved", false)
				.addParameter("submissionDate", new Date())
				.executeUpdate();
			return commentUuid;
		}
	}

	@Override
	public List<Post> getAllPosts() {
		try (Connection conn = sql2o.open()) {
			List<Post> posts = conn.createQuery("SELECT * FROM posts")
					.addColumnMapping("publishing_date", "publishingDate")
					.executeAndFetch(Post.class);
			posts.forEach((post) -> post.setCategories(getCategoriesFor(conn, post.getUuid())));
			return posts;
		}
	}

	private List<String> getCategoriesFor(Connection conn, UUID uuid) {
		return conn.createQuery("SELECT category FROM posts_categories WHERE post_uuid = :post_uuid")
				.addParameter("post_uuid", uuid.toString())
				.executeAndFetch(String.class);
	}

	@Override
	public List<Comment> getAllCommentsOn(UUID post) {
		try(Connection conn = sql2o.open()) {
			return conn.createQuery("SELECT * FROM comments WHERE post_uuid=:postUuid")
					.addParameter("postUuid", post.toString())
					.addColumnMapping("submission_date", "submissionDate")
					.addColumnMapping("post_uuid", "postUuid")
					.executeAndFetch(Comment.class);
		}
	}

	@Override
	public boolean existsPost(UUID post) {
		try(Connection conn = sql2o.open()) {
			Integer count = conn.createQuery("SELECT COUNT(uuid) FROM posts WHERE uuid=:uuid")
				.addParameter("uuid", post.toString())
				.executeScalar(Integer.class);
			return (count > 0);
		}
	}

}
