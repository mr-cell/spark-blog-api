package mr.cell.domain;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Comment {
	private UUID uuid;
	private UUID postUuid;
	private String author;
	private String content;
	private boolean approved;
	private Date submissionDate;
}
