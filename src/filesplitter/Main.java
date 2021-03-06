package filesplitter;
import java.awt.*;

import javax.swing.*;

import filesplitter.gui.SplitterFrame;

/**
 * The class containing the main function.
 */
public class Main {
	public static Dimension defaultFrameDimension;
	
	/**
	 * The main function of the program.
	 * It creates and shows a {@link SplitterFrame}
	 * 
	 * @param args The arguments passed to the program from the command line (not used in this program).
	 */
	public static void main(String[] args) {
		SplitterFrame f = new SplitterFrame("File Splitter");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		defaultFrameDimension = f.getSize();
	}
}
