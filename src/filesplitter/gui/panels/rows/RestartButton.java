package filesplitter.gui.panels.rows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * JButton to set JprogressBars to 0%
 */
public class RestartButton extends TemplateRow implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	protected static JButton restart;
	
	public RestartButton() {
		super();
		restart = new JButton("RESTART");
		restart.addActionListener(this);
		add(restart);
	}
	
	/**
	 * Action performed when click the button 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("RESTART")) {
			MergeModeStart.progressBar.setValue(0);
			SplitOperations.progressBar.setValue(0);
		}
		
	}   

}
