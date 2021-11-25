package filesplitter.gui.panels;

	import java.awt.Font;
	import javax.swing.BorderFactory;
	import javax.swing.BoxLayout;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import java.awt.Component;
	import java.awt.FlowLayout;

	/** 
	 * Creates a JPanel to explain how to use the SplitPanel
	 */
public class SplitInstructions extends JPanel{
	
		
			private static final long serialVersionUID = 1L;
			private JPanel panel1;
			private JPanel panel2;
			private JPanel panel3;
			private JLabel label1;
			private JLabel label2;
			private JLabel label3;
			private Font f;
			
			/**Creates a JPanel with a {@link javax.swing.BoxLayout}
			 * with three JPanels inside using a  {@link java.awt.FlowLayout}
			 */
			
			public SplitInstructions() {
				
				this.setBorder(BorderFactory.createTitledBorder("HOW IT WORKS?"));
				
				panel1 = new JPanel();
		        panel2 = new JPanel();
		        panel3 = new JPanel();
		        
		        panel1.setBorder(BorderFactory.createTitledBorder("1. SELECT FILE"));
		        panel2.setBorder(BorderFactory.createTitledBorder("2. CHOOSE THE MODE"));
		        panel3.setBorder(BorderFactory.createTitledBorder("3. GO"));
		        
		        BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		        BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
		        BoxLayout layout3 = new BoxLayout(panel3, BoxLayout.Y_AXIS);
		        
		        panel1.setLayout(layout1);
		        panel2.setLayout(layout2);
		        panel3.setLayout(layout3);
		        
		        label1 = new JLabel();		
		        label1.setText("<html> Select the file you want<br>"
		        		+ "to split from your file system </html>");
		        		
				f = new Font("Times New Roman", Font.ITALIC, 10);			
				label1.setFont(f);
				
				label2 = new JLabel();
				label2.setText("<html> Select the mode you desire. <br>"
						+ "you can choose the dimensions of each part <br>"
						+ "or the number of parts. in the firs case you <br>"
						+ "can also protect your file with a password <br>"
						+ "or compress it. the programm will calculate the <br>"
						+ "number or the dimension of the resulting files <br>"
						+ "automatically </html>");
				label2.setFont(f);
				
				label3 = new JLabel("<html> Push the button <br>"
						+ " Start to start splitting <br>"
						+ "the file. You can split more <br>"
						+ "than one file at once. <br>"
						+ "You can also aplly changes <br>"
						+ "whenever you want </html>");
				label3.setFont(f);
		        
		        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
		        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
		        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
		        panel1.add(label1);
		        panel2.add(label2);
		        panel3.add(label3);
		        
		        this.setLayout(new FlowLayout());
		        add(panel1);
		        add(panel2);
		        add(panel3);
		        		         
		        // Set the window to be visible as the default to be false
		        this.setVisible(true);
			}
	}



	

