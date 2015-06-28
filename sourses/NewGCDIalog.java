import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class NewGCDIalog extends JDialog {
	
	GeneCode gc;
	JTextField at;
	JTextField lt;

	public NewGCDIalog(){
		//gc = new GeneCode();
		createLabel();
	}
	
	private void createLabel(){
		
		this.setLayout(new GridLayout(3, 2));
		
		JLabel alphabet = new JLabel("Alphabet:");
		JLabel wl = new JLabel("Word length:");
		 at = new JTextField("ABC");
		 lt = new JTextField("3");
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createGC();
				setVisible(false);
				
			}
		});
		
		
		
		add(alphabet);
		add(at);
		add(wl);
		add(lt);
		add(okButton);
		
		setSize(200,100);
	}
	
	private void createGC(){
		String test = lt.getText();
		int l = Integer.parseInt(lt.getText());
		gc = new GeneCode("New", at.getText(), l);
	}
	
	public GeneCode getResult(){
		return gc;
	}
}
