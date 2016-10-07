
import java.util.Vector;

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
}
