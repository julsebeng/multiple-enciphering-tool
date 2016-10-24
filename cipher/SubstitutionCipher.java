package cipher;
import java.util.HashMap;

// 26-letter alphabetic substitution cipher, *only* handles alphabetic [a-z] characters, and ignores case
// assume source is abcd...xyz and you provide the associated mapping (key)
// ex abcd...wxyz -> qwerty...zxcvbnm  (map from alphabet to qwerty keyboard by position)
public class SubstitutionCipher extends Cipher {
	private String key;
	static private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public SubstitutionCipher(String keyArg) {
		name = "Substitution";
		key = keyArg;	
	}
	public SubstitutionCipher(SubstitutionCipher other) {
		super(other);
		this.key = other.key;
	}
	@Override
	public Cipher clone() {
		return new SubstitutionCipher(this);
	}
	public SubstitutionCipher() {
		name = "Substitution";
		System.err.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	@Override
	public void init(String[] args) {
		assert(args.length == 1);
		key = args[0];
	}
	@Override
	public String getArgsString() {
		return new String(key);
	}
	public void setKey(String keyArg) {
		key = keyArg;
	}
	public String getKey() {
		return key;
	}
	@Override
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
	@Override
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
