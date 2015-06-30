import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;


public class DnaGeneratorTest {

	DnaGenerator dnaGenerator = new DnaGenerator();
	
	@Test
	//Test generateDNA
	public void generateDNAtest() {
		
		int seqLeng = 5;
		boolean richtung = true;
		int typSeqToCheck = 1;
		String dnaleng = dnaGenerator.generateDNA(seqLeng, richtung, typSeqToCheck);
		int seqlengreturn = dnaleng.length();
		// Laenge der Sequenz -> richtung true
		assertEquals(seqLeng,seqlengreturn);
		
		//ACTG in der Sequenz -> richtung true
		assertTrue(dnaleng.matches("[ACGT]{5}"));
		
		richtung = false;
		dnaleng = dnaGenerator.generateDNA(seqLeng, richtung, typSeqToCheck);
		seqlengreturn = dnaleng.length();
		// Laenge der Sequenz -> richtung false
		assertEquals(seqLeng,seqlengreturn);
		
		//ACTG in der Sequenz -> richtung false
		assertTrue(dnaleng.matches("[ACGT]{5}"));
	}
	
	@Test
	public void invertSequenceTest() {
			
			
			String idna = dnaGenerator.invertSequence("ACTG");
			
			// revere Sequenz
			assertEquals(idna,"GTCA");
	}
	@Test
	//DNA -> cDNA
	public void dnaToCDNATest() {
		
		String dna = dnaGenerator.dnaToCDNA();
		
		// Replikation von TAATAGTATGAAGAGGATGACAATAACCAACAGCATCACAAAAAGTACACAACGCGACGGCGTCGCCCACCGCCTCCCGGAGGGGGTGGCTGATGGTGTTGCAGAAGGAGTAGCTCATCGATAATGACCGTTGTCTTATTGGTAGTGCTTCTCCTACTGTTTTTCGCAGCGGCTGCCTCTTCCATTATCACT
		assertEquals(dna,"ATTATCATACTTCTCCTACTGTTATTGGTTGTCGTAGTGTTTTTCATGTGTTGCGCTGCCGCAGCGGGTGGCGGAGGGCCTCCCCCACCGACTACCACAACGTCTTCCTCATCGAGTAGCTATTACTGGCAACAGAATAACCATCACGAAGAGGATGACAAAAAGCGTCGCCGACGGAGAAGGTAATAGTGA");
	}
	
	@Test
	//DNA -> mRNA
	public void dnaToMRNATest() {
		
		String rna = dnaGenerator.dnaToMRNA();
		
		//Transkription von TAATAGTATGAAGAGGATGACAATAACCAACAGCATCACAAAAAGTACACAACGCGACGGCGTCGCCCACCGCCTCCCGGAGGGGGTGGCTGATGGTGTTGCAGAAGGAGTAGCTCATCGATAATGACCGTTGTCTTATTGGTAGTGCTTCTCCTACTGTTTTTCGCAGCGGCTGCCTCTTCCATTATCACT
		assertEquals(rna,"AUUAUCAUACUUCUCCUACUGUUAUUGGUUGUCGUAGUGUUUUUCAUGUGUUGCGCUGCCGCAGCGGGUGGCGGAGGGCCUCCCCCACCGACUACCACAACGUCUUCCUCAUCGAGUAGCUAUUACUGGCAACAGAAUAACCAUCACGAAGAGGAUGACAAAAAGCGUCGCCGACGGAGAAGGUAAUAGUGA");
	}
	
	@Test
	// mRNA-> Protein
	public void dnaToProteinTest() {
		
		String protein = dnaGenerator.dnaToProtein(1);
		
		//Translation von AUUAUCAUACUUCUCCUACUGUUAUUGGUUGUCGUAGUGUUUUUCAUGUGUUGCGCUGCCGCAGCGGGUGGCGGAGGGCCUCCCCCACCGACUACCACAACGUCUUCCUCAUCGAGUAGCUAUUACUGGCAACAGAAUAACCAUCACGAAGAGGAUGACAAAAAGCGUCGCCGACGGAGAAGGUAAUAGUGA
		assertEquals(protein,"IIILLLLLLVVVVFFMCCAAAAGGGGPPPPTTTTSSSSSSYYWQQNNHHEEDDKKRRRRRR***");
	}
	
