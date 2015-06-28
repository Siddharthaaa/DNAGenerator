import java.io.IOException;

import javax.swing.JDialog;


public class TestGeneCode {

	public static void main(String[] str) throws ClassNotFoundException, IOException{
		
		
		GeneCode gc = null;  ;// = new GeneCode();
		
		gc = new GeneCode();
		
		
		//F�r DNACreator wichtige funktionen
		gc.getName(); // Sring Bezeichnung (zb Mitochondrium)
		gc.getCodonLength(); // int wortl�nge, wie lang ein codon ist
		gc.getAlphabet(); //char[] die verf�gbaren buchstaben. wichtig f�r die generierung von zufallstrings
		
		
		
		/*//Dialog zum Konfigurieren einer Datei
		ConfigGeneCode dialog = new ConfigGeneCode();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		dialog.setGeneCode(gc);
		
		dialog.setModal(true);
		dialog.setVisible(true);
		*/
		GeneticQuiz gq = new GeneticQuiz();
		gq.setVisible(true);

		
		
		
	
		//System.out.println(gc.toString());
		
		
	}
}
