package shg.vocabulary.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import shg.vocabulary.exception.OpenFileException;
import shg.vocabulary.object.EnglishVoca;
import shg.vocabulary.object.Word;
import shg.vocabulary.object.tool.DictionaryToFileConverter;
import shg.vocabulary.object.tool.FileToDictionaryConverter;
import shg.vocabulary.object.tool.OpenedDictionary;

@SuppressWarnings("serial")
public class MyMenu extends JMenuBar {
	JMenu fileMenu, helpMenu;
	
	public MyMenu() {
		fileMenu = new JMenu("파일");
		
		JMenuItem create = new JMenuItem("새로 만들기");
		JMenuItem open = new JMenuItem("열기");
		JMenuItem save = new JMenuItem("저장");
		JMenuItem saveas = new JMenuItem("다른 이름으로 저장...");
		
		create.addActionListener(new MenuActionListener());
		open.addActionListener(new MenuActionListener());
		save.addActionListener(new MenuActionListener());
		saveas.addActionListener(new MenuActionListener());
		
		fileMenu.add(create);
		fileMenu.add(open);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveas);
		
		helpMenu = new JMenu("도움말");
		helpMenu.add(new JMenuItem("미구현"));
		
		this.add(fileMenu);
		this.add(helpMenu);
	}
	
	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if (cmd.equals("새로 만들기")) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"TXT Files", 
						"txt");
				chooser.setFileFilter(filter);
				int ret = chooser.showSaveDialog(null);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					String pathName = chooser.getSelectedFile().getPath();
					String fileName = chooser.getSelectedFile().getName();
					
					EnglishVoca voca = new EnglishVoca.EnglishVocaBuilder()
							.setVoc(new ArrayList<Word>())
							.setDictionaryName(fileName)
							.setFilePath(pathName)
							.build();
					
					DictionaryToFileConverter converter = new DictionaryToFileConverter(voca, pathName);
					converter.convert();
					OpenedDictionary.setInstance(voca);
				}
			}
			
			else if (cmd.equals("열기")) {
				try {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"TXT Files", 
							"txt");
					chooser.setFileFilter(filter);
					int ret = chooser.showOpenDialog(null);
					
					if (ret == JFileChooser.APPROVE_OPTION) {
						String pathName = chooser.getSelectedFile().getPath();
						
						FileToDictionaryConverter converter = new FileToDictionaryConverter(new File(pathName));
						OpenedDictionary.setInstance(converter.convert());
					}
				} catch (OpenFileException ex) {
					JOptionPane.showMessageDialog(null, "올바른 파일의 형식이 아닙니다.", "Message", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			
			else if (cmd.equals("저장")) {
				try { 
					MainFrame.save();
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "생성된 단어장이 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			else if (cmd.equals("다른 이름으로 저장...")) {
				try {
					EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"TXT Files", 
							"txt");
					chooser.setFileFilter(filter);
					int ret = chooser.showSaveDialog(null);
					
					if (ret == JFileChooser.APPROVE_OPTION) {
						String pathName = chooser.getSelectedFile().getPath();

						DictionaryToFileConverter converter = new DictionaryToFileConverter(dictionary, pathName);
						converter.convert();
					}
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "생성된 단어장이 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
	}
}
