package mr.cell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;

import java.util.Collections;
import java.util.UUID;

import org.junit.Test;

import org.easymock.EasyMock;

import mr.cell.dao.PostDao;
import mr.cell.domain.NewPostPayload;

public class PostsCreateHandlerTest {
	
	@Test
	public void anInvalidNewPost_ReturnsBadRequest() {
		NewPostPayload newPost = new NewPostPayload();
		newPost.setTitle("");
		newPost.setContent("bla bla bla");
		
		assertFalse(newPost.isValid());
		
		PostDao dao = EasyMock.createMock(PostDao.class);
		replay(dao);
		
		
		PostsCreateHandler handler = new PostsCreateHandler(dao);
		
		assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), false));
		assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), true));
		
		verify(dao);
	}
	
	@Test
    public void aValidPost_IsCorrectlyCreated() {
        NewPostPayload newPost = new NewPostPayload();
        newPost.setTitle("My new post");
        newPost.setContent("Bla bla bla");
        assertTrue(newPost.isValid());

        PostDao dao = EasyMock.createMock(PostDao.class);
        expect(dao.createPost(newPost)).andReturn(UUID.fromString("728084e8-7c9a-4133-a9a7-f2bb491ef436"));
        replay(dao);

        PostsCreateHandler handler = new PostsCreateHandler(dao);
        assertEquals(new Answer(200, "\"728084e8-7c9a-4133-a9a7-f2bb491ef436\""), handler.process(newPost, Collections.emptyMap(), false));

        verify(dao);
    }

}
