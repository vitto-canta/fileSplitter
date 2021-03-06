package filesplitter.logic.threadExecution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import filesplitter.logic.config.SplitFile;

/**
 * A thread that splits files into parts with specified size (and encrypts them).
 */
public class EncryptModeThread extends FirstModeThread {
	String key;
	
	/**
	 * Invokes the superclass (FirstModeThread) by passing it the appropriate parameters for the first split mode.
	 * @param s The {@link filesplitter.logic.config.SplitFile}.
	 * @param threadPercentage The percentage of progress the thread will add to the JProgressBar once completed.
	 */
	public EncryptModeThread(SplitFile s, double threadPercentage) {
		super(s, threadPercentage);
		key = s.config.options.getKey();
	}
	
	/**
	 * Starts splitting and encrypting the file.
	 */
	@Override
	public void run() {
		super.run();
		for(File f: super.fileParts) {
			String partName = f.getName().substring(1);
			String cryptName = partName + ".crypt.par";
			File newFile = new File(f.getParent(), cryptName);
			try {
			encryptFile(f, newFile);
			} catch (Exception e) {
				System.out.println("Errore occured while crypting: " + e);
				return;
			}
			f.delete();
		}
		threadCompleted();
	}
	
	private void encryptFile(File inputFile, File outputFile) throws Exception {
		FileInputStream inFile = new FileInputStream(inputFile);
		FileOutputStream outFile = new FileOutputStream(outputFile);

		String password = key;

		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		outFile.write(salt);
		

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,256);
		SecretKey secretKey = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

				
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();

		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		outFile.write(iv);
				
		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();
	
	}
	
}
