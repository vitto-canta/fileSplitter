package filesplitter.gui.panels.rows;

import javax.swing.*;

/**
 * A row that allows the user to select the first split mode (with compression).
 */
public class SplitMode1Compress extends TemplateRow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JRadioButton compress;
	
	/**
	 * Creates components to select this mode.
	 * @param splitMode1Options ButtonGroup containing the options of the first split mode.
	 */
	public SplitMode1Compress(ButtonGroup splitMode1Options) {
		super();
		
		compress = new JRadioButton("Compress");
		splitMode1Options.add(compress);
		add(Box.createHorizontalStrut(30));
		add(compress);
	}
}

