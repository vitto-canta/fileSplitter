package filesplitter.gui.panels;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.FlowLayout;

/**
 * Creates a JPanel to explain how to use the MergePanel
 */
public class MergeInstructions extends JPanel{
			
		private static final long serialVersionUID = 1L;
		
		private JPanel panel1;
		private JPanel panel2;
		private JLabel label1;
		private JLabel label2;
		private Font f;	
		
		/**
		* Creates a JPanel with a {@link javax.swing.BoxLayout}
		* with two JPanels inside using a  {@link java.awt.FlowLayout}
		*/		
		public MergeInstructions() {
			
			this.setBorder(BorderFactory.createTitledBorder("HOW IT WORKS?"));
			
			panel1 = new JPanel();
	        panel2 = new JPanel();
	        
	        panel1.setBorder(BorderFactory.createTitledBorder("1. SELECT FILE"));
	        panel2.setBorder(BorderFactory.createTitledBorder("2. GO"));
	        
	        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
	        BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
	        
	        panel1.setLayout(layout1);
	        panel2.setLayout(layout2);
	        
	        label1 = new JLabel();		
	        label1.setText("<html> Select only the first part<br>"
	        		+ "of a file with .par extension.<br>"
	        		+ "The system will find the follwing<br>"
	        		+ "parts automatically. In case of a<br>"
	        		+ "crypted file, you will ask<br>"
	        		+ "to insert the key.</html>");
	        		
			f = new Font("Times New Roman", Font.ITALIC, 10);			
			label1.setFont(f);
			
			label2 = new JLabel("<html> Push the button <br>"
					+ "Start to start merging<br>"
					+ "the files. Wait untill<br>"
					+ "you read file created<br>"
					+ "correctly </html>");
			label2.setFont(f);
	        
	        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
	        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
	        panel1.add(label1);
	        panel2.add(label2);
	        
	        this.setLayout(new FlowLayout());
	        add(panel1);
	        add(panel2);
	        	         
	        // Set the window to be visible as the default to be false
	        this.setVisible(true);		
			
		}
}
