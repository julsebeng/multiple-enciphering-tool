package cipher;

import java.util.Vector;
import java.util.Arrays;
import java.io.Writer;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class CipherSequence {
	private Vector<Cipher> sequence;
	String name;
	String author;
	float version;
	
	public CipherSequence() {
		sequence = new Vector<Cipher>();
	}
	public CipherSequence(CipherSequence other) {
		this.sequence = new Vector<Cipher>(other.sequence);
	}
	@Override
	public Object clone() {
		return new CipherSequence(this);
	}
	public boolean equals(CipherSequence other) {
		return this.sequence.equals(other.sequence);
	}
	public String encrypt(String input) throws Exception {
		int i;
		String temp = new String(input);
		for (i=0; i<sequence.size(); i++) {
			temp = sequence.get(i).encrypt(temp);
		}
		return temp;
	}
	public String decrypt(String input) throws Exception {
		int i;
		String temp = new String(input);
		for (i=sequence.size()-1; 0<=i; i--) {
			temp = sequence.get(i).decrypt(temp);
		}
		return temp;
	}
	public void loadFromFile(String fileName) throws Exception {
		FileReader fr = new FileReader(fileName); // load file as stream
		loadFromFile(fr);
	}
	public void loadFromFile(FileReader fr) throws Exception {
		BufferedReader reader = new BufferedReader(fr); // allow for quick line (block) loading
		
		String line; // raw input of line
		String[] words; // array version of input, space deliniated
		String[] args;  // words[1:end]
		Cipher curCipher;
		while (( line = reader.readLine()) != null) {
			words = line.split(" ");
			if (words.length > 1)
				args = Arrays.copyOfRange(words, 1, words.length);
			else {
				args = new String[1];
				args[0] = "";
			}

			curCipher = CoreCipherClassLibrary.instantiateFromString(words[0], args);
			//System.out.println(curCipher.encrypt(line));

			sequence.add(curCipher);
		}
		reader.close();
	}
	public void saveToFile(String fileName) throws Exception {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
		saveToFile(writer);
	}
	public void saveToFile(Writer writer) throws Exception {
		for (Cipher c: sequence) {
			//System.out.println(c.getName() + " " + c.getArgsString());
			writer.write(c.toString());
			writer.write(String.format("%n"));
		}
		writer.close();
	}
	public Cipher getAt(int i) {
		return sequence.elementAt(i);
	}
	public void removeAt(int i) {
		sequence.removeElementAt(i);
	}
	public void removeLast() {
		sequence.remove(sequence.size()-1);
	}
	public void setAt(Cipher ciph, int i) {
		sequence.setElementAt(ciph, i);
	}
	public void insertAt(Cipher ciph, int i) {
		sequence.insertElementAt(ciph, i);
	}
	public void add(Cipher ciph) {
		sequence.addElement(ciph);
	}
	public int size() {
		return sequence.size();
	}
}
