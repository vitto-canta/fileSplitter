package filesplitter.gui.panels.rows;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A row that allows the user to insert the password if required
 */
public class MergeModeKey extends TemplateRow implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel keyLabel;
	protected static JPasswordField keyField;
	protected static JCheckBox showKey;
	protected static char originalEchoChar;
		
	/**
	 * Arranges the graphic components vertically with {@link BoxLayout}.
	 */
	public MergeModeKey() {
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(Box.createVerticalStrut(10));
		keyLabel = new JLabel("Key: ");
		keyLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		add(keyLabel);
		add(Box.createVerticalStrut(15));
		keyField = new JPasswordField();
		keyField.setEditable(false);
		keyField.setAlignmentX(LEFT_ALIGNMENT);
		
		keyField.setMaximumSize(new Dimension(200, 24));
		keyField.setMinimumSize(new Dimension(200, 24));
		keyField.setPreferredSize(new Dimension(200, 24));
		
		
		add(keyField);
		add(Box.createVerticalStrut(10));
		
		showKey = new JCheckBox("Show key");
		showKey.setEnabled(false);
		showKey.setAlignmentX(LEFT_ALIGNMENT);
		showKey.addActionListener(this);
		
		add(showKey);
		originalEchoChar = keyField.getEchoChar();
		add(Box.createVerticalStrut(100));
		
	
	}
	
	/**
	 * Returns the password entered in the JPasswordField.
	 * @return a string containing the password.
	 */
	public static String getKey() {
		String passText = new String(keyField.getPassword());
		return passText;
	}
	
	/**
	 * Shows an error message if the password entered is wrong.
	 */
	public static void wrongPassword() {
		JOptionPane.showMessageDialog(null,
			    "Wrong password or corrupted files",
			    "Operation failed",
			    JOptionPane.WARNING_MESSAGE);
	}
	/**
	 * Reacts to the JCheckBox click event by showing/hiding the entered password.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(showKey.isSelected()) {
			keyField.setEchoChar((char)0);
		} else {
			keyField.setEchoChar(originalEchoChar);
		}
	}
	
	/**
	 * Resets the JPasswordField.
	 */
	public static void resetKeyField() {
		keyField.setText("");
		keyField.setEchoChar(originalEchoChar);
		showKey.setSelected(false);
	}
}