	@Test
	// check query sequece
	public void checkSequenceDNATest1()
	{
		String querydna = "ATTATCATACTTCTCCTACTGTTATTGGTTGTCGTAGTGTTTTTCATGTGTTGCGCTGCCGCAGCGGGTGGCGGAGGGCCTCCCCCACCGACTACCACAACGTCTTCCTCATCGAGTAGCTATTACTGGCAACAGAATAACCATCACGAAGAGGATGACAAAAAGCGTCGCCGACGGAGAAGGTAATAGTGA";
				
		dnaGenerator.getSeqTyp(1);
		boolean checkdna = dnaGenerator.checkSequence(querydna);
		
		// check dna true
		assertEquals(checkdna, true);
	}
	
	@Test
	// check query sequece
	public void checkSequenceDNATest2()
	{
		String queryDna = "TTATCATACTTCTCCTACTGTTATTGGTTGTCGTAGTGTTTTTCATGTGTTGCGCTGCCGCAGCGGGTGGCGGAGGGCCTCCCCCACCGACTACCACAACGTCTTCCTCATCGAGTAGCTATTACTGGCAACAGAATAACCATCACGAAGAGGATGACAAAAAGCGTCGCCGACGGAGAAGGTAATAGTGA";
				
		dnaGenerator.getSeqTyp(1);
		boolean checkDna = dnaGenerator.checkSequence(queryDna);
		
		// check dna false
		assertEquals(checkDna, false);
	}
	
	@Test
	// check query sequece
	public void checkSequenceRNATest1()
	{
		String queryRna = "AUUAUCAUACUUCUCCUACUGUUAUUGGUUGUCGUAGUGUUUUUCAUGUGUUGCGCUGCCGCAGCGGGUGGCGGAGGGCCUCCCCCACCGACUACCACAACGUCUUCCUCAUCGAGUAGCUAUUACUGGCAACAGAAUAACCAUCACGAAGAGGAUGACAAAAAGCGUCGCCGACGGAGAAGGUAAUAGUGA";
		
		dnaGenerator.getSeqTyp(2);
		boolean checkRna = dnaGenerator.checkSequence(queryRna);
		
		// check rna true
		assertEquals(checkRna, true);
	}
	
	@Test
	// check query sequece
	public void checkSequenceRNATest2()
	{
		String queryRna = "UUAUCAUACUUCUCCUACUGUUAUUGGUUGUCGUAGUGUUUUUCAUGUGUUGCGCUGCCGCAGCGGGUGGCGGAGGGCCUCCCCCACCGACUACCACAACGUCUUCCUCAUCGAGUAGCUAUUACUGGCAACAGAAUAACCAUCACGAAGAGGAUGACAAAAAGCGUCGCCGACGGAGAAGGUAAUAGUGA";
		
		dnaGenerator.getSeqTyp(2);
		boolean checkRna = dnaGenerator.checkSequence(queryRna);
		
		// check rna false
		assertEquals(checkRna, false);
	}
	
	@Test
	// check query sequece
	public void checkSequenceProteinTest1()
	{
		String queryProtein = "IIILLLLLLVVVVFFMCCAAAAGGGGPPPPTTTTSSSSSSYYWQQNNHHEEDDKKRRRRRR***";
		
		dnaGenerator.getSeqTyp(3);
		boolean checkProtein = dnaGenerator.checkSequence(queryProtein);
		
		// check protein true
		assertEquals(checkProtein, true);
	}
	
	@Test
	// check query sequece
	public void checkSequenceProteinTest2()
	{
		String queryProtein = "IILLLLLLVVVVFFMCCAAAAGGGGPPPPTTTTSSSSSSYYWQQNNHHEEDDKKRRRRRR***";
		
		dnaGenerator.getSeqTyp(3);
		boolean checkProtein = dnaGenerator.checkSequence(queryProtein);
		
		// check protein false
		assertEquals(checkProtein, false);
	}
}
