package filesplitter.gui.panels.rows;

import javax.swing.*;
/**
 * Abstract class representing a template row.
 */
public abstract class TemplateRow extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets a horizontal {@link javax.swing.BoxLayout} and a left-alignment for the row.
	 */
	public TemplateRow() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
	}
}

