import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import javax.lang.model.element.Element;

import org.omg.CORBA.CharSeqHolder;


public class GeneCode implements Serializable {
	
	public final static String FILEEXTENTION = ".genecode";
	
	private String name;
	private char[] alphabet;
	private int wordLength;
	private BufferedImage img;
	private boolean imgTooBig;
	private int smallestCell;
	
	private Hashtable<String, String> codons;
	private Hashtable<String, String> complements;
	//
	private GeneCode nextGeneCode;
	
	private boolean reverseComplement = false;
	
	//colors for created image
	private Color [] colors;
	
	
	public GeneCode(){
		this("Standard","ACGU",3);
		setCodon("AUU","I"); //1
		setCodon("AUC","I"); //2
		setCodon("AUA","I");
		setCodon("AUG","M"); //Start
		setCodon("ACU","T"); //5
		setCodon("ACC","T"); //6
		setCodon("ACA","T"); //7
		setCodon("ACG","T"); //8
		setCodon("AAU","N"); //9
		setCodon("AAC","N"); //10
		setCodon("AAA","K"); //11
		setCodon("AAG","K"); //12
		setCodon("AGU","S"); //13
		setCodon("AGC","S"); //14
		setCodon("GUU","V"); //17
		setCodon("GUC","V"); //18
		setCodon("GUA","V"); //19
		setCodon("GUG","V"); //20
		setCodon("GCU","A"); //21
		setCodon("GCC","A"); //22
		setCodon("GCA","A"); //23
		setCodon("GCG","A"); //24
		setCodon("GAU","D"); //25
		setCodon("GAC","D"); //26 
		setCodon("GAA","E"); //27
		setCodon("GAG","E"); //28
		setCodon("GGU","G"); //29
		setCodon("GGC","G"); //30
		setCodon("GGA","G"); //31
		setCodon("GGG","G"); //32
		setCodon("UUU","F"); //33
		setCodon("UUC","F"); //34
		setCodon("UUA","L"); //35
		setCodon("UUG","L"); //36
		setCodon("UCU","S"); //37
		setCodon("UCC","S"); //38
		setCodon("UCA","S"); //39
		setCodon("UCG","S"); //40
		setCodon("UAU","Y"); //41
		setCodon("UAC","Y"); //42
		setCodon("UGU","C"); //45
		setCodon("UGC","C"); //46		 
		setCodon("UGG","W"); //48		 
		setCodon("CUU","L"); //49
		setCodon("CUC","L"); //50
		setCodon("CUA","L"); //51
		setCodon("CUG","L"); //52
		setCodon("CCU","P"); //53
		setCodon("CCC","P"); //54
		setCodon("CCA","P"); //55
		setCodon("CCG","P"); //56
		setCodon("CAU","H"); //57
		setCodon("CAC","H"); //58
		setCodon("CAA","Q"); //59
		setCodon("CAG","Q"); //60
		setCodon("CGU","R"); //61
		setCodon("CGC","R"); //62
		setCodon("CGA","R"); //63
		setCodon("CGG","R"); //64
	}
	
	public GeneCode(String name, String alph, int l){
		
		codons = new Hashtable<String, String>();
		complements = new Hashtable<String, String>();
		this.alphabet = alph.toCharArray();
		alph="";
		for(char c: alphabet){
			if(!alph.contains(c + "")){
				alph+=c;
				complements.put(c+"", c+"");
			}
		}
		this.alphabet = alph.toCharArray();
		
		
		
		this.name = name;
		wordLength = l;
		

		colors =  new Color[alphabet.length];
		for(int i = 0;i<colors.length;i++){
			float f = (float) (0.5f+0.5*((float)i/colors.length));
			colors[i] = new Color(f,f,f);
		}

		
		
		
		init("",0);
	}
	
	public GeneCode(GeneCode g){
		
		colors = g.colors.clone();
		alphabet = g.alphabet.clone();
		codons = (Hashtable<String, String>) g.codons.clone();
		name = g.name;
		wordLength = g.wordLength;
		complements = (Hashtable<String, String>) g.complements.clone();
		
	}
	
	//fill Hashtable rekursive
	private void init(String prefix, int position ){
		if(wordLength>position){
			for(int i=0;i<alphabet.length;i++){
				init(prefix + alphabet[i],position+1);
			}
		}
		else{
			codons.put(prefix, "*");
		}
	}
	
	public boolean setCodon(String name, String value){
		
		if(codons.containsKey(name)){
			img =null;
			codons.put(name, value);
			return true;
		}
		return false;
	}
	public boolean setComplement(String name, String value){
		
		if(complements.containsKey(name)){
			img =null;
			complements.put(name, value);
			return true;
		}
		return false;
	}
	public String getComplement(String letter) {
		return complements.get(letter);
	}
	
	
	
	boolean SaveAs(String FileName) throws IOException{
		
		 OutputStream file = new FileOutputStream(FileName);
	     OutputStream buffer = new BufferedOutputStream(file);
	     ObjectOutput output = new ObjectOutputStream(buffer);
		 output.writeObject(this);
		 output.close();
		return true;
	}
	
	static GeneCode ReadCode(String fileName) throws IOException, ClassNotFoundException{
		
		InputStream file = new FileInputStream(fileName);
	    InputStream buffer = new BufferedInputStream(file);
	    ObjectInput input = new ObjectInputStream (buffer);
	
		 GeneCode result = (GeneCode) input.readObject();
		 input.close();
		return result;
	}
	
	public String getValue(String codon){
		
		return codons.get(codon);
	}
	
