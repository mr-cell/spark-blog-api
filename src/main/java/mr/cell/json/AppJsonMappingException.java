package mr.cell.json;

import java.io.IOException;

public class AppJsonMappingException extends IOException {

	private static final long serialVersionUID = 7162776213147842681L;
	
	public AppJsonMappingException() {
		super();
	}
	
	public AppJsonMappingException(String message) {
		super(message);
	}
	
	public AppJsonMappingException(Throwable cause) {
		super(cause);
	}
	
	public AppJsonMappingException(String message, Throwable cause) {
		super(message, cause);
	}

}
