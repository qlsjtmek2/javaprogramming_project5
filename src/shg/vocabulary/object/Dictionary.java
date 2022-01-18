package shg.vocabulary.object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import shg.vocabulary.object.tool.OpenedDictionary;

public abstract class Dictionary {
	protected List<Word> voc = new ArrayList<>();
	protected String dictionaryName;

	public Dictionary(List<Word> voc, String dictionaryName) {
		super();
		this.voc = voc;
		this.dictionaryName = dictionaryName;
	}

	public List<Word> getVoc() {
		return voc;
	}
	
	public void update() {
		OpenedDictionary.setInstance(this);
	}

	static interface Addable {
		public void addWord(Word word);
	}
	
	static interface Searchable {
		public List<Word> searchWord(String str);
	}
	
	static interface Sortable {
		public List<Word> getSortWords(Comparator<? super Word> comparator);
	}
}
