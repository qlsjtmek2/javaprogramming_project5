package shg.vocabulary.ui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import shg.vocabulary.event.OpenedChangeListener;
import shg.vocabulary.object.EnglishVoca;
import shg.vocabulary.object.TableColumn;
import shg.vocabulary.object.Word;
import shg.vocabulary.object.tool.OpenedDictionary;
import shg.vocabulary.object.tool.PrintToGUI;

@SuppressWarnings("serial")
public class VocaPanel extends JPanel {
	private JPanel north;
	private JTextField searchField;
	private JButton searchButton;
	private JButton addWordButton;
	private JCheckBox wrongAnswerNoteCB;
	private JTable wordTable;
	private List<TableColumn> columns;
	
	public VocaPanel() {
		this.setLayout(new BorderLayout());
		
		north = new JPanel();
		searchField = new JTextField(20);
		
		searchButton = new JButton("검색");
		addWordButton = new JButton("단어추가");
		wrongAnswerNoteCB = new JCheckBox("오답노트 활성화");
		
		columns = new ArrayList<>();
		columns.add(new TableColumn.TableColumnBuilder()
				.setName("index")
				.setPreferredWidth(15)
				.setValue((w,m)->Integer.toString(m.getRowCount()+1)).build());
		columns.add(new TableColumn.TableColumnBuilder()
				.setName("영어 단어")
				.setPreferredWidth(170)
				.setValue((w,m)->w.eng).build());
		columns.add(new TableColumn.TableColumnBuilder()
				.setName("한글 뜻")
				.setPreferredWidth(170)
				.setValue((w,m)->w.kor).build());
		columns.add(new TableColumn.TableColumnBuilder()
				.setName("틀린 횟수")
				.setPreferredWidth(20)
				.setValue((w,m)->Integer.toString(w.getWrongCount())).build());
		
		DefaultTableModel model = new DefaultTableModel();
		for (TableColumn column : columns)
			model.addColumn(column.name);
		wordTable = new JTable(model);
		TableColumnModel columnModel = wordTable.getColumnModel();
		for (int i=0; i<columns.size(); i++)
			columnModel.getColumn(i).setPreferredWidth(columns.get(i).getPreferredWidth());
		JScrollPane scrollPane = new JScrollPane(wordTable);
		
		north.add(searchField);
		north.add(searchButton);
		north.add(addWordButton);
		north.add(wrongAnswerNoteCB);
		
		this.add(north, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		
		OpenedChangeListener.addOnOpenedChangeListener(new OpenedChangeListener.OnOpenedChangeListener() {

			@Override
			public void onOpenedChange() {
				PrintToGUI ptg = new PrintToGUI();
				ptg.print(OpenedDictionary.getInstance().getVoc(), wordTable, columns);
			}
			
		});
		
		searchField.addActionListener(new VocaPanelActionListener());
		searchButton.addActionListener(new VocaPanelActionListener());
		addWordButton.addActionListener(new AddWordActionListener());
		wrongAnswerNoteCB.addActionListener(new WorngNoteActionListener());
	}
	
	class VocaPanelActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String searchText = searchField.getText();
			if (searchText != null) {
				EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
				PrintToGUI ptg = new PrintToGUI();
				ptg.print(dictionary.searchWord(searchText), wordTable, columns);
			}
		}
		
	}
	
	class AddWordActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame frame = (JFrame) SwingUtilities.getRoot(VocaPanel.this);
			AddWordDialog dialog = new AddWordDialog(frame, "단어 추가");
			dialog.setVisible(true);
			// 모달 다이얼로그 이므로 setVisible() 메소드는
			// 다이얼로그가 닫힐 때까지 리턴하지 않는다.
			String[] texts = dialog.getInput();
			if (texts == null) return;
			
			EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
			dictionary.addWord(new Word(texts[0], texts[1]));
			dictionary.update();
			PrintToGUI ptg = new PrintToGUI();
			ptg.print(dictionary.getVoc(), wordTable, columns);
		}
		
	}

	class WorngNoteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
			
			if (wrongAnswerNoteCB.isSelected()) {
				List<Word> worngWords = dictionary.getSortWords((o1, o2)->(o1.getWrongCount()-o2.getWrongCount())*(-1));
				worngWords = worngWords.stream()
						.filter(word->(word.getWrongCount() > 0))
						.limit(20)
						.collect(Collectors.toList());
				PrintToGUI ptg = new PrintToGUI();
				ptg.print(worngWords, wordTable, columns);
			} else {
				PrintToGUI ptg = new PrintToGUI();
				ptg.print(dictionary.getVoc(), wordTable, columns);
			}
		}
		
	}
	
	class AddWordDialog extends JDialog {
		JTextField engField = new JTextField(10);
		JTextField korField = new JTextField(10);
		JButton addButton = new JButton("추가");
		JButton cancelButton = new JButton("취소");
		
		public AddWordDialog(JFrame frame, String title) {
			super(frame, title, true);
			this.setLayout(new GridLayout(3,2,10,10));
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(300, 150);
			
			this.add(new JLabel("영어 단어"));
			this.add(engField);
			this.add(new JLabel("한글 뜻"));
			this.add(korField);
			this.add(addButton);
			this.add(cancelButton);
			
			addButton.addActionListener(e->dispose());
			cancelButton.addActionListener(e->dispose());
			engField.addActionListener(e->dispose());
			korField.addActionListener(e->dispose());
		}
		
		String[] getInput() {
			if (engField.getText().length() == 0) return null;
			else if (korField.getText().length() == 0) return null;
			else {
				String[] strs = new String[2];
				strs[0] = engField.getText();
				strs[1] = korField.getText();
				return strs;
			}
		}

	}

}
