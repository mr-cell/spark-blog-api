package mr.cell.domain;

import lombok.Data;

@Data
public class NewCommentPayload implements Validable {
	
	private String author;
	private String content;

	@Override
	public boolean isValid() {
		return author != null && !author.isEmpty() && content != null && !content.isEmpty();
	}

}
