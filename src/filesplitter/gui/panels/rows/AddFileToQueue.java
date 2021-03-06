package filesplitter.gui.panels.rows;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.*;

import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.TableQueue;
import filesplitter.logic.config.FileConfig;
import filesplitter.logic.config.SplitFile;

/**
 * A row that allows the user to add/edit files in the queue.
 */
public class AddFileToQueue extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static JButton addfileToQueue;
	 
	/**
	 * Creates a JButton to add/edit files in the queue and adds an ActionListener to it.
	 */
	public AddFileToQueue() {
		super();
		addfileToQueue = new JButton("+ ADD FILE");
		addfileToQueue.addActionListener(this);
		add(addfileToQueue);
	}

	/**
	 * Reacts to the button click event by adding/editing files in the queue.
	 * @param e The button click event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("+ ADD FILE")) {
			File[] files = SelectFile.fileChooser.getSelectedFiles();
			if(files.length == 0) {
				missingValue("No file selected");
				return;
			}
			for(File f: files) {
				SplitFile tmp = new SplitFile(f);
				tmp.setState("Queued");
				String fileSize = fileSize(f);
				
				if(tmp.config.mode == FileConfig.splitMode.secondMode && ((int)tmp.config.options.getValue() > f.length())) {
					JOptionPane.showMessageDialog(null,
							"It should lower than the dimension (in byte) of the file \n (minimum unit of a part: 1 byte)" ,
						    "The number of parts is missing or NOT valid." ,
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				TableQueue.dtm.addRow(new Object[] {tmp.getPath(), fileSize, tmp.config.getDescription(), tmp.getState()});
				SplitterFrame.splitFiles.add(tmp);
				
				if(tmp.config.options.errorOccurred()) {
					SplitterFrame.splitFiles.remove(tmp);
					int tableRows = TableQueue.dtm.getRowCount();
					TableQueue.dtm.removeRow(tableRows - 1);
					return;
	
				}
			}
			clearFields();
			SplitMode1Encrypt.resetKeyField(); 
		} else if(e.getActionCommand().equals("Apply changes")) {
			for(int index: SplitOperations.editFilesIndex) {
				File f = SplitterFrame.splitFiles.get(index).getFile();
				SplitFile newSplitFile = new SplitFile(f);
				if(newSplitFile.config.options.errorOccurred()) {
					return;
				}
				newSplitFile.setState("Queued");
				SplitterFrame.splitFiles.set(index, newSplitFile);
				String fileSize = fileSize(f);
				TableQueue.dtm.setValueAt(fileSize, index, 1);
				TableQueue.dtm.setValueAt(newSplitFile.config.getDescription(), index, 2);
				TableQueue.dtm.setValueAt(newSplitFile.getState(), index, 3);
			}
			clearFields();
			SplitMode1Encrypt.resetKeyField();
			addfileToQueue.setText("+ ADD FILE");
			SplitOperations.removeFiles.setEnabled(true);
			SplitOperations.editFiles.setEnabled(true);
			SplitOperations.start.setEnabled(true);
			
			TableQueue.tableQueue.setEnabled(true);
		}
	}
	
	private void missingValue(String errorType) {
		JOptionPane.showMessageDialog(null,
			    errorType,
			    "Missing or NOT valid value",
			    JOptionPane.WARNING_MESSAGE);
	}
	
	private String fileSize(File f) {
		DecimalFormat df = new DecimalFormat("#.##");
        
		long byteSize = f.length();
		double resultSize;
		switch (SplitMode1.sizeUnit.getSelectedItem().toString()) {
		case "Byte": {
			resultSize = byteSize;
			return (df.format(resultSize) + " B");
		}
		case "Kilobyte": {
			resultSize = (byteSize/1000.0);
			return (df.format(resultSize) + " KB");
		}
		case "Megabyte": {
			resultSize = (byteSize/1000000.0);
			return (df.format(resultSize) + " MB");
		}
		case "Gigabyte": {
			resultSize = (byteSize/1000000000.0);
			return (df.format(resultSize) + " GB");
		}
		default: {
			resultSize = byteSize;
			return (df.format(resultSize) + " B");
		}
		}
	}
	
	private static void clearFields() {
		SelectFile.selectedFiles.setText("");
		SplitMode1.byteSize.setText("");
		SplitMode1.sizeUnit.setSelectedIndex(0);
		SplitMode2.parts.setText("");
		
	}
}

