package filesplitter.gui.panels.rows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.TableQueue;
import filesplitter.logic.config.SplitFile;
import filesplitter.logic.threadExecution.EncryptModeThread;
import filesplitter.logic.threadExecution.FirstModeThread;
import filesplitter.logic.threadExecution.SecondModeThread;
import filesplitter.logic.threadExecution.ZipModeThread;

/**
 * A row that allows the user to remove/edit queued files or start the splitting process.
 */
public class SplitOperations extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton removeFiles, editFiles, start;
	public static JProgressBar progressBar;
	public static double totalProgress;;
	
	public static Integer activeThreads;
	
	public static int[] editFilesIndex;
	
	/**
	 * Creates components to select the operations to be performed.
	 */
	public SplitOperations() {
		
		removeFiles = new JButton("Remove");
		
		removeFiles.addActionListener(this);
		removeFiles.setEnabled(false);
		
		editFiles = new JButton("Change");
		
		editFiles.addActionListener(this);
		editFiles.setEnabled(false);
		
		start = new JButton("Start");
		start.addActionListener(this);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
	
		totalProgress = 0;
		
		
		add(removeFiles);
		add(Box.createHorizontalStrut(10));
		add(editFiles);
		add(Box.createHorizontalStrut(10));
		add(start);
		add(Box.createHorizontalStrut(10));
		add(progressBar);
		add(Box.createHorizontalStrut(10));
		

	}

	/**
	 * Reacts to the button click event by removing/editing queued files or starting the splitting process.
	 * Understands which button generated the event and performs the associated action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Remove": {
			int[] files = TableQueue.tableQueue.getSelectedRows();
			for(int index = files.length -1; index >= 0; index--) {
				SplitterFrame.splitFiles.remove(files[index]);
				TableQueue.dtm.removeRow(files[index]);
			}
			removeFiles.setEnabled(false);
			editFiles.setEnabled(false);
			break;
		}
		case "Change": {
			AddFileToQueue.addfileToQueue.setText("Apply changes");
			removeFiles.setEnabled(false);
			editFiles.setEnabled(false);
			start.setEnabled(false);
			
			editFilesIndex = TableQueue.tableQueue.getSelectedRows();
			TableQueue.tableQueue.setEnabled(false);
			break;
		}
		case "Start":{
			if(SplitterFrame.splitFiles.isEmpty() || allCompleted()) {
				JOptionPane.showMessageDialog(null,
					    "No queued files",
					    "File missing",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			start.setEnabled(false);
			totalProgress = 0;
			progressBar.setValue((int)totalProgress);
			double numberOfThreads = SplitterFrame.splitFiles.size();
			for(SplitFile s: SplitterFrame.splitFiles) {
				if(!s.getState().equals("Queued")) {
					numberOfThreads--;
				}
			}
			Thread t;
			double threadPercentage = 100.0 / numberOfThreads;
			activeThreads = (int)numberOfThreads;
			for(SplitFile s: SplitterFrame.splitFiles) {
				if((s.getState().equals("Queued"))) {
					switch (s.config.mode) {
					case firstMode: {
						t = new FirstModeThread(s, threadPercentage);
						t.start();
						break;
					}
					case secondMode: {
						t = new SecondModeThread(s, threadPercentage);
						t.start();
						break;
					}
					case compressMode: {
						t = new ZipModeThread(s, threadPercentage);
						t.start();
						break;
					}
					case cryptMode: {
						t = new EncryptModeThread(s, threadPercentage);
						t.start();
						break;
					}
					default: {
						return;
					}
					}
					
				}
			}
			break;
		}
		}
	}
	
	private boolean allCompleted() {
		for(SplitFile s: SplitterFrame.splitFiles) {
			if(s.getState().equals("Queued")) {
				return false;
			}
		}
		return true;
	}
	
}
