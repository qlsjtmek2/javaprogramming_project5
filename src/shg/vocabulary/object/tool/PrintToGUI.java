package shg.vocabulary.object.tool;

import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shg.vocabulary.object.QuizTest;
import shg.vocabulary.object.TableColumn;
import shg.vocabulary.object.Word;
import shg.vocabulary.ui.panel.QuizPanel;

public class PrintToGUI {
	public PrintToGUI() {}
	
	public void print(List<Word> voc, JTable table, List<TableColumn> columns) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Word word : voc) {
			addRecord(word, model, columns);
		}
	}
	
	private void addRecord(Word word, DefaultTableModel model, List<TableColumn> columns) {
		String[] record = new String[columns.size()];
		for (int i=0; i<columns.size(); i++)
			record[i] = columns.get(i).value(word, model);
		model.addRow(record);
	}
	
	public void quizStart(QuizTest test, QuizPanel panel) {
		panel.settingStartQuizPanel();
		JTabbedPane tp = (JTabbedPane) panel.getParent();
		tp.setEnabledAt(0, false);
		tp.setEnabledAt(1, false);
		Thread timer = new Thread(test);
		test.timer = timer;
		timer.start();
	}
}
