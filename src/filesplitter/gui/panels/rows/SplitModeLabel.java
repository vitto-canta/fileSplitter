package filesplitter.gui.panels.rows;

import javax.swing.*;

/**
 * A row containing a label that indicates to select a split mode.
 */
public class SplitModeLabel extends TemplateRow{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel splitMode;
	
	/**
	 * Creates the label.
	 */
	public SplitModeLabel() {
		super();
		splitMode = new JLabel("Select a splitting mode: ");
		add(splitMode);
	}
}

