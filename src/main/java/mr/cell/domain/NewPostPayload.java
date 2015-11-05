package mr.cell.domain;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class NewPostPayload implements Validable {
	private String title;
	private List<String> categories = new LinkedList<>();
	private String content;
	
	public boolean isValid() {
		return title != null && !title.isEmpty() && !categories.isEmpty();
	}
}
