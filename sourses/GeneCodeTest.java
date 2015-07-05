
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import junit.framework.JUnit4TestAdapter;


@RunWith(value=Parameterized.class)
public class GeneCodeTest {
	
	private String alph;
	private int length;
	private String codon;
	private boolean exists;
	GeneCode gc;
	
	@Parameters
	public static Collection<Object[]> getTestParameters(){
		return (Collection<Object[]>) Arrays.asList(new Object[][]{
				{"ABC",2,"AB",true},
				{"ACGT",3,"ACCC",false},
				{"01",8,"00000001",true},
				{"QWERTZUIOPASD",4,"QWQQQ",false}
		});
	}
	
	public GeneCodeTest(String alphabet, int l, String codon,boolean exists) {
		alph = alphabet;
		length =l;
		this.codon = codon;
		this.exists = exists;
	}
	@Test
	public void testConstructor() {
		gc = new GeneCode("",alph,length);
		assertTrue(exists == gc.setCodon(codon, "X"));
		double angle = (new Random()).nextDouble()*Math.PI*2;
		assertEquals(gc.getCodonKeyFromGraph(angle),gc.getCodonKeyFromGraph(angle+Math.PI*2));
	}
	
	@Test(timeout = 3000)
	public void testImgCreation(){
		gc = new GeneCode("Test",alph,length);
		gc.getCodeSun();
	}
	
	@Test(expected=Exception.class)
	public void testReadGeneCodeFromFile() throws IOException{
		GeneCode.ReadCode(null);
	}
	
	@Test
	public void testAntiLooping(){
		gc = new GeneCode("",alph,length);
		assertFalse(gc.setNextCode(gc));
		
		GeneCode tmp1 = new GeneCode();
		GeneCode tmp2 = new GeneCode();
		assertTrue(tmp1.setNextCode(tmp2));
		assertTrue(tmp2.setNextCode(gc));
		assertFalse(gc.setNextCode(tmp1));
	}
	
	

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GeneCodeTest.class);
	}
}
