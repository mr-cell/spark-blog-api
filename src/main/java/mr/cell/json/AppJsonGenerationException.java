package mr.cell.json;

import java.io.IOException;

public class AppJsonGenerationException extends IOException {

	private static final long serialVersionUID = 8222728128736993186L;
	
	public AppJsonGenerationException() {
		super();
	}
	
	public AppJsonGenerationException(String message) {
		super(message);
	}
	
	public AppJsonGenerationException(Throwable cause) {
		super(cause);
	}
	
	public AppJsonGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}
