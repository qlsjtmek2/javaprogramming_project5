package shg.vocabulary.object.tool;

import shg.vocabulary.event.OpenedChangeListener;
import shg.vocabulary.object.Dictionary;

public class OpenedDictionary {
	private static Dictionary dictionary = null;
    
	private OpenedDictionary() {}

	public static Dictionary getInstance() {
		return dictionary;
	}

	public static void setInstance(Dictionary dic) {
		dictionary = dic;
		if (dic != null)
			OpenedChangeListener.change();
	}
}
