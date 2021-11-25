package filesplitter.gui.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import filesplitter.gui.panels.rows.MergeModeSelectFile;

/**
 * JPanel for merge mode.
 */
public class MergePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected MergeModeSelectFile selectFile;
	protected JSeparator sep;
	protected MergeTitle title;
	protected MergeInstructions inst;
	/**
	 * Arranges the graphic components vertically with {@link javax.swing.BoxLayout}.
	 */
	public MergePanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(Box.createVerticalStrut(10));
		
		sep = new JSeparator(JSeparator.HORIZONTAL);
	    add(sep);
	    
		title = new MergeTitle();
		add(title);
		
		   
	    add(Box.createVerticalStrut(10));	
	    
	    inst = new MergeInstructions();
	    add(inst);
	    
	    add(Box.createVerticalStrut(10));
	    
	    sep = new JSeparator(JSeparator.HORIZONTAL);
	    add(sep);
	    
	     	
		selectFile = new MergeModeSelectFile();
		selectFile.selectFileButton.setToolTipText("Select only the first part of the file");
		selectFile.setAlignmentX(CENTER_ALIGNMENT);
		add(selectFile);
		add(Box.createVerticalStrut(111));
				
	}
}
