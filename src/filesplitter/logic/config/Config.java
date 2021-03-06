package filesplitter.logic.config;

import javax.swing.JOptionPane;

/**
 * Generic abstract class for file configuration.
 */
public abstract class Config {
	private boolean error;
	/**
	 * Unit of measurement for file size.
	 */
	public static enum partUnit {B, KB, MB, GB};
	
	public Config() {
		error = false;
	}
	/**
	 * Shows an error message.
	 * @param errorType The type of error.
	 */
	protected void missingValue(String errorType) {
		JOptionPane.showMessageDialog(null,
			    errorType,
			    "Missing or NOT valid value",
			    JOptionPane.WARNING_MESSAGE);	
		error = true;
	}
	/**
	 * Returns the error status.
	 * @return true if an error has occurred, false otherwise.
	 */
	public boolean errorOccurred() {
		return error;
	}
	/**
	 * Returns the extension of a part.
	 * @return the extension of a part.
	 */
	public String getExtension() {
		return ".par";
	}
	/**
	 * Method used only in {@link EncryptConfig}.
	 * @return .
	 */
	public String getKey() {
		return "";
	}
	/**
	 * Abstract method that returns an object.
	 * @return the size of each part ({@link Mode1Config}, {@link CompressConfig}, {@link EncryptConfig}) or the number of parts ({@link Mode2Config}).
	 */
	public abstract  Object getValue();
	/**
	 * Abstract method that returns a string.
	 * Used in {@link Mode1Config}.
	 * @return a string.
	 */
	public abstract String getSizeUnit();
}
