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
			
			
			String dna = dnaGenerator.invertSequence("ACTG");
			
			// revere Sequenz
			assertEquals(dna,"GTCA");
	}
	@Test
	//DNA -> cDNA
	public void dnaToCDNATest() {
		
		String dna = dnaGenerator.dnaToCDNA();
		
		// Replikation von ACTG
		assertEquals(dna,"TGAC");
	}
	
	@Test
	//DNA -> mRNA
	public void dnaToMRNATest() {
		
		String dna = dnaGenerator.dnaToMRNA();
		
		//Transkription von ACTG
		assertEquals(dna,"UGAC");
	}
}