	public int getSize(){
		return codons.size();
	}
	public Enumeration<String> getElements(){
		return codons.keys();
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append( "Name:" + name + "\n");
		result.append("Alphabet: " + new String (alphabet) + "\n");
		result.append("Word length: " + wordLength + "\n");
		Enumeration<String> e = codons.keys();
		while(e.hasMoreElements()){
			String codon = e.nextElement();
			result.append( codon + " -> " + codons.get(codon) + "\n");
		}
		return result.toString();
	}
	
	public GeneCode clone(){
		GeneCode gc = new GeneCode(this);
		
		return gc;
	}

	public String getName() {
		return name;
	}
	
	public char[] getAlphabet(){
		return alphabet.clone();
	}

	public void setName(String text) {
		name = text;
		// TODO Auto-generated method stub
		
	}

	public int getCodonLength() {
		return wordLength;
		
	}
	
	public String getCodonKeyFromGraph(double angle){
		angle %= Math.PI*2;
		String key = "";
		double angleStep = Math.PI*2;
		for(int i =0;i<wordLength;i++){
			angleStep /= alphabet.length;
			int index = (int)(angle/angleStep);
			key += alphabet[index];
			angle %= angleStep;
		}
		return key;
	}
	
	public BufferedImage getCodeSun(){
		
		if(img !=null)
			return img;
		
		smallestCell = 50;
		int BoundDistance = 50;
		int w = smallestCell;
		int h = 0;
		
		//Abmessungen ausrechnen
		//diese haengen vom alphabet 
		for(int i = 0;i<wordLength;i++){
			h += 2*w;
			w *= alphabet.length/(1+(float)(i+1)/wordLength);
		}
		h+=2*BoundDistance;
		w = h;
		System.out.println("Bildbreite: "+ w);
		
		if(w>5000){
			imgTooBig = true;
			img = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
			img.getGraphics().setColor(Color.WHITE);
			img.getGraphics().drawString("Bild ist zu Groß", 10, 50);
			return img;
		}
		
		int mx= w/2;
		int my= h/2;
		
		int r0,r1;
		r1= (w-2*BoundDistance)/2;
		r0 = r1-smallestCell;
		
		int segmente = (int)Math.pow(alphabet.length, wordLength);
		
		img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2D = (Graphics2D) img.getGraphics();
		
		g2D.setColor(Color.BLACK);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                  RenderingHints.VALUE_ANTIALIAS_ON );
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY );
		
		double arcStep =0;
		for(int i= wordLength;i > 0;i--){
			
			g2D.setColor(Color.black);
			g2D.setStroke(new BasicStroke((r1-r0)/30));
			g2D.drawOval(mx-r1, my-r1, r1*2, r1*2);
			
			arcStep = Math.PI * 2f/segmente;
			double arcDelta = 360./segmente;
			
			for(int j =0; j < segmente;j++){
				double cos = Math.cos(j*arcStep);
				double sin = Math.sin(j*arcStep);
				
				g2D.setColor(colors[j%alphabet.length]);

				g2D.fill(new Arc2D.Double(mx-r1,my-r1,r1*2,r1*2,-j*arcDelta,-arcDelta,Arc2D.PIE));
				g2D.setColor(Color.black);
				
				g2D.drawLine((int)(mx + r0*cos), (int)(my+ r0*sin),(int)(mx + r1*cos), (int)(my+ r1*sin));
				
				cos = Math.cos((j+0.5)*arcStep);
				sin = Math.sin((j+0.5)*arcStep);
				g2D.setFont(new Font(null,Font.ITALIC,(int)((r1-r0)/1.5)));
				
				String letter = "" + alphabet[j%alphabet.length]; 
				
				FontMetrics fm = g2D.getFontMetrics();
				int strW = fm.stringWidth(letter)/2; 
				int strH = fm.getAscent()/2;
				g2D.drawString(letter,mx -strW + (int) ((r0+(r1-r0)/2)*cos),my +strH + (int)((r0+(r1-r0)/2)*sin ));
				if(i  == wordLength){
					String val = getValue(getCodonKeyFromGraph((j+0.5)*arcStep));
					g2D.drawString(val,mx -strW + (int) ((r1+(r1-r0)/2)*cos),my +strH + (int)((r1+(r1-r0)/2)*sin ));
				}
				
				
			}
			int rd = r1-r0;
			r1 = r0;
			r0 = (int) (r0-rd*alphabet.length*((float)(i-1)/wordLength));
			if(i-2 == 0)
				r0 =r1/4;
			segmente /= alphabet.length;
			
		}
		
		return img;
	}
	//TODO noch nicht fertig
	public BufferedImage markCodon(double angle){
		angle %= Math.PI*2;
		BufferedImage imgMarked = img.getSubimage(0, 0, img.getWidth(), img.getHeight());
		double angleStep = Math.PI*2;
		for(int i =0;i<wordLength;i++){
			angleStep /= alphabet.length;
			angle %= angleStep;
		}
		return imgMarked;
	}

	public Color getColor(int index) {
		if(colors ==null)
			return null;
		return colors[index%colors.length];
	}

	public void setColor(int index, Color color) {
		if(colors!=null){
			colors[index%colors.length]=color;
			img =null;
		}
	}
	public int getColorsCount(){
		if(colors == null)
			return 0;
		return colors.length;
	}
	
	public GeneCode getNextCode(){
		return nextGeneCode;
	}
	
	public boolean  setNextCode(GeneCode genc){
		
		if(genc == null)
			return false;
		nextGeneCode = genc.clone();
		return true;
	}

	
}
