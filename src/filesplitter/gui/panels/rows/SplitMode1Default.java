package filesplitter.gui.panels.rows;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 * A row that allows the user to select the first mode (without additional options).
 * This row is used to deselect the compression/cryptography option.
 */
public class SplitMode1Default extends TemplateRow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JRadioButton defaultOption;
	
	/**
	 * Creates components to select this mode.
	 * @param splitMode1Options ButtonGroup containing the options of the first split mode.
	 */ 
	public SplitMode1Default(ButtonGroup splitMode1Options) {
		super();
		
		defaultOption = new JRadioButton("Default");
		defaultOption.setSelected(true);
		splitMode1Options.add(defaultOption);
		add(Box.createHorizontalStrut(30));
		add(defaultOption);
	}
	/**
	public JRadioButton getRadioButton() {
		return defaultOption;
	}*/
}
