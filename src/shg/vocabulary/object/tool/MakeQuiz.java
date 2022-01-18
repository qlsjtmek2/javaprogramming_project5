package shg.vocabulary.object.tool;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import shg.vocabulary.exception.MakeQuizException;
import shg.vocabulary.object.Dictionary;
import shg.vocabulary.object.Quiz;
import shg.vocabulary.object.Word;

public class MakeQuiz {
	private Dictionary dictionary;

	public MakeQuiz(Dictionary dictionary) {
		super();
		this.dictionary = dictionary;
	}
	
	public Quiz make(int chioceNum) {
		List<Word> voc = dictionary.getVoc();
		Set<Word> choiceSet = new HashSet<>();
		Random random = new Random();
		
		if (voc.size() < chioceNum)
			throw new MakeQuizException("fail make quiz (lack word)");
		
		try {
			Loop: while (choiceSet.size() < chioceNum) {
				Word randomWord = voc.get(random.nextInt(voc.size()));
				for (Word word : choiceSet) {
					if (word.kor.equals(randomWord.kor))
						continue Loop;
				}
				choiceSet.add(randomWord);
			}
		} catch (IllegalArgumentException e) {
			throw new MakeQuizException("fail make quiz (lack word)");
		}
		
		return new Quiz(choiceSet.stream().skip(random.nextInt(choiceSet.size())).findFirst().orElse(null), choiceSet);
		/*
		 * Set 자료구조에서 랜덤 값 추출
		 * 출처 : https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
		 */
	}
}
