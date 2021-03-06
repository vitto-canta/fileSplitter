package filesplitter.gui.panels.rows;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
/**
 * A row that allows the user to choose one or more files from the file system.
 */
public class SelectFile extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton selectFileButton;
	public static JTextField selectedFiles;
	protected static JFileChooser fileChooser;
	
	/**
	 * Creates a JButton to choose the files and a JTextField to list the selected files.
	 */
	public SelectFile() {
		super();
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(null);
		
				
		selectFileButton = new JButton("Drop file from file system");
		selectFileButton.addActionListener(this);
		selectedFiles = new JTextField();
		selectedFiles.setEditable(false);
		selectedFiles.setMaximumSize(new Dimension(500, 24));
		selectedFiles.setMinimumSize(new Dimension(200, 24));
		selectedFiles.setPreferredSize(new Dimension(200, 24));
		
		add(selectFileButton);
		add(Box.createHorizontalStrut(10));
		add(selectedFiles);
		add(Box.createHorizontalStrut(10));
		
	}
	
	/**
	 * Opens a JFileChooser to select files.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.showOpenDialog(this);
		StringBuffer s = new StringBuffer();
		for(File f: fileChooser.getSelectedFiles()) {
			s.append("\"" + f.getName() + "\" ");
		}
		selectedFiles.setText(s.toString());
	}
	
}
