package shg.vocabulary.object;

public class Word {
	public String eng;
	public String kor;
	private int wrongCount = 0;
	
	public Word(String eng, String kor) {
		this(eng, kor, 0);
	}
	
	public Word(String eng, String kor, int wrongCount) {
		super();
		this.eng = eng;
		this.kor = kor;
		this.wrongCount = wrongCount;
	}
	
	public int getWrongCount() {
		return wrongCount;
	}
	
	public void addWrongCount() {
		this.wrongCount++;
	}
	
	@Override
	public String toString() {
		return eng+"\t"+kor+"\t"+wrongCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Word) {
			Word word = (Word) obj;
			return word.eng.equals(this.eng) && word.kor.equals(this.kor);
		} else
			return false;
	}
}
