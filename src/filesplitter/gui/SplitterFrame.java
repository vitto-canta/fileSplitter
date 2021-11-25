package filesplitter.gui;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import filesplitter.gui.panels.*;
import filesplitter.gui.panels.rows.RestartButton;
import filesplitter.logic.config.SplitFile;

/**
 * A class representing a custom JFrame.
 */
public class SplitterFrame extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SplitPanel sPanel;
	private MergePanel mPanel;	
	private JSeparator sep;
	private RestartButton restart;		
	
	public static ArrayList<SplitFile> splitFiles;
	public static Vector<File> parts;
	
	/**
	 * Sets the title of the JFrame and adds panels to it arranges them horizontally with {@link javax.swing.BoxLayout}.
	 * @param title JFrame title
	 */
	public SplitterFrame(String title) {
		super(title);
		
		SplitterFrame.splitFiles = new ArrayList<SplitFile>();
	
		Container c = getContentPane();		
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
        
		add(Box.createHorizontalStrut(10));	
       sPanel = new SplitPanel();
       c.add(sPanel);
       
       add(Box.createHorizontalStrut(10));	
       sep = new JSeparator(JSeparator.VERTICAL);
       getContentPane().add(sep);   
       add(Box.createHorizontalStrut(10));	
       
       mPanel = new MergePanel();
       c.add(mPanel);
       
       add(Box.createHorizontalStrut(10));	
       sep = new JSeparator(JSeparator.VERTICAL);
       getContentPane().add(sep);
       add(Box.createHorizontalStrut(10));	
       
       restart = new RestartButton();
       c.add(restart);
       add(Box.createHorizontalStrut(10));	
	
	}
}



	
