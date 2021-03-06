package filesplitter.logic.threadExecution;

import java.io.*;
import java.util.ArrayList;

import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.TableQueue;
import filesplitter.gui.panels.rows.SplitOperations;
import filesplitter.logic.config.FileConfig;
import filesplitter.logic.config.SplitFile;

/**
 * A thread that splits files according to the selected mode.
 */
public abstract class GenericModeThread extends Thread {
	private SplitFile sFile;
	private long value;
	private double threadPercentage;
	
	protected ArrayList<File> fileParts;
	
	/**
	 * Configures the thread before execution.
	 * @param file The file.
	 * @param partSize The size of each part (or the number of parts for the second mode).
	 * @param threadPercentage The percentage of progress the thread will add to the JProgressBar once completed.
	 */
	public GenericModeThread(SplitFile file, long partSize, double threadPercentage) {
		super();
		sFile = file;
		if(file.config.mode == FileConfig.splitMode.secondMode) {
			value = partSize;
		} else {
			value = getPartSize(file, partSize);
		}
		this.threadPercentage = threadPercentage;
	}
	
	/**
	 * Starts splitting the file.
	 */
	public void run() {
		sFile.setState("Running");
		TableQueue.dtm.setValueAt(sFile.getState(), SplitterFrame.splitFiles.indexOf(sFile), 3);
		try {
			splitFile(sFile);
		} catch (IOException e) {
			System.out.println("I/O Error");
			SplitOperations.totalProgress = 0;
			SplitOperations.progressBar.setValue((int)SplitOperations.totalProgress);
			sFile.setState("Errore in I/O");
			TableQueue.dtm.setValueAt(sFile.getState(), SplitterFrame.splitFiles.indexOf(sFile), 3);
			SplitOperations.start.setEnabled(true);
			return;
		}
	}
	
	private void splitFile(SplitFile file) throws IOException {
		fileParts = new ArrayList<File>();
		File f = file.getFile();
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		long fileSize = f.length();
		long partSize;
		long partsNumber;
		long remainingBytes = fileSize;
		boolean isSecondMode = false;
		if(file.config.mode == FileConfig.splitMode.secondMode) {
			isSecondMode = true;
			partsNumber = value;
			partSize = fileSize/partsNumber;
		} else {
			partSize = value;
			partsNumber = fileSize / partSize;
		}
        
		long maxReadBufferSize = 8*1024;
		
        for(int part=1; part <= partsNumber; part++) {
        	if(part == partsNumber && isSecondMode) {
        		partSize += fileSize%partsNumber;
        	}
        	
        	String filePartName;
        	if(file.config.mode == FileConfig.splitMode.compressMode
        			|| file.config.mode == FileConfig.splitMode.cryptMode) {
        		filePartName = "." + file.getFile().getName() + "." + part;
        	} else {
        		filePartName = file.getFile().getName() + "." + part + file.config.options.getExtension();
        	}
    	
        	File newFile = new File(file.getFile().getParent(), filePartName);
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(newFile));
            if(partSize > maxReadBufferSize) {
                long numReads = partSize/maxReadBufferSize;
                long numRemainingRead = partSize % maxReadBufferSize;
                for(int i=0; i<numReads; i++) {
                    remainingBytes -= readWrite(bis, bw, maxReadBufferSize);   
                }
                if(numRemainingRead > 0) {
                   remainingBytes -= readWrite(bis, bw, numRemainingRead);
                }
            }else {
                remainingBytes -= readWrite(bis, bw, partSize);
            }
            bw.close();
            fileParts.add(newFile);
        }
        if(remainingBytes > 0) {
        	int part = (int)partsNumber + 1;
        	String filePartName;
        	if(file.config.mode == FileConfig.splitMode.compressMode
        			|| file.config.mode == FileConfig.splitMode.cryptMode) {
        		filePartName = "." + file.getFile().getName() + "." + part;
        	} else {
        		filePartName = file.getFile().getName() + "." + part + file.config.options.getExtension();
        	}
  
        	File newFile = new File(file.getFile().getParent(), filePartName);
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(newFile));
            if(remainingBytes > maxReadBufferSize) {
                long numReads = remainingBytes/maxReadBufferSize;
                long numRemainingRead = remainingBytes % maxReadBufferSize;
                for(int i=0; i<numReads; i++) {
                    remainingBytes -= readWrite(bis, bw, maxReadBufferSize);   
                }
                if(numRemainingRead > 0) {
                   remainingBytes -= readWrite(bis, bw, numRemainingRead);
                }
            }else {
                remainingBytes -= readWrite(bis, bw, remainingBytes);
            }
            bw.close();
            fileParts.add(newFile);
        }
        bis.close();
	}
	
	private static long readWrite(BufferedInputStream bis, BufferedOutputStream bw, long numBytes) throws IOException {
		byte[] buf = new byte[(int) numBytes];
		int val = bis.read(buf);
		if(val != -1) {
			bw.write(buf);
			return val;
		}
		return 0;
	}
	
	private long getPartSize(SplitFile f, long size) {
		switch (f.config.options.getSizeUnit()) {
		case " B": {
			return size;
		}
		case " KB": {
			return size*1000;
		}
		case " MB": {
			return size*1000000;
		}
		case " GB": {
			return size*1000000000;
		}
		default: {
			return size;
		}
		}
	}
	
	/**
	 * Marks the current thread as completed.
	 */
	protected void threadCompleted() {
		sFile.setState("Completed");
		TableQueue.dtm.setValueAt(sFile.getState(), SplitterFrame.splitFiles.indexOf(sFile), 3);
		synchronized (SplitOperations.progressBar) {
			SplitOperations.totalProgress += threadPercentage;
			SplitOperations.progressBar.setValue((int)SplitOperations.totalProgress);
		}
		synchronized (SplitOperations.activeThreads) {
			if(SplitOperations.activeThreads.intValue() == 1) {
				SplitOperations.totalProgress = 100;
				SplitOperations.progressBar.setValue((int)SplitOperations.totalProgress);
				SplitOperations.start.setEnabled(true);	
			}
			SplitOperations.activeThreads--;
		}
	}
	
}

