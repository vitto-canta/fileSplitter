package filesplitter.logic.threadExecution;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import filesplitter.logic.config.SplitFile;

/**
 * A thread that splits files into parts with specified size (and compresses them).
 */
public class ZipModeThread extends FirstModeThread {
	
	/**
	 * Invokes the superclass (FirstModeThread) by passing it the appropriate parameters for the first split mode.
	 * @param s The {@link filesplitter.logic.config.SplitFile}.
	 * @param threadPercentage The percentage of progress the thread will add to the JProgressBar once completed.
	 */
	public ZipModeThread(SplitFile s, double threadPercentage) {
		super(s, threadPercentage);
	}
	
	/**
	 * Starts splitting and compressing the file.
	 */
	@Override
	public void run() {
		super.run();
		try {
			createZipFromFiles();
		} catch (IOException e) {
			System.out.println("I/O Error");
		}
		threadCompleted();
	}
	
	private void createZipFromFiles() throws IOException{
		FileOutputStream fos;
		ZipOutputStream zos;
		
		for(File f: super.fileParts) {
			String partName = f.getName().substring(1);
			String zipName = partName + ".zip.par";
			File newFile = new File(f.getParent(), zipName);
			
			fos = new FileOutputStream(newFile);
			zos = new ZipOutputStream(fos);
			
			addToZipFile(f, partName, zos);
			f.delete();
		} 
	}

	private static void addToZipFile(File file, String fileName, ZipOutputStream zos) throws IOException {

	    FileInputStream fis = new FileInputStream(file);
	    ZipEntry zipEntry = new ZipEntry(fileName);
	    
	    zos.putNextEntry(zipEntry);
	    
	    try {
	    	fis.transferTo(zos);
	    } catch (NoSuchMethodError e) {
			e.printStackTrace();
		}
	    zos.closeEntry();
	    fis.close();
	}
}

