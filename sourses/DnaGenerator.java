import java.util.Random;

/** 
*Ein Programm f\u00FCr \u00DCben der genetiaschen Verfahren:

*Replication (DNA zu cDNA), Transcription (DNA zu mRNA), Translation(mRNA zu Protein).
*
*@author Abel Hodelin Hernandez
*@author Timur Horn
*@version 1.0
*/
public class DnaGenerator
{
	
	private Random random = new Random();
	public String dnaGeneral = "TAATAGTATGAAGAGGATGACAATAACCAACAGCATCACAAAAAGTACACAACGCGACGGCGTCGCCCACCGCCTCCCGGAGGGGGTGGCTGATGGTGTTGCAGAAGGAGTAGCTCATCGATAATGACCGTTGTCTTATTGGTAGTGCTTCTCCTACTGTTTTTCGCAGCGGCTGCCTCTTCCATTATCACT"; // generierte DNA-Sequenz 
	public String cdnaGeneral = "ATTATCATACTTCTCCTACTGTTATTGGTTGTCGTAGTGTTTTTCATGTGTTGCGCTGCCGCAGCGGGTGGCGGAGGGCCTCCCCCACCGACTACCACAACGTCTTCCTCATCGAGTAGCTATTACTGGCAACAGAATAACCATCACGAAGAGGATGACAAAAAGCGTCGCCGACGGAGAAGGTAATAGTGA"; // cDNA
	public String rnaGeneral = "AUUAUCAUACUUCUCCUACUGUUAUUGGUUGUCGUAGUGUUUUUCAUGUGUUGCGCUGCCGCAGCGGGUGGCGGAGGGCCUCCCCCACCGACUACCACAACGUCUUCCUCAUCGAGUAGCUAUUACUGGCAACAGAAUAACCAUCACGAAGAGGAUGACAAAAAGCGUCGCCGACGGAGAAGGUAAUAGUGA"; // mRNA
	public String proteinGeneral = "IIILLLLLLVVVVFFMCCAAAAGGGGPPPPTTTTSSSSSSYYWQQNNHHEEDDKKRRRRRR***"; // Protein
	private boolean senseGeneral = true; // Richtung zum lesen
	private int seqTypGeneral = 1; // Verfahren
	private int geneCodeGeneral = 1; // genetiche Code
	
	
	//Fuer die neue Schnittstelle
	private GeneCode gCode; //actueller Gencode
	private String crSeq=""; // erzeugte Sequenz
	private String translChain;// Uebersetzung der erzeugten Sequenz 
	private String translEnd =""; //die Endsequenz der Uebersetzung
	
	/**
	* Generiert die DNA-Sequenz
	*@param seqLeng: L\u00E4nge der zu erzeugender DNA-Sequenz (Nukleotide oder Aminos\u00E4ure).
	*@param invert: Richtung der zu erzeugender DNA-Sequenz.
	*@param typSeqToCheck: Welche Verfahren wird simuliert.
	*
	*@return dnaGeneral: DNA-Sequenz
	*/
	public String generateDNA(int seqLeng ,boolean invert, int typSeqToCheck)
	{
		String dnaSequenz = "";
		seqTypGeneral = typSeqToCheck;
		senseGeneral = invert;
		
		/* Translation (DNA zu Protein) */
		if (typSeqToCheck == 3)
			seqLeng = seqLeng * 3;
		
		/* Generierung der DNA-Sequenz*/
		for(int i = 0; i < seqLeng; i ++)
		{
			int nucleotideRandomizer = 1 + random.nextInt(4);
			String nucleotide = ""; // Nukleotide
			switch(nucleotideRandomizer)
			{
				case 1: nucleotide = "A";
						break;
				case 2: nucleotide = "C";
						break;
				case 3: nucleotide = "G";
						break;
				case 4: nucleotide = "T";
						break;
			}
			dnaSequenz += nucleotide;
		}
		
		dnaGeneral = dnaSequenz;
		return dnaGeneral;
	}

	/**
	* Invertiert die XNA-Sequenz
	*
	*@param dna: XNA-Sequenz um zu invertieren
	*@return dna: umgekehrte XNA-Sequenz
	*/
	public String invertSequence(String dna)
	{
		dna = new StringBuffer(dna).reverse().toString();
		return dna;
	}
	
	public void setCode(int geneCode)
	{
		geneCodeGeneral = geneCode;
	}
	
	public void getSeqTyp(int typ)
	{
		seqTypGeneral = typ;
	}
	
	/**
	* Replication (DNA zu cDNA)
	*
	*@return cdnaGeneral: cDNA-Sequenz
	*/
	public String dnaToCDNA()
	{
		String cDNA = "";
		
		if(!senseGeneral)
			dnaGeneral = invertSequence(dnaGeneral);

			/* Replication (DNA zu cDNA)*/
			for(char c : dnaGeneral.toCharArray())
			{
				if(c == 'A')
					cDNA += "T";
				if(c == 'C')
					cDNA += "G";
				if(c == 'G')
					cDNA += "C";
				if(c == 'T')
					cDNA += "A";
			}
			cdnaGeneral = cDNA;
		return cdnaGeneral;
	}
	
