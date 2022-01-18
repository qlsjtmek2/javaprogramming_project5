package shg.vocabulary.ui.panel;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {
	@java.io.Serial
	private static final long serialVersionUID = -4432412913715438027L;
	
	private final String imgPath = "img/my_picture.jpg";
	private final ImageIcon imgIcon;
	
	private JLabel myPicture;
	private JLabel myInfo;
	
	public HomePanel() {
		this.setLayout(new GridLayout(1, 2, 10, 10));
		imgIcon = new ImageIcon(imgPath);
		myPicture = new JLabel(imgIcon);
		myPicture.setSize(imgIcon.getIconWidth(), imgIcon.getIconHeight());
		
		myInfo = new JLabel("202010392 신희곤");
		myInfo.setHorizontalAlignment(SwingConstants.CENTER);
		myInfo.setFont(new Font("바탕체", Font.BOLD, 24));

		this.add(myPicture);
		this.add(myInfo);
	}
}