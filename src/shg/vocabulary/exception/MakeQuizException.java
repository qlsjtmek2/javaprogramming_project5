package shg.vocabulary.exception;

@SuppressWarnings("serial")
public class MakeQuizException extends RuntimeException {
	public MakeQuizException() {}
	public MakeQuizException(String msg) {
		super(msg);
	}
}
