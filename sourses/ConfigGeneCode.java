import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/** 
*Diese GUI ist ein Dialog f\u00FCr die Konfiguration und Erstellung eines GeneCode-Objekts
*
*@author Abel Hodelin Hernandez
*@author Timur Horn
*@version 1.0
*/

public class ConfigGeneCode extends JDialog implements ActionListener,MouseListener {

	
	private static final long serialVersionUID = 1L;
	private final JScrollPane contentPanel;
	private JTable table;
	GeneCode gc;
	JButton okButton;
	JTextField gcname_in;
	JButton cancelButton;
	JColorChooser colorChooser;
	JLabel[] colorFields;
	JTextField[] complementsFields;
	JPanel[] letterAddInfoFields;
	JPanel alphabetAddInfoArea;
	


	/**
	 * Create the dialog.
	 */
	public ConfigGeneCode() {
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		table = new JTable(5,2);
		
		table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Codon");
		table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Value");
		//contentPanel.add(table);
		contentPanel = new JScrollPane(table);
		
		contentPanel.setLayout(new ScrollPaneLayout());
		contentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			alphabetAddInfoArea = new JPanel();
			buttonPane.setLayout(new FlowLayout());
			getContentPane().add(alphabetAddInfoArea,BorderLayout.NORTH);
			
			
			JLabel gcname = new JLabel("Name:");
			gcname.setEnabled(false);
			buttonPane.add(gcname);
			gcname_in = new JTextField(20);
			gcname_in.setBounds(0, 0, 300, 20);
			
			buttonPane.add(gcname_in);
			
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				
				buttonPane.add(cancelButton);
			}
			
		
			
			
			createMenuBar();
			
		}
		
		
		//getContentPane().add(table,BorderLayout.CENTER);
		//getContentPane().remove(table);
	}	
	
	boolean setGeneCode(GeneCode gc){
		if(gc == null)
			return false;
		this.gc = gc;
		table.setSize(gc.getSize(), 2);
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		tm.setRowCount(gc.getSize());
		gcname_in.setText(this.gc.getName());
		contentPanel.repaint();
		table.repaint();
		
		Enumeration<String> e = gc.getCodons();
		int i = 0;
		while(e.hasMoreElements()){
			String codon = e.nextElement();
			//table.getModel().
			table.setValueAt(codon, i, 0);
			table.setValueAt(gc.getValue(codon), i, 1);
			i++;
			
		}
		alphabetAddInfoArea.removeAll();
		alphabetAddInfoArea.setLayout(new FlowLayout());
		colorFields = new JLabel[gc.getAlphabet().length];
		complementsFields = new JTextField[gc.getAlphabet().length];
		letterAddInfoFields = new JPanel[gc.getAlphabet().length];
		for(i = 0;i<gc.getAlphabet().length;i++){
			colorFields[i] = new JLabel(gc.getAlphabet()[i]+"",JLabel.CENTER);
			colorFields[i].addMouseListener(this);
			colorFields[i].setBackground(gc.getColor(i));
			colorFields[i].setOpaque(true);
			
			complementsFields[i] = new JTextField(gc.getComplement(gc.getAlphabet()[i] +""));
			
			letterAddInfoFields[i] = new JPanel(new GridLayout(2, 1));
			letterAddInfoFields[i].add(colorFields[i]);
			letterAddInfoFields[i].add(complementsFields[i]);
			
			
			alphabetAddInfoArea.add(letterAddInfoFields[i]);
		}
		alphabetAddInfoArea.revalidate();
		repaint();
		return true;
		
	}
	
	 private void createMenuBar() {

	        JMenuBar menubar = new JMenuBar();
	        

	        JMenu file = new JMenu("File");
	        file.setMnemonic(KeyEvent.VK_F);

	        JMenuItem newGC = new JMenuItem("New");
	        newGC.setMnemonic(KeyEvent.VK_E);
	        newGC.setToolTipText("Load a Genecode file");
	        newGC.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	createNewGeneCode();
	            }
	        });

	        file.add(newGC);
	        
	        JMenuItem load = new JMenuItem("Load");
	        load.setMnemonic(KeyEvent.VK_E);
	        load.setToolTipText("Load a Genecode file");
	        load.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                loadGeneCode(chooseFile());
	            }
	        });

	        file.add(load);
	        
	        
	        JMenuItem saveAs = new JMenuItem("SaveAs...");
	        saveAs.setMnemonic(KeyEvent.VK_E);
	        saveAs.setToolTipText("Write in a file");
	        saveAs.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	readTable();
	                saveCodeFile(chooseFile());
	            }
	        });

	        file.add(saveAs);
	        
	        JMenuItem exit = new JMenuItem("Exit");
	        exit.setMnemonic(KeyEvent.VK_E);
	        exit.setToolTipText("Exit application");
	        exit.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                dispose();
	            }
	        });

	        file.add(exit);
	        
	        menubar.add(file);

	        setJMenuBar(menubar);
	    }
	 
	 private boolean saveCodeFile(String f)
	 {
		 readTable();
		 if(f == null)
			 return false;
		 if(!f.endsWith(GeneCode.FILEEXTENTION))
			 f += GeneCode.FILEEXTENTION;
		 try {
			gc.SaveAs(f);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
			return false;
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 return true;
	 }
	 
	 
	private GeneCode loadGeneCode(String file){
		
		try {
			GeneCode gc = GeneCode.ReadCode(file);
			
			setGeneCode(gc);
			return gc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	private void createNewGeneCode(){
		NewGCDIalog dialog = new NewGCDIalog();
		dialog.setModal(true);
		dialog.setVisible(true);
		
		gc = dialog.getResult();
		setGeneCode(gc);
		
	}
	
	private String chooseFile(){
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		//chooser.setCurrentDirectory(  System.getProperty("user.dir"));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Genecode files", "genecode", "gc");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       return chooser.getSelectedFile().getName();
	    }
	    return null;
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		 String action = ae.getActionCommand();
		
		 	        if (action.equals("OK")) {
		
		 	            //System.out.println("Button pressed!");
		 	        	readTable();
		 	        	dispose();
		 	       
		 	        }
		 	       if (action.equals("Cancel")) {
		 	  		
		 	            //System.out.println("Button pressed!");
		 	        	dispose();
		 	       
		 	        }
		
	}
	
	//read table in the GeneCode Hashtable
	private void readTable(){
		int count = table.getRowCount();
         for(int i = 0; i<count;i++){
         	gc.setCodon(table.getValueAt(i, 0).toString(), table.getValueAt(i, 1).toString());
         }
         gc.setName(gcname_in.getText());
         
         for(int i = 0; i< colorFields.length;i++){
        	 gc.setColor(i, colorFields[i].getBackground());
        	 gc.setComplement(colorFields[i].getText(), complementsFields[i].getText());
         }
         
	}
	public GeneCode getGeneCode(){
		return gc;
	}
	
	private void chooseColor(int index){
		
		Color c = JColorChooser.showDialog(this, "Buchstabenfarbe", gc.getColor(index));
		if(c!=null)
			colorFields[index].setBackground(c);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i = 0;i<colorFields.length;i++){
			if(e.getSource() == colorFields[i]){
				chooseColor(i);
			}
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
}
