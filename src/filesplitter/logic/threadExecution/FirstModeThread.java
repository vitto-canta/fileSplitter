package filesplitter.logic.threadExecution;

import filesplitter.logic.config.FileConfig;
import filesplitter.logic.config.SplitFile;

/**
 * A thread that splits files into parts with specified size.
 */
public class FirstModeThread extends GenericModeThread {
	SplitFile s;
	
	/**
	 * Invokes the superclass (GenericModeThread) by passing it the appropriate parameters for the first split mode.
	 * @param s The {@link filesplitter.logic.config.SplitFile}.
	 * @param threadPercentage The percentage of progress the thread will add to the JProgressBar once completed.
	 */
	public FirstModeThread(SplitFile s, double threadPercentage) {
		super(s, (long)s.config.options.getValue(), threadPercentage);
		this.s = s;
	}
	
	/**
	 * Starts splitting the file.
	 */
	@Override
	public void run() {
		super.run();
		if(s.config.mode == FileConfig.splitMode.compressMode
				|| s.config.mode == FileConfig.splitMode.cryptMode) {
			return;
		}
		threadCompleted();
	}
	
	
}