	/**
	* Transcription (DNA zu mRNA)
	*
	*@return rnaGeneral: mRNA-Sequenz
	*/
	public String dnaToMRNA()
	{
		String mRNA = "";
		
		if(!senseGeneral)
			dnaGeneral = invertSequence(dnaGeneral);

		/* Transcription (DNA zu mRNA)*/
		for(char c : dnaGeneral.toCharArray())
		{
			if(c == 'A')
				mRNA += "U";
			if(c == 'C')
				mRNA += "G";
			if(c == 'G')
				mRNA += "C";
			if(c == 'T')
				mRNA += "A";
		}
		rnaGeneral = mRNA;
		return rnaGeneral;
	}
	
	/**
	* Translation (mRNA zu Protein)
	*
	*@param code: genetischer Code
	*
	*@return proteinGeneral: Protein-Sequenz
	*/
	public String dnaToProtein(int code)
	{
		String protein = "";
		String codon = ""; // 3 Buchstabe-Code
		String mRNA = dnaToMRNA();
		GeneticCode geneticCode = new GeneticCode(); // genetischer Code
		
		/* Translation(mRNA zu Protein)*/
		for(char c : mRNA.toCharArray())
		{
			codon += c; // Erzeugen der Codonen
			
			if(codon.length() == 3)
			{
				protein += geneticCode.RNAToProtein(codon, code);
				
				codon = "";
			}	
		}
		proteinGeneral = protein;
		return proteinGeneral;
	}
	
	/**
	*Analysiert das Ergebnis
	*Vergleich die erzeugte X-Sequenze mit der Sequenz des Benutzers
	*@param querySequence: Sequenz des Benutzers
	*
	*@return true wenn beide Sequenze gleich sind, sonst false
	*/
	public boolean checkSequence(String querySequence)
	{
		String generatedSequence = "";
	
		/* Sequenz zu analysieren*/
		switch(seqTypGeneral)
		{
			case 1: generatedSequence = dnaToCDNA();
					break;
			case 2: generatedSequence = dnaToMRNA();
					break;
			case 3: generatedSequence = dnaToProtein(geneCodeGeneral);
					break;
		}
		if(!generatedSequence.equalsIgnoreCase(querySequence))
		{
			return false;
		}
		return true;
	}
	/**
	*Zeigt das Ergebnis (Sequenze)
	*
	*@return seq: X-Sequenze
	*/
	public String getSequence()
	{
		int seqTyp = seqTypGeneral;
		String seq = "";
		
		switch(seqTyp)
		{
			case 1: seq = cdnaGeneral;
				break;
			case 2: seq = rnaGeneral;
				break;
			case 3: seq = rnaGeneral + "\n" + proteinGeneral;
				break;
		}
		return seq;
	}
	
	/**
	*Erzeugt eine Zuf\u00E4llige Sequenz anhand vom geneCode
	*@param length: L\u00E4nge der sequenz nach dem \u00DCbersetzen der zu erzeugenden Sequenz
	*@param geneCode: der f\u00FCr die Erzeugung verwendete Code
	*@return zufaellige Sequenz
	*/
	public String generateDNA_new(int length, GeneCode geneCode) {
		if(geneCode == null)
			return "";
		gCode = geneCode;
		//length of a codon 
		int l = length;
		//calculate length
		GeneCode g = geneCode;
		//Vekettung von Codes beachten
		while(g!= null){
			l *= g.getCodonLength();
			g= g.getNextCode();
		}
		
		Random r = new Random(System.currentTimeMillis());
		crSeq="";
		char[] alphabet = geneCode.getAlphabet();
		//zufallsString aus dem Alphabet erzeugen
		for(int i = 0; i<l;i++){
			crSeq += alphabet[r.nextInt(alphabet.length)];
		}
		return crSeq;
	}
	/**
	*Pr\u00FCft, ob die eingabe mit der \u00DCbersetzung \u00FCbereinstimmt
	*@param querySequence: vom User vorgeschlagene Seq
	*@param reverse: ob es sich um eine invertierte Sequens handelt
	*@return true wenn die \u00DCbersetzung richtig ist
	*/
	
	public boolean checkSequence_new(String querySequence, boolean reverse) {
		
		
		if(reverse)
			translChain = translateCode(invertSequence(crSeq), gCode);
		else
			translChain = translateCode(crSeq,gCode);
		
		return translEnd.equalsIgnoreCase(querySequence);
		
	}
	/**
	*Pr\u00FCft, ob die eingabe mit der \u00DCbersetzung \u00FCbereinstimmt
	*@return Alle entstandenen Translationen mit "\n" getrennt
	*/

	public String getSequence_new() {
		return translChain;
	}
	
	
	/**
	*Eine rekursive Funktion, die eine Sequenz anhand eines Codes \u00FCbersetzt
	*@param seq: die zu \u00FCbersetzende Sequenz
	*@param gc: der zu verwendende Code
	*@return Alle entstandenen Translationen mit "\n" getrennt
	*/
	private String translateCode(String seq, GeneCode gc){
		if(gc==null)
			return "";
		String trans = "";
		int frame = gc.getCodonLength();
		for(int i = 0; i<seq.length();i+=frame){
			trans += gc.getValue(seq.substring(i, i+frame));
		}
		translEnd = trans;
		if(gc.getNextCode() != null){
			//Rekursion
			trans += "\n" + translateCode(trans, gc.getNextCode());;
		}
		return trans;
	}

}
