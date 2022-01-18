package shg.vocabulary.object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import shg.vocabulary.object.Dictionary.*;

public class EnglishVoca extends Dictionary implements Addable, Searchable, Sortable {
	public String filePath;
	
	private EnglishVoca(EnglishVocaBuilder builder) {
		super(builder.voc, builder.dictionaryName);
		this.filePath = builder.filePath;
	}

	@Override
	public void addWord(Word word) {
		voc.add(word);
	}

	@Override
	public List<Word> searchWord(String str) {
		List<Word> list = new ArrayList<>();
		str = str.trim();
		for (Word word : voc) {
			if (word != null) {
				if (word.eng.contains(str)) {
					list.add(word);
				}
			}
		}
		
		return list;
	}

	@Override
	public List<Word> getSortWords(Comparator<? super Word> comparator) {
		return voc.stream().sorted(comparator).collect(Collectors.toList());
	}
	
	public static class EnglishVocaBuilder {
		private List<Word> voc = new ArrayList<>();
		private String dictionaryName;
		private String filePath;
		
		public EnglishVocaBuilder() {}
		public EnglishVocaBuilder setVoc(List<Word> voc) {
			this.voc = voc;
			return this;
		}
		
		public EnglishVocaBuilder setDictionaryName(String dictionaryName) {
			this.dictionaryName = dictionaryName;
			return this;
		}
		
		public EnglishVocaBuilder setFilePath(String filePath) {
			this.filePath = filePath;
			return this;
		}
		
		public EnglishVoca build() {
			return new EnglishVoca(this);
		}
		
	}
}
