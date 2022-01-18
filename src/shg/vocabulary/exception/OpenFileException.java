package shg.vocabulary.exception;

@SuppressWarnings("serial")
public class OpenFileException extends RuntimeException {
	public OpenFileException() {}
	public OpenFileException(String msg) {
		super(msg);
	}
}
