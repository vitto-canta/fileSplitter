package filesplitter.logic.threadExecution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import filesplitter.gui.SplitterFrame;
import filesplitter.gui.panels.rows.MergeModeKey;
import filesplitter.gui.panels.rows.MergeModeStart;

/**
 * A thread that decrypts and merges multiple parts to rebuild a file.
 */
public class DecryptModeThread extends MergeNormalModeThread {
	String key;
	
	/**
	 * Invokes the superclass (NormalModeThread) by passing it the name of the file that will be reassembled.
	 * @param fileName The name of the file that will be reassembled.
	 */
	public DecryptModeThread(String fileName) {
		super(fileName);
		key = MergeModeKey.getKey();
		MergeModeKey.resetKeyField();
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
			
			for(File part: SplitterFrame.parts) {
				if(!decryptFile(part, out)) {
					f.delete();
					MergeModeKey.wrongPassword();
					totalPercentage = 0;
					MergeModeStart.progressBar.setValue((int)totalPercentage);
					MergeModeStart.start.setEnabled(true);
					return;
				}
				totalPercentage+=partPercentage;
				MergeModeStart.progressBar.setValue((int)totalPercentage);
				part.delete();
				
			}
			out.flush();
			out.close();
			
			totalPercentage = 100;
			MergeModeStart.progressBar.setValue((int)totalPercentage);
			MergeModeStart.start.setEnabled(true);
		} catch (IOException e) {
			System.out.println("I/O Error");
		}
		MergeModeStart.threadCompleted(fileName);
	}
	
	private boolean decryptFile(File inputFile, FileOutputStream fos) {
		try {
		FileInputStream fis = new FileInputStream(inputFile);
		
		
		String password = key;

		byte[] salt = new byte[8];
		fis.read(salt);

		
		byte[] iv = new byte[16];
		fis.read(iv);
		

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

		byte[] in = new byte[64];
		int read;
		while ((read = fis.read(in)) != -1) {
			byte[] output = cipher.update(in, 0, read);
			if (output != null)
				fos.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			fos.write(output);
		fis.close();
		fos.flush();
		
		} catch (BadPaddingException e1) {
			return false;
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
}
