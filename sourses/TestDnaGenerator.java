//TestDnaGenerator

import java.util.Scanner;

public class TestDnaGenerator
{
	public static void main(String args[])
	{
		Scanner datenIntroMain = new Scanner(System.in);
		
		DnaGenerator dnaGenerator = new DnaGenerator();
		
		System.out.println("DNA to cDNA: 1 ");
		System.out.println("DNA to mRNA: 2 ");
		System.out.println("DNA to Protein: 3");
		int sequenzTypToTest = datenIntroMain.nextInt(); //Typ der Sequenz zu Testen
		
		int control = 0; // Kontroliert in ein Whileschleife sequenzTypToTest
		
		if(sequenzTypToTest < 1 || sequenzTypToTest > 3)
		{
			//Aus der App nach der 3. falsche Eingabe von sequenzTypToTest
			while(control < 2 && (sequenzTypToTest < 1 || sequenzTypToTest > 3) )
			{
				System.out.println("DNA to cDNA: 1 "); // DNA zu coding DNA
				System.out.println("DNA to mRNA: 2 ");
				System.out.println("DNA to Protein: 3");
				sequenzTypToTest = datenIntroMain.nextInt();
				
				control ++;
			}
		}
		else
		{
			if(sequenzTypToTest == 3)
				System.out.print("How many aminoacids (3 - 20): ");
			else
				System.out.print("How many nucleotides (3 - 20): ");
			
			int numberChar = datenIntroMain.nextInt(); //Anzahl an Buchstaben
			
			control = 0;
		
			if(numberChar < 3 || numberChar >20)
			{
				//Aus der App nach der 3. falsche Eingabe von sequenzTypToTest
				while(control < 2 && numberChar < 3 )
				{
					if(sequenzTypToTest == 3)
						System.out.print("How many aminoacids (3 - 20): ");
					else
						System.out.print("How many nucleotides (3 - 20): ");
					numberChar = datenIntroMain.nextInt();
					
					control ++;
				}
			}
			else
			{
				System.out.println("Sense of the sequence.");
				System.out.println("Forward: true");
				System.out.println("Backward: false");
				boolean richtung = datenIntroMain.nextBoolean();
				boolean test = true;
				
				if(sequenzTypToTest == 3)
				{
					System.out.println("Genetic Code:");
					System.out.println("Standard: 1");
					System.out.println("Vertebrate Mitochondrial: 2");
					//System.out.println(".....");
					int code = datenIntroMain.nextInt(); // genetische Code
					dnaGenerator.setCode(code);
				}	

				dnaGenerator.generateDNA(numberChar, richtung, sequenzTypToTest);
				
				System.out.println("Generated DNA: ");
				System.out.println(dnaGenerator.dnaGeneral);
				
				System.out.println("Query sequence: ");
				String query = datenIntroMain.next();
				
				switch(sequenzTypToTest)
				{
					case 1: test = dnaGenerator.checkSequence(query);
							break;
					case 2: test = dnaGenerator.checkSequence(query);
							break;
					case 3: test = dnaGenerator.checkSequence(query);
							break;	
				}
				
				if(test)
					System.out.println("OK!");
				else
					System.out.println("Wrong!");
				
				String seqEnde = dnaGenerator.getSequence();
				
				System.out.println(seqEnde);
			}
		}
		if(control >=2)
			System.out.println("Imput Error!");
	}
}
