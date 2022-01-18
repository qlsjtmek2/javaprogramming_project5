package shg.vocabulary.object.tool;

public abstract class Converter<T, V> {
	protected T input;

	public Converter(T input) {
		super();
		this.input = input;
	}
	
	public abstract V convert();
}
