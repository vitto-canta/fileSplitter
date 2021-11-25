package filesplitter.gui.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * JPanel for merge title.
 */
public class MergeTitle extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel title2;
	private Font f;
	
	
	public MergeTitle() {
		  
		title2 = new JLabel("MERGE FILES");
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		f= new Font("Times New Roman", Font.BOLD, 20);
		setLayout(new BorderLayout(10, 10));
		title2.setFont(f);
		add(title2);
	}

}
