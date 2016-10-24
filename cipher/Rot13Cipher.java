package cipher;

// Manual implementation of Rot13, leaves non alphabetic characters unchanged
public class Rot13Cipher extends Cipher {
	public Rot13Cipher() {
		name = "Rot13";
		version = 1.0f;
	}
	public Rot13Cipher(Rot13Cipher other) {
		super(other);
	}
	@Override
	public Cipher clone() {
		return new Rot13Cipher(this);
	}
	@Override
	public String encrypt(String input) {
		StringBuilder temp = new StringBuilder(input);
		for (int i=0; i<temp.length(); i++) {
			
			char c = temp.charAt(i);
			if (c >= 'a' && c <= 'm') c+= 13;
			else if (c >= 'A' && c <= 'M') c+= 13;
			else if (c >= 'n' && c <= 'z') c-= 13;
			else if (c >= 'N' && c <= 'Z') c-= 13;
			
			temp.setCharAt(i,c);
		}
		
		return temp.toString();
	}
	@Override
	public String decrypt(String input) {
		return encrypt(input); // in Rot13 encrypt==decrypt
	}
}
