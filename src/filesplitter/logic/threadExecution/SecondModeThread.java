package filesplitter.logic.threadExecution;

import filesplitter.logic.config.SplitFile;

/**
 * A thread that splits files into a specified number of parts.
 */
public class SecondModeThread extends GenericModeThread {

	/**
	 * Invokes the superclass (GenericModeThread) by passing it the appropriate parameters for the second split mode.
	 * @param s The {@link filesplitter.logic.config.SplitFile}.
	 * @param threadPercentage The percentage of progress the thread will add to the JProgressBar once completed.
	 */
	public SecondModeThread(SplitFile s, double threadPercentage) {
		super(s, (int)s.config.options.getValue(), threadPercentage);
	}
	
	/**
	 * Starts splitting the file.
	 */
	@Override
	public void run() {
		super.run();
		threadCompleted();
	}
}
