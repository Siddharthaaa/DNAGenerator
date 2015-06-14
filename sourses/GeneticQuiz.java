import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;



public class GeneticQuiz extends JFrame{
	
	
	private DnaGenerator dnaGen = new DnaGenerator();
	JTextPane codeText;
	JLabel status;
	String rightTranslation="";
	JComboBox<Integer> l;
	JComboBox<String> direrction;
	JCheckBox reverse;
	JTextPane translText;
	JTextPane translTextSolve;
	Integer[] lengthList = new Integer []{5,10,15,20,25,30};
	String CBListe[] = {"DNA -> cDNA", "DNA -> mRNA",  "DNA -> Protein"};
	
	public GeneticQuiz() {
		
		setSize(500, 400);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		add(panel);
		
		
		JPanel inOutPanel = new JPanel(new GridLayout(2,2,10,10));
		JPanel ButtonPanel1 = new JPanel(new GridLayout(2,1,5,5));
		JPanel ButtonPanel2 = new JPanel(new GridLayout(2,1,5,5));
		JLabel code = new JLabel("Code:");
		JLabel translation = new JLabel("Translation");
		status = new JLabel("            ");
		
		codeText = new JTextPane();
		codeText.setEditable(false);
		JScrollPane codeTextScrollPane = new JScrollPane(codeText);
		translText = new JTextPane();
		JScrollPane translTextScrollPane = new JScrollPane(translText);
		translTextSolve = new JTextPane();
		JScrollPane translTextSolveScrollPane = new JScrollPane(translTextSolve);
		
		JPanel translTextPanel = new JPanel(new GridLayout(2,1));
		translTextPanel.add(translTextScrollPane);
		translTextPanel.add(translTextSolveScrollPane);
		
		
		
		JPanel optionsPanel = new JPanel(new FlowLayout());
		JPanel configPanel = new JPanel(new GridLayout(1, 2));
		
		configPanel.add(optionsPanel);
		
		
		
		GeneCode gc = new GeneCode("TEST","ATCG",3);
		ImagePanel imageCode = new ImagePanel(gc.getCodeSun());
		
		configPanel.add(imageCode);
		
		
		reverse = new JCheckBox("Reverse");
		
		direrction = new JComboBox<String>(CBListe);
		JLabel LabelLength = new JLabel("Length:");
		l = new JComboBox<Integer>(lengthList);
		JLabel LabelTrDirection = new JLabel("Translation direction:");
		
		JButton createCode = new JButton("Create");
		createCode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getStringToTranslate();

			}
		});
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluate();

			}
		});
		
		ButtonPanel1.add(code);
		ButtonPanel1.add(createCode);
		
		ButtonPanel2.add(translation);
		ButtonPanel2.add(submitButton);
		
		
		inOutPanel.add(ButtonPanel1);
		inOutPanel.add(codeTextScrollPane);
		inOutPanel.add(ButtonPanel2);
		inOutPanel.add(translTextPanel);
		
		optionsPanel.add(reverse);
		optionsPanel.add(LabelTrDirection);
		optionsPanel.add(direrction);
		optionsPanel.add(LabelLength);
		optionsPanel.add(l);
		optionsPanel.add(status);
		
		
		
		
		
		panel.add(inOutPanel);
		panel.add(configPanel);
		//panel.add(status);
	}

	protected void getStringToTranslate() {
		// TODO Auto-generated method stub
		
		codeText.setText(dnaGen.generateDNA(l.getItemAt(l.getSelectedIndex()),
						reverse.isSelected(), direrction.getSelectedIndex()+1));
	}
	
	protected void evaluate() {
		// TODO Auto-generated method stub
		if(dnaGen.checkSequence(translText.getText())){
			status.setText("Gratulation!!!");
			status.setForeground(Color.GREEN);
		}
		else{
			status.setText("FALSCH");
			status.setForeground(Color.RED);
			translTextSolve.setText("Lösung");
		}
	}
}
