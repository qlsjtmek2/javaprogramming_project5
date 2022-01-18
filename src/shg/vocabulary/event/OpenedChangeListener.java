package shg.vocabulary.event;

import java.util.ArrayList;
import java.util.List;

public class OpenedChangeListener {
	private static List<OnOpenedChangeListener> linstener = new ArrayList<>();

	private OpenedChangeListener() {}
	
	public static void change() {
		for (OnOpenedChangeListener lis : OpenedChangeListener.linstener)
			lis.onOpenedChange();
	}
	
	public static void addOnOpenedChangeListener(OnOpenedChangeListener listener) {
		OpenedChangeListener.linstener.add(listener);
	}
	
	public static interface OnOpenedChangeListener {
		void onOpenedChange();
	}
}
