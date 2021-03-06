package filesplitter.logic.config;

/**
 * A class containing the options for the first split mode (with compression).
 */
public class CompressConfig extends Mode1Config {
	/**
	 * Sets the parameters for the first split mode (with compression).
	 * @param description File description.
	 */
	public CompressConfig(StringBuffer description) {
		super(description);
		description.append(" | Compressed");
	}
	
	
}
