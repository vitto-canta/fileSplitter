package filesplitter.gui.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import filesplitter.gui.panels.rows.AddFileToQueue;
import filesplitter.gui.panels.rows.SelectFile;
import filesplitter.gui.panels.rows.SplitMode1;
import filesplitter.gui.panels.rows.SplitMode1Compress;
import filesplitter.gui.panels.rows.SplitMode1Encrypt;
import filesplitter.gui.panels.rows.SplitMode1Default;
import filesplitter.gui.panels.rows.SplitMode2;
import filesplitter.gui.panels.rows.SplitModeLabel;
import filesplitter.gui.panels.rows.SplitOperations;


/**
 * JPanel for split mode.
 */
public class SplitPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ButtonGroup splitModeGroup;
	public static ButtonGroup splitMode1Options;
	
	protected SelectFile selectFile;
	protected SplitModeLabel splitModeLabel;
	
	protected SplitMode1 splitMode1;
	protected SplitMode1Encrypt splitMode1Crypt;
	protected SplitMode1Compress splitMode1Compress;
	protected SplitMode1Default splitMode1Default;
	
	protected SplitMode2 splitMode2;
	
	protected AddFileToQueue addFileToQueue;
	
	protected TableQueue tableQueue;
	
	protected SplitOperations splitOperations;
	
	protected SplitInstructions inst;
	
	protected SplitTitle title;
	
	private JSeparator sep;
	
	/**
	 * Arranges the graphic components vertically with {@link javax.swing.BoxLayout}.
	 */
	public SplitPanel() {
		
				
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//addMouseListener(this);
		
		splitModeGroup = new ButtonGroup();
		splitMode1Options = new ButtonGroup();
		
		add(Box.createVerticalStrut(10));
		
		sep = new JSeparator(JSeparator.HORIZONTAL);
	    add(sep);
	    
	    title = new SplitTitle();
	    add(title);
	    
	   	    
	    add(Box.createVerticalStrut(10));	
	    
	    inst = new SplitInstructions();
	    add(inst);
	    
	    add(Box.createVerticalStrut(10));
	    
	    sep = new JSeparator(JSeparator.HORIZONTAL);
	    add(sep);
	    
	    add(Box.createVerticalStrut(10));
		selectFile = new SelectFile();
		add(selectFile);
		add(Box.createVerticalStrut(10));
		
		splitModeLabel = new SplitModeLabel();
		add(splitModeLabel);
		add(Box.createVerticalStrut(10));
		
		splitMode1Crypt = new SplitMode1Encrypt(splitMode1Options);
		splitMode1Compress = new SplitMode1Compress(splitMode1Options);
		splitMode1Default = new SplitMode1Default(splitMode1Options);
		
		splitMode1 = new SplitMode1(splitModeGroup, splitMode1Options);
		add(splitMode1);
		add(Box.createVerticalStrut(10));
		
		add(splitMode1Crypt);
		add(splitMode1Compress);
		add(splitMode1Default);
		add(Box.createVerticalStrut(10));
		
		splitMode2 = new SplitMode2(splitModeGroup);
		add(splitMode2);
		add(Box.createVerticalStrut(10));
		
		addFileToQueue = new AddFileToQueue();
		add(addFileToQueue);
		add(Box.createVerticalStrut(10));
		
		tableQueue = new TableQueue();
		add(tableQueue);
		add(Box.createVerticalStrut(10));
		
		splitOperations = new SplitOperations();
		add(splitOperations);
		add(Box.createVerticalStrut(10));
		
	}
	
}
