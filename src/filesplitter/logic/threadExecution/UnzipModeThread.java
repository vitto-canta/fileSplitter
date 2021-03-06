package filesplitter.logic.threadExecution;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.rows.MergeModeStart;

/**
 * A thread that unzips and merges multiple parts to rebuild a file.
 */
public class UnzipModeThread extends MergeNormalModeThread {
	
	/**
	 * Invokes the superclass (NormalModethread) by passing it the name of the file that will be reassembled.
	 * @param fileName The name of the file that will be reassembled.
	 */
	public UnzipModeThread(String fileName) {
		super(fileName);
	}
	
	/**
	 * Starts rebuilding the file.
	 */
	@Override
	public void run() {
		double partPercentage = 100/SplitterFrame.parts.size();
		double totalPercentage = 0;
		
		try {
			String parent = SplitterFrame.parts.elementAt(0).getParent();
			File f = new File(parent, fileName);
			FileOutputStream out = new FileOutputStream(f);
			
			FileInputStream fis;
		
			for(File part: SplitterFrame.parts) {
				fis = new FileInputStream(part);
				ZipInputStream zis = new ZipInputStream(fis);
				ZipEntry zipEntry  = zis.getNextEntry();
				while(zipEntry != null) {
					zis.transferTo(out);
					zis.closeEntry();
					zipEntry = zis.getNextEntry();
				}
				zis.closeEntry();
				zis.close();
				fis.close();
				totalPercentage+=partPercentage;
				MergeModeStart.progressBar.setValue((int)totalPercentage);
				part.delete();
			}
			totalPercentage = 100;
			MergeModeStart.progressBar.setValue((int)totalPercentage);
			MergeModeStart.start.setEnabled(true);
		} catch (IOException e) {
			System.out.println("I/O Error");
		} 
		MergeModeStart.threadCompleted(fileName);
	}
}


