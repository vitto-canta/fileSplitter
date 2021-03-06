package filesplitter.logic.config;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import filesplitter.gui.panels.SplitPanel;

/**
 * Split configuration for the associated {@link SplitFile}.
 */
public class FileConfig {
	/**
	 * Possible split modes.
	 */
	public static enum splitMode {firstMode, secondMode, cryptMode, compressMode};
	/**
	 * Current split mode.
	 */
	public splitMode mode;
	/**
	 * File description.
	 */
	protected StringBuffer description;
	/**
	 * Current mode options.
	 */
	public Config options;
	
	/**
	 * Creates a configuration file based on the choices made by the user through the graphical interface.
	 * @param associatedFile The associated {@link SplitFile}.
	 */
	public FileConfig(SplitFile associatedFile) {
		description = new StringBuffer();
		Enumeration<AbstractButton> e = SplitPanel.splitModeGroup.getElements();
		while(e.hasMoreElements()) {
			AbstractButton b = e.nextElement();
			if(b.isSelected()) {
				if(b.getText().equals("By dimension of each part ")) {
					
					
					Enumeration<AbstractButton> ab = SplitPanel.splitMode1Options.getElements();
					while(ab.hasMoreElements()) {
						AbstractButton btn = ab.nextElement();
						if(btn.isSelected()) {
							String name = btn.getText();
							
							switch (name) {
							case "Protect with a key": {
								options = new EncryptConfig(description);
								mode = splitMode.cryptMode;
								break;
							}
							case "Compress": {
								options = new CompressConfig(description);
								mode = splitMode.compressMode;
								break;
							}
							case "Default": {
								options = new Mode1Config(description);
								mode = splitMode.firstMode;
								break;
							}	
							}
						}
					}
					
					
				} else {
					mode = splitMode.secondMode;
					options = new Mode2Config(description);
				}
				
			}
		}
	}
	/**
	 * Returns file description.
	 * @return file description.
	 */
	public String getDescription() {
		return description.toString();
	}
	
	
	
	
}
	

	

