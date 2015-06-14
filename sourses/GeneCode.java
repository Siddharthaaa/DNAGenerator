import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import javax.lang.model.element.Element;

import org.omg.CORBA.CharSeqHolder;


public class GeneCode implements Serializable {
	
	public final static String FILEEXTENTION = ".genecode";
	
	String name;
	private char[] alphabet;
	private int wordLength;
	
	public Hashtable<String, String> codons;
	
	public GeneCode(){
		this("Default","ACGT",3);
	}
	
	public GeneCode(String name, String alph, int l){
		codons = new Hashtable<String, String>();
		this.alphabet = alph.toCharArray();
		alph="";
		for(char c: alphabet){
			if(!alph.contains(c + ""))
				alph+=c;
		}
		this.alphabet = alph.toCharArray();
		
		this.name = name;
		wordLength = l;
		init("",0);
	}
	
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
			codons.put(name, value);
			return true;
		}
		return false;
	}
	
	boolean SaveAs(String FileName) throws FileNotFoundException{
		
		 XMLEncoder e = new XMLEncoder(
                 new BufferedOutputStream(
                     new FileOutputStream(FileName)));
		 e.writeObject(this);
		 e.close();
		return false;
	}
	
	static GeneCode ReadCode(String fileName) throws FileNotFoundException{
		 XMLDecoder d = null;
	
			d = new XMLDecoder(
			         new BufferedInputStream(
			             new FileInputStream(fileName)));
	
		 GeneCode result = (GeneCode) d.readObject();
		 d.close();
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
		GeneCode gc = new GeneCode("","A",0);
		gc.alphabet = alphabet.clone();
		gc.codons = (Hashtable<String, String>) codons.clone();
		gc.name = name;
		gc.wordLength = wordLength;
		return gc;
	}

	public String getName() {
		return name;
	}
	
	public char[] getAlphabet(){
		return alphabet;
	}

	public void setName(String text) {
		name = text;
		// TODO Auto-generated method stub
		
	}

	public int getCodonLength() {
		return wordLength;
		
	}
	
	public String getCodonKeyFromGraph(double angle){
		String key = "";
		double angleStep = Math.PI*2;
		for(int i =0;i<wordLength;i++){
			angleStep /= alphabet.length;
			int index = (int)(angle/angleStep);
			System.out.println(index);
			key += alphabet[index];
			angle %= angleStep;
		}
		return key;
	}
	
	public BufferedImage getCodeSun(){
		int w = 1000;
		int h = 1000;
		
		int mx= w/2;
		int my= h/2;
		
		int r0,r1;
		r0 =0;
		r1= h/4;
		
		int segmente = 1;
		
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2D = (Graphics2D) img.getGraphics();
		g2D.setBackground(Color.WHITE);
		g2D.setColor(Color.BLACK);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                  RenderingHints.VALUE_ANTIALIAS_ON );
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY );
		double arcStep =0;
		for(int i=0;i < wordLength;i++){
			g2D.setStroke(new BasicStroke((r1-r0)/50));
			g2D.drawOval(mx-r1, my-r1, r1*2, r1*2);
			segmente *= alphabet.length;
			arcStep = Math.PI * 2f/segmente;
			for(int j =0; j < segmente;j++){
				double cos = Math.cos(j*arcStep);
				double sin = Math.sin(j*arcStep);
				
				g2D.drawLine((int)(mx + r0*cos), (int)(my+ r0*sin),(int)(mx + r1*cos), (int)(my+ r1*sin));
				
				cos = Math.cos((j+0.5)*arcStep);
				sin = Math.sin((j+0.5)*arcStep);
				g2D.setFont(new Font(null,Font.ITALIC,(int)((r1-r0)/1.5)));
				
				String letter = "" + alphabet[j%alphabet.length]; 
				
				FontMetrics fm = g2D.getFontMetrics();
				int strW = fm.stringWidth(letter)/2; 
				int strH = fm.getAscent()/2;
				g2D.drawString(letter,mx -strW + (int) ((r0+(r1-r0)/2)*cos),my +strH + (int)((r0+(r1-r0)/2)*sin ));
				if(i +1 == wordLength){
					String val = getValue(getCodonKeyFromGraph((j+0.5)*arcStep));
					g2D.drawString(val,mx -strW + (int) ((r1+(r1-r0)/2)*cos),my +strH + (int)((r1+(r1-r0)/2)*sin ));
				}
				//g2D.drawArc(w/2, w/2, 200, 400, 0, (360*j)/segmente);
				
			}
			int tmp = r0;
			r0 = r1;
			r1 = r1+ (int)((float)(r1-tmp)/alphabet.length*(2-(float)i/wordLength));
		}
		
		return img;
	}
	
}
