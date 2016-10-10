
import java.util.Vector;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

class CipherSequence {
	private Vector<Cipher> sequence;
	String name;
	String author;
	float version;
	
	public CipherSequence() {
	}
	public String encrypt(String input) {
		int i;
		String temp = new String(input);
		for (i=0; i<sequence.size(); i++) {
			temp = sequence.get(i).encrypt(temp);
		}
		return temp;
	}
	public String decrypt(String input) {
		int i;
		String temp = new String(input);
		for (i=sequence.size(); 0<i; i--) {
			temp = sequence.get(i).decrypt(temp);
		}
		return temp;
	}
	public void loadFromFile(String fileName) throws Exception {
		FileReader fr = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		Cipher curCipher;
		while (( line = reader.readLine()) != null) {
			System.out.println(line);
			curCipher = CoreCipherClassLibrary.instantiateFromString(line);
			sequence.add(curCipher);
		}
		reader.close();
	}
}
