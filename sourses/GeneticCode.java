/** 
*Speicher die genetischen Code:
*	Standard
*	Vertebrate Mitochondrial
*	...
*@author Abel Hodelin Hernandez
*@author Timur Horn
*@version 1.0
*/
public class GeneticCode
{
	/**
	* Translation(mRNA zu Protein)
	*
	*@param codon: 3 Buchstaben
	*@param code: genetischer Code
	*
	*@return amino: Aminosäure
	*/
	public String RNAToProtein(String codon, int code)
	{
		String amino = "";
		
		switch(codon)
		{
			case "AUU": amino = "I"; //1
					break;
			case "AUC": amino = "I"; //2
					break;
			case "AUA":
				switch(code)
				{
					case 1: amino = "I"; //3
							break;
					case 2: amino = "M"; //3
							break;
				}
					break;
			case "AUG": amino = "M"; //Start
					break;
			case "ACU": amino = "T"; //5
					break;
			case "ACC": amino = "T"; //6
					break;
			case "ACA": amino = "T"; //7
					break;
			case "ACG": amino = "T"; //8
					break;
			case "AAU": amino = "N"; //9
					break;
			case "AAC": amino = "N"; //10
					break;
			case "AAA": amino = "K"; //11
					break;
			case "AAG": amino = "K"; //12
					break;
			case "AGU": amino = "S"; //13
					break;
			case "AGC": amino = "S"; //14
					break;
			case "AGA": 
				switch(code)
				{
					case 1: amino = "R"; //15
							break;
					case 2: amino = "*"; //15
							break;
				}
					break;
			case "AGG":
				switch(code)
				{
					case 1: amino = "R"; //16
							break;
					case 2: amino = "*"; //16
							break;
				}
					break;
			case "GUU": amino = "V"; //17
					break;
			case "GUC": amino = "V"; //18
					break;
			case "GUA": amino = "V"; //19
					break;
			case "GUG": amino = "V"; //20
					break;
			case "GCU": amino = "A"; //21
					break;
			case "GCC": amino = "A"; //22
					break;
			case "GCA": amino = "A"; //23
					break;
			case "GCG": amino = "A"; //24
					break;
			case "GAU": amino = "D"; //25
					break;
			case "GAC": amino = "D"; //26 
					break;
			case "GAA": amino = "E"; //27
					break;
			case "GAG": amino = "E"; //28
					break;
			case "GGU": amino = "G"; //29
					break;
			case "GGC": amino = "G"; //30
					break;
			case "GGA": amino = "G"; //31
					break;
			case "GGG": amino = "G"; //32
					break;
			case "UUU": amino = "F"; //33
					break;
			case "UUC": amino = "F"; //34
					break;
			case "UUA": amino = "L"; //35
					break;
			case "UUG": amino = "L"; //36
					break;
			case "UCU": amino = "S"; //37
					break;
			case "UCC": amino = "S"; //38
					break;
			case "UCA": amino = "S"; //39
					break;
			case "UCG": amino = "S"; //40
					break;
			case "UAU": amino = "Y"; //41
					break;
			case "UAC": amino = "Y"; //42
					break;
			case "UAA": amino = "*"; //43
					break;
			case "UAG": amino = "*"; //44
					break;
			case "UGU": amino = "C"; //45
					break;
			case "UGC": amino = "C"; //46
					break;
			case "UGA":
				switch(code)
				{
					case 1: amino = "*"; //47
							break;
					case 2: amino = "W"; //47
							break;
				}
					break;
			case "UGG": amino = "W"; //48
					break;
			case "CUU": amino = "L"; //49
					break;
			case "CUC": amino = "L"; //50
					break;
			case "CUA": amino = "L"; //51
					break;
			case "CUG": amino = "L"; //52
					break;
			case "CCU": amino = "P"; //53
					break;
			case "CCC": amino = "P"; //54
					break;
			case "CCA": amino = "P"; //55
					break;
			case "CCG": amino = "P"; //56
					break;
			case "CAU": amino = "H"; //57
					break;
			case "CAC": amino = "H"; //58
					break;
			case "CAA": amino = "Q"; //59
					break;
			case "CAG": amino = "Q"; //60
					break;
			case "CGU": amino = "R"; //61
					break;
			case "CGC": amino = "R"; //62
					break;
			case "CGA": amino = "R"; //63
					break;
			case "CGG": amino = "R"; //64
					break;
		}
		//System.out.println(amino);
		return amino;
	}
}
