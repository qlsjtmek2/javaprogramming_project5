package shg.vocabulary.ui.panel;

import javax.swing.JTabbedPane;

import shg.vocabulary.event.OpenedChangeListener;
import shg.vocabulary.object.tool.OpenedDictionary;

@SuppressWarnings("serial")
public class MyTabbedPane extends JTabbedPane {
	HomePanel HomePanel = new HomePanel();
	VocaPanel VocaPanel = new VocaPanel();
	QuizPanel QuizPanel = new QuizPanel();
	
	public MyTabbedPane() {
		super();
		
		this.add("홈", HomePanel);
		this.add("단어장", VocaPanel);
		this.add("퀴즈", QuizPanel);
		setEnable();
		
		OpenedChangeListener.addOnOpenedChangeListener(new OpenedChangeListener.OnOpenedChangeListener() {

			@Override
			public void onOpenedChange() {
				setEnable();
			}
			
		});
	}
	
	public void setEnable() {
		if (OpenedDictionary.getInstance() == null) {
			this.setEnabledAt(1, false);
			this.setEnabledAt(2, false);
		} else {
			this.setEnabledAt(1, true);
			this.setEnabledAt(2, true);
		}
	}
}
