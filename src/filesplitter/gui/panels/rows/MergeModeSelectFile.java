package filesplitter.gui.panels.rows;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.FileTableModel;

/**
 * A row that allows the user to choose the file to be reassembled (only the first part).
 */
public class MergeModeSelectFile extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton selectFileButton;
	public static JTextField selectedFiles;
	public JTable partsList;
	public static FileTableModel dtm;
	public JScrollPane fileParts;
	protected JFileChooser fileChooser;
	protected MergeModeKey decrypt;
	protected static enum mergeModeEnum {normalMode, compressMode, cryptMode};
	protected static mergeModeEnum mergeMode;
	protected static String fileName;
	protected MergeModeStart start;
	
	/**
	 * Arranges the graphic components vertically with {@link BoxLayout}.
	 */
	public MergeModeSelectFile() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		SplitterFrame.parts = new Vector<File>();
		
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setCurrentDirectory(null);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".par", "par");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		
		selectFileButton = new JButton("Drop file from file system (only the 1st part)");
		selectFileButton.setAlignmentX(CENTER_ALIGNMENT);
		selectFileButton.addActionListener(this);
		
		add(Box.createVerticalStrut(10));
		add(selectFileButton);
		add(Box.createVerticalStrut(10));
			    
	    decrypt = new MergeModeKey();
		add(decrypt);
						
		dtm = new FileTableModel();
		String header[] = new String[] {"File", "Part", "State"};
		dtm.setColumnIdentifiers(header);
		
		partsList = new JTable();
		partsList.setModel(dtm);
		partsList.setPreferredScrollableViewportSize(new Dimension(0, 100));
		
		fileParts = new JScrollPane(partsList);

		add(fileParts);
		
		add(Box.createVerticalStrut(10));
		
		start = new MergeModeStart();
		add(start);
		add(Box.createVerticalStrut(10));
	}
	
		
	/**
	 * Reacts to the button click event by opening a JFileChooser and configuring the merge mode according to the file chosen by the user.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.showOpenDialog(this);
		File path = null;
		try {
			path = new File(fileChooser.getSelectedFile().getParent());
		} catch (NullPointerException ex) {
			return;
		}
		SplitterFrame.parts.clear();
		int rows = dtm.getRowCount();
		for(int index=rows-1; index>=0; index--) {
			dtm.removeRow(index);
		}
		String firstPart = fileChooser.getSelectedFile().getName();
		int firstPartLenght = firstPart.length();
		fileName = "";
		String ext = "";
		

		if(firstPart.endsWith(".1.crypt.par")) {
			fileName = firstPart.substring(0, firstPartLenght - 12);
			ext = ".crypt.par";
			mergeMode = mergeModeEnum.cryptMode;
			enableCryptField(true);
		} else if(firstPart.endsWith(".1.zip.par")) {
			fileName = firstPart.substring(0, firstPartLenght - 10);
			ext = ".zip.par";
			mergeMode = mergeModeEnum.compressMode;
			enableCryptField(false);
		} else if(firstPart.endsWith(".1.par")) {
			fileName = firstPart.substring(0, firstPartLenght - 6);
			ext = ".par";
			mergeMode = mergeModeEnum.normalMode;
			enableCryptField(false);
		} else {
			dtm.setColumnIdentifiers(new Object[] {"File", "Part","State"});
			JOptionPane.showMessageDialog(null,
				    "Be sure you've selected the 1st part of the file",
				    "Invalid file",
				    JOptionPane.WARNING_MESSAGE);
			enableCryptField(false);
			return;
		}
		int partNumber;
		
		File[] listFiles = path.listFiles();
		for(File f: listFiles) {
			if(f.getName().startsWith(fileName) && f.getName().endsWith(ext)) {
				String numberString = f.getName().substring(fileName.length()+1, f.getName().length()-ext.length());
				partNumber = Integer.parseInt(numberString);
				if(SplitterFrame.parts.size() < partNumber) {
					SplitterFrame.parts.setSize(partNumber);
				}
				SplitterFrame.parts.set(partNumber-1, f);	
			}
		}
		int index = 1;
		for(File f: SplitterFrame.parts) {
			if(f == null) {
				JOptionPane.showMessageDialog(null,
					     index + "th part missing",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			dtm.addRow(new Object[] {f.getName(),index, "waiting"});
			index++;
			}
		}		
	
	private void enableCryptField(boolean b) {
		MergeModeKey.keyField.setText("");
		MergeModeKey.keyField.setEditable(b);
		MergeModeKey.showKey.setEnabled(b);
		MergeModeKey.showKey.setSelected(false);
		MergeModeKey.keyField.setEchoChar(MergeModeKey.originalEchoChar);
	}
	
}

