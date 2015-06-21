import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JDialog;


public class TestGeneCode {

	public static void main(String[] str) throws ClassNotFoundException, IOException{
		
		
		GeneCode gc = null;  ;// = new GeneCode();
		
		gc = new GeneCode();
		try {
			gc.SaveAs("TEST.genecode");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		gc = GeneCode.ReadCode("TEST.genecode");
		
		
		//F�r DNACreator wichtige funktionen
		gc.getName(); // Sring Bezeichnung (zb Mitochondrium)
		gc.getCodonLength(); // int wortl�nge, wie lang ein codon ist
		gc.getAlphabet(); //char[] die verf�gbaren buchstaben. wichtig f�r die generierung von zufallstrings
		
		
		
		//Dialog zum Konfigurieren einer Datei
		ConfigGeneCode dialog = new ConfigGeneCode();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		dialog.setGeneCode(gc);
		
		dialog.setModal(true);
		dialog.setVisible(true);
		
		GeneticQuiz gq = new GeneticQuiz();
		gq.setVisible(true);

		try {
			gc = GeneCode.ReadCode("Default");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	
		//System.out.println(gc.toString());
		
		
	}
}
