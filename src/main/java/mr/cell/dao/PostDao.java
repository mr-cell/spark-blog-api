package mr.cell.dao;

import java.util.List;
import java.util.UUID;

import mr.cell.domain.Comment;
import mr.cell.domain.Post;
import mr.cell.payload.NewCommentPayload;
import mr.cell.payload.NewPostPayload;

public interface PostDao {
	
	UUID createPost(NewPostPayload postPayload);
	UUID createComment(UUID post, NewCommentPayload commentPayload);
	List<Post> getAllPosts();
	List<Comment> getAllCommentsOn(UUID post);
	boolean existsPost(UUID post);

}
