package shg.vocabulary.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizTest implements Runnable {
	public List<Quiz> quizList = new ArrayList<>();
	public HashMap<Quiz, String> choiceAnswerMap = new HashMap<>();
	public Thread timer;
	private int score = 0;
	private int second = 0;
	
	public QuizTest(List<Quiz> quizList) {
		super();
		this.quizList = quizList;
	}
	
	public void addChoiceAnswer(Quiz quiz, String choiceAnswer) {
		choiceAnswerMap.put(quiz, choiceAnswer);
	}
	
	public void grade() {
		for (Quiz quiz : quizList) {
			if (quiz.getAnswer().kor.equals(choiceAnswerMap.get(quiz))) {
				score += 1;
			} else {
				quiz.getAnswer().addWrongCount();
			}
		}
	}
	
	public int getScore() {
		return score;
	}

	public int getSecond() {
		return second;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(1000);
				second++;
			}
		} catch (InterruptedException e) {
			return;
		}
	}
}
