package shg.vocabulary.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import shg.vocabulary.object.EnglishVoca;
import shg.vocabulary.object.tool.DictionaryToFileConverter;
import shg.vocabulary.object.tool.OpenedDictionary;
import shg.vocabulary.ui.panel.MyTabbedPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	MyMenu menu = new MyMenu();
	MyTabbedPane tapPane = new MyTabbedPane();
	
	public MainFrame() {
		this("202010392 신희곤");
	}
	
	public MainFrame(String title) {
		super(title);
		this.setSize(540,500);
		this.setLocationRelativeTo(null); // 창을 중앙에 띄우기
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		this.setVisible(true);
	}
	
	public void init() {
		Container frame = this.getContentPane();
		frame.add(tapPane);
		this.setJMenuBar(menu);
		
		/* Ctrl+S 누를시 저장기능
		 * 코드 출처: https://stackoverflow.com/questions/62314873/how-to-make-save-shortcut-like-ctrls
		 */
		this.getRootPane().registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try { 
					save();
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "생성된 단어장이 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
				}
			}
            
        }, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
            public void windowClosing(WindowEvent e) { 
				try { save(); } catch (NullPointerException ex) {}
            }
            
		});
	}
	
	public static void save() throws NullPointerException {
		EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
		DictionaryToFileConverter converter = new DictionaryToFileConverter(dictionary, dictionary.filePath);
		converter.convert();
	}
}
