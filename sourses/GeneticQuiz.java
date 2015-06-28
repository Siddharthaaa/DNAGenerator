import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;



public class GeneticQuiz extends JFrame implements MouseListener,ItemListener{
	
	
	private DnaGenerator dnaGen = new DnaGenerator();
	JTextPane codeText;
	JLabel status;
	String rightTranslation="";
	JComboBox<Integer> l;
	JComboBox<String> codeSelectionCB;
	JCheckBox complCB;
	JTextPane translText;
	JTextPane translTextSolve;
	JButton configGC;
	Integer[] lengthList = new Integer []{5,10,15,20,25,30};
	
	
	ArrayList<GeneCode> gencodes;
	
	JTextField codonInfo;
	ImagePanel imageCodeSun;
	//aktueller Gene code
	GeneCode gc;
	
	public GeneticQuiz(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		gencodes= new ArrayList<GeneCode>();
		createStandardCodes();
		readCodeFiles(new File(".\\"));
		
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
		
		
		
		
		imageCodeSun = new ImagePanel();
		imageCodeSun.addMouseListener(this);
		
		configPanel.add(imageCodeSun);
		
		
		complCB = new JCheckBox("complement Strand");
		codonInfo = new JTextField(20);
		codonInfo.setEditable(false);
		
		codeSelectionCB = new JComboBox<String>();
		codeSelectionCB.addItemListener(this);
		
		fillComboBox();
		
		JLabel LabelLength = new JLabel("Length:");
		l = new JComboBox<Integer>(lengthList);
		JLabel LabelTrDirection = new JLabel("Translation direction:");
		
		JButton createCode = new JButton("Create");
		createCode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createStringToTranslate();

			}
		});
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluate();

			}
		});
		
		configGC =new JButton("Config");
		configGC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				configurateGeneCode();
				
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
		
		optionsPanel.add(complCB);
		optionsPanel.add(LabelTrDirection);
		optionsPanel.add(codeSelectionCB);
		optionsPanel.add(LabelLength);
		optionsPanel.add(l);
		optionsPanel.add(status);
		optionsPanel.add(codonInfo);
		optionsPanel.add(configGC);
		
		
		
		
		
		panel.add(inOutPanel);
		panel.add(configPanel);
		//panel.add(status);
	}

	private void fillComboBox() {
		for(int i =0;i<gencodes.size();i++){
			codeSelectionCB.addItem(gencodes.get(i).getName());
		}
		
	}

	protected void configurateGeneCode() {
		ConfigGeneCode dialog = new ConfigGeneCode();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setGeneCode(gc);
		dialog.setModal(true);
		dialog.setVisible(true);
		if(gc != dialog.getGeneCode()){
			addGeneCode(dialog.getGeneCode());
		}
		imageCodeSun.setImage(gc.getCodeSun());
		imageCodeSun.repaint();
		codeSelectionCB.repaint();
		
	}

	protected void createStringToTranslate() {
		// TODO Auto-generated method stub
		
		codeText.setText(dnaGen.generateDNA(l.getItemAt(l.getSelectedIndex()),
						complCB.isSelected(), codeSelectionCB.getSelectedIndex()+1));
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
			translTextSolve.setText(dnaGen.getSequence());
		}
	}
	
	private void createStandardCodes(){
		
		
		GeneCode g = new GeneCode("DNA->cDNA", "ATCG", 1);
		g.setCodon("A", "T");
		g.setCodon("T", "A");
		g.setCodon("G", "C");
		g.setCodon("C", "G");
		gencodes.add(g);
		
		g = new GeneCode("DNA->RNA","ATCG",1);
		g.setCodon("A", "U");
		g.setCodon("T", "A");
		g.setCodon("G", "C");
		g.setCodon("C", "G");
		gencodes.add(g);
		
		gencodes.add(new GeneCode());
	}
	
	private void readCodeFiles(File dir){
		File[] files = dir.listFiles();
		if(files!=null){
			for(int i =0;i<files.length;i++){
				if(files[i].getName().endsWith(GeneCode.FILEEXTENTION)){
					try {
						gencodes.add(GeneCode.ReadCode(files[i].getName()));
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(imageCodeSun)){
			double xm = imageCodeSun.getWidth()/2;
			double ym = imageCodeSun.getHeight()/2;
			double x = (e.getX()-xm);
			double y = (e.getY()-ym)*xm/ym;
			double angle = Math.atan(y/x);
			if(x< 0){
				angle += Math.PI;//// NICHT FERTIG
			}
			if(x>0 && y<0){
				angle += Math.PI*2;
			}
			String key = gc.getCodonKeyFromGraph(angle);
			codonInfo.setText(key + "=>" + gc.getValue(key));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addGeneCode(GeneCode geneCode) {
		gencodes.add(geneCode);
		codeSelectionCB.addItem(geneCode.getName());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		gc = gencodes.get(codeSelectionCB.getSelectedIndex());
		imageCodeSun.setImage(gc.getCodeSun());
		imageCodeSun.repaint();

		
	}
}
