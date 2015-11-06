package mr.cell.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import mr.cell.domain.Comment;
import mr.cell.domain.Post;
import mr.cell.payload.NewCommentPayload;
import mr.cell.payload.NewPostPayload;
import mr.cell.uuid.RandomUuidGenerator;
import mr.cell.uuid.UuidGenerator;

public class MemoryPostDao implements PostDao {
	
	private Map<UUID, Post> posts = new HashMap<>();
	private UuidGenerator generator = new RandomUuidGenerator();
	
	public UUID createPost(NewPostPayload postPayload) {
		Post post = new Post();
		UUID uuid = generator.generate();
		post.setUuid(uuid);
		post.setTitle(postPayload.getTitle());
		post.setCategories(postPayload.getCategories());
		post.setContent(postPayload.getContent());
		posts.put(uuid, post);
		return uuid;
	}
	
	public List<Post> getAllPosts() {
		return posts.keySet().stream().sorted().map((id) -> posts.get(id) ).collect(Collectors.toList());
	}

	@Override
	public UUID createComment(UUID post, NewCommentPayload commentPayload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> getAllCommentsOn(UUID post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsPost(UUID post) {
		// TODO Auto-generated method stub
		return false;
	}
}
