
import java.util.HashMap;

// Base class for Ciphers
// All Ciphers must implement this interface inorder for the CipherSequence class to function
abstract class Cipher {
	// need to add an init function that accepts the string to be parsed from cyph file

	protected static String  name;		// name of the Cipher
	protected static float   version;	// version of the Cipher
	protected static Boolean unicode;	// true if this Cipher supports unicode data
	public Cipher() {
		unicode = false;
	}
	abstract String encrypt(String input);
	abstract String decrypt(String input);
}

// Dummy cipher, does nothing to input
// Example on how to implement Cipher objects
class DummyCipher extends Cipher {
	DummyCipher() {
		name = "Dummy";
		version = 1.0f;
		unicode = true;
	}
	public String encrypt(String input) {
		return input;
	}
	public String decrypt(String input) {
		return input;
	}
}

// Rotate by N cipher, leaves non alphabetic characters unchanged
class RotNCipher extends Cipher {
	protected int rotN;
	RotNCipher(int N) {
		rotN = N;
		name = "RotN";
		version = 1.0f;
	}
	public String encrypt(String input) {
		// regular strings are supposed to be immutable, and modifying them produces new string objects
		// StringBuilder on the other hand is mutable, and designed for rapid editing of strings
		StringBuilder temp = new StringBuilder(input);
		for (int i=0; i<temp.length(); i++) {
			int c = temp.charAt(i);
			// First, subtract 'a' to get the value of the letter relative to 'a', that is 'a' is 0, 'b' is 1, 'z' is 26
			// Then, add the offset, and modulus by 26 to simulate cycling back to 'a'
			// Finally, add 'a' back again to get a valid ASCII character
			if (c >= 'a' && c <= 'z')
				c = (((c - 'a') + rotN) % 26) + 'a';
			if (c >= 'A' && c <= 'Z')
				c = (((c - 'A') + rotN) % 26) + 'A';

			temp.setCharAt(i,(char)c);
		}

		return temp.toString();
	}
	public String decrypt(String input) {
		StringBuilder temp = new StringBuilder(input);
		for (int i=0; i<temp.length(); i++) {
			int c = temp.charAt(i);
			// See the implementation of encrypt to get the general gist of the mathematics here
			// Only major difference is using (26-rotN) in place of rotN,
			// which is simply a calculation of how much to rotate by inorder to complete one full rotation around the alphabet
			if (c >= 'a' && c <= 'z')
				c = (((c - 'a') + (26-rotN)) % 26) + 'a';
			if (c >= 'A' && c <= 'Z')
				c = (((c - 'A') + (26-rotN)) % 26) + 'A';

			temp.setCharAt(i,(char)c);
		}

		return temp.toString();
	}
}
	

// Manual implementation of Rot13, leaves non alphabetic characters unchanged
class Rot13Cipher extends RotNCipher {
	Rot13Cipher() {
		super(13);
		name = "Rot13";
		version = 1.0f;
	}
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
	public String decrypt(String input) {
		return encrypt(input); // in Rot13 encrypt==decrypt
	}
}


// 26-letter alphabetic substitution cipher, *only* handles alphabetic [a-z] characters, and ignores case
// assume source is abcd...xyz and you provide the associated mapping (key)
// ex abcd...wxyz -> qwerty...zxcvbnm  (map from alphabet to qwerty keyboard by position)
class SubstitutionCipher extends Cipher {
	private String key;
	static private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	SubstitutionCipher(String keyArg) {
		key = keyArg;	
	}
	public String encrypt(String input) {
		assert(alphabet.length() == key.length());

		// Make a hash map, reserve enough room for upper and lower case of [a-z]
		HashMap<Character,Character> encodeHash = new HashMap<Character,Character>(2*alphabet.length());

		// Insert lowercase entries into the hashtable, (k,v)
		for (int i=0; i<alphabet.length(); i++) {
			encodeHash.put(alphabet.charAt(i), key.charAt(i));
		}

		// Insert uppercase entries into the hashtable, (k,v)
		String upperAlphabet = (new String(alphabet)).toUpperCase();
		String upperKey = (new String(key)).toUpperCase();
		for (int i=0; i<alphabet.length(); i++) {
			encodeHash.put(upperAlphabet.charAt(i), upperKey.charAt(i));
		}
		
		StringBuilder temp = new StringBuilder(input);
		for (int i=0; i<input.length(); i++) {
			char c = temp.charAt(i);
			// if character isn't in the key (upper or lower) then it returns null pointer, catch this and return the given input
			// the effect is ignoring all non-alphabetic chars, but without explicit testing of proper range
			try {
				c = encodeHash.get(c);
			}
			catch (java.lang.NullPointerException ex) {
			}
			temp.setCharAt(i,c);
		}
		return temp.toString();
	}
	// See encrypt for detailed explanation of algorithm
	// Only significant difference is reversal of key and value
	public String decrypt(String input) {
		assert(alphabet.length() == key.length());
		HashMap<Character,Character> decodeHash = new HashMap<Character,Character>(alphabet.length());
		for (int i=0; i<alphabet.length(); i++) {
			decodeHash.put(key.charAt(i), alphabet.charAt(i));
		}

		String upperAlphabet = (new String(alphabet)).toUpperCase();
		String upperKey = (new String(key)).toUpperCase();
		for (int i=0; i<alphabet.length(); i++) {
			decodeHash.put(upperKey.charAt(i), upperAlphabet.charAt(i));
		}
		
		StringBuilder temp = new StringBuilder(input);
		for (int i=0; i<input.length(); i++) {
			char c = temp.charAt(i);
			try {
				c = decodeHash.get(c);
			}
			catch (java.lang.NullPointerException ex) {
			}
			temp.setCharAt(i,c);
		}
		return temp.toString();
	}
}
