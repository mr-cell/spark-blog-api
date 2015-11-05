package mr.cell;

import lombok.Data;

@Data
public class Answer {
	
	int httpStatus;
	String body;
	
	public Answer(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public Answer(int httpStatus, String body) {
		this(httpStatus);
		this.body = body;
	}
}
