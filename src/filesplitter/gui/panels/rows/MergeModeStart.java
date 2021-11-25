package filesplitter.gui.panels.rows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import filesplitter.gui.SplitterFrame;
import filesplitter.logic.threadExecution.DecryptModeThread;
import filesplitter.logic.threadExecution.MergeNormalModeThread;
import filesplitter.logic.threadExecution.UnzipModeThread;

/**
 * A row contained in the {@link MergeModeKey} column.
 */
public class MergeModeStart extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton start;
	public static JProgressBar progressBar;
	
	/**
	 * Arranges the graphic components horizontally with {@link BoxLayout}.
	 */
	public MergeModeStart() {
		super();
			
		start = new JButton("Start");
		start.addActionListener(this);
		add(start);
		add(Box.createHorizontalStrut(10));
		
				
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		add(progressBar);
		add(Box.createHorizontalStrut(10));
		
	}

	/**
	 * Reacts to the button click event by starting the file merging process.
	 * @param e The button click event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MergeModeStart.progressBar.setValue(0);
		if(SplitterFrame.parts.size() == 0) {
			MergeModeSelectFile.dtm.setColumnIdentifiers(new Object[] {"File","Part","State"});
			
			int rows = MergeModeSelectFile.dtm.getRowCount();
			for(int index=rows-1; index>=0; index--) {
				MergeModeSelectFile.dtm.removeRow(index);
			}
			
			JOptionPane.showMessageDialog(null,
				    "Select the file you want to merge",
				    "No file selected",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		Thread mergeOp;
		String fileName = MergeModeSelectFile.fileName;
		
		MergeModeSelectFile.dtm.addRow(new Object[] {fileName, null , "creating the file"});
		
			
		switch (MergeModeSelectFile.mergeMode) {
		case normalMode: {
			mergeOp = new MergeNormalModeThread(fileName);
			mergeOp.start();
			break;
		}
		case compressMode: {
			mergeOp = new UnzipModeThread(fileName);
			mergeOp.start();
			break;
		}
		case cryptMode: {
			mergeOp = new DecryptModeThread(fileName);
			mergeOp.start();
			break;
		}
		}
	}
	
	/**
	 * Add a row in the table to inform the file is created correctly
	 * @param fileName The name of the merging file
	 */
	public static void threadCompleted(String fileName) {
		MergeModeSelectFile.dtm.addRow(new Object[] {fileName, null , "file created correctly"});
		SplitterFrame.parts.clear();
	}
}
