import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
/** 
*Ein Hilfsfenster für die Erzeugung eines neuen GeneCodes
*Enthält nur die nötigsten felder
*@author Abel Hodelin Hernandez
*@author Timur Horn
*@version 1.0
*/

public class NewGCDIalog extends JDialog {
	
	GeneCode gc;
	JTextField at;
	JTextField lt;
	JTextField initVal;

	public NewGCDIalog(){
		//gc = new GeneCode();
		createLabel();
	}
	
	private void createLabel(){
		
		this.setLayout(new GridLayout(4, 2));
		
		JLabel alphabet = new JLabel("Alphabet:");
		JLabel wl = new JLabel("Word length:");
		JLabel init = new JLabel("Init values:");
		 at = new JTextField("ABC");
		 lt = new JTextField("3");
		 initVal = new JTextField("*");
		
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
		add(init);
		add(initVal);
		add(okButton);
		
		setSize(200,100);
	}
	
	private void createGC(){
		String test = lt.getText();
		int l = Integer.parseInt(lt.getText());
		initVal.getText();
		gc = new GeneCode("New", at.getText(), l,initVal.getText());
	}
	
	public GeneCode getResult(){
		return gc;
	}
}
