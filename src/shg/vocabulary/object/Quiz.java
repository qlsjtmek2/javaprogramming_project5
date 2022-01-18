package shg.vocabulary.object;

import java.util.Set;

public class Quiz {
	public Set<Word> choiceSet;
	private Word answer;
	
	public Quiz(Word answer, Set<Word> choiceSet) {
		super();
		this.answer = answer;
		this.choiceSet = choiceSet;
	}

	public Word getAnswer() {
		return answer;
	}

	public void setAnswer(Word answer) {
		this.answer = answer;
	}
}
