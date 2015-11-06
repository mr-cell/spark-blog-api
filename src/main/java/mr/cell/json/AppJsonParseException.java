package mr.cell.json;

import java.io.IOException;

public class AppJsonParseException extends IOException {

	private static final long serialVersionUID = 7780633949828367787L;
	
	public AppJsonParseException() {
		super();
	}
	
	public AppJsonParseException(String message) {
		super(message);
	}
	
	public AppJsonParseException(Throwable cause) {
		super(cause);
	}
	
	public AppJsonParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
