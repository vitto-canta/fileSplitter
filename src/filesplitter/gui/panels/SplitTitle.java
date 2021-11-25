package filesplitter.gui.panels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
/**
 * JPanel for split title
 */
public class SplitTitle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel title2;
	private Font f;
	
	
	public SplitTitle() {
	
		title2 = new JLabel("SPLIT FILE");
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		f= new Font("Times New Roman", Font.BOLD, 20);
		setLayout(new BorderLayout(0, 0));
		title2.setFont(f);
		add(title2);
			
	}
}
