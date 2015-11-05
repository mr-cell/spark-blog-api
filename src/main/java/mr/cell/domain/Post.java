package mr.cell.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Post {
	
	private UUID uuid;
	private String title;
	private List<String> categories;
	private String content;
	private Date publishingDate;

}
