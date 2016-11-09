package cipher;
import java.util.HashMap;
import java.util.HashSet;

// 36-character alpha-numeric substitution cipher, *only* handles alphabetic [a-z] and numeric [0-9] characters, and ignores case
// assume source is abcd...xyz and you provide the associated mapping (key)
// ex abcd...wxyz -> qwerty...zxcvbnm  (map from alphabet to qwerty keyboard by position)
public class SubstitutionCipher extends Cipher {
	private String key;
	static private String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
	public SubstitutionCipher(String keyArg) throws Exception {
		if (keyArg.length() != 36 || !isUniqueChars(keyArg))
			throw new Exception("Substitution key must be a permutation of abc...xyz0123456789");
		name = "Substitution";
		key = keyArg;	
	}
	public SubstitutionCipher(SubstitutionCipher other) {
		super(other);
		this.key = other.key;
	}
	@Override
	public Object clone() {
		return new SubstitutionCipher(this);
	}
	public SubstitutionCipher() {
		name = "Substitution";
		System.err.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	@Override
	public void init(String[] args) throws Exception {
		assert(args.length == 1);
		if (args[0].length() != 36 || !isUniqueChars(args[0]))
			throw new Exception("Substitution key must be a permutation of abc...xyz0123456789");
		key = args[0];
	}
	@Override
	public String getArgsString() {
		return key;
	}
	@Override
	public boolean equals(Cipher other) {
		try {
			SubstitutionCipher substitution = (SubstitutionCipher)other;
			return this.key.equals(substitution.key);
		}
		catch (Exception ex) {
			return false;
		}
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
		HashMap<Character,Character> encodeHash = new HashMap<Character,Character>(2*alphabet.length()-10);

		// Insert lowercase and numeric entries into the hashtable, (k,v)
		for (int i=0; i<alphabet.length(); i++) {
			encodeHash.put(alphabet.charAt(i), key.charAt(i));
		}

		// Insert uppercase entries into the hashtable, (k,v)
		// Make sure to strip out numeric entries first!
		String upperAlphabet = alphabet.replaceAll("[0-9]", "").toUpperCase();
		String upperKey = key.replaceAll("[0-9]", "").toUpperCase();
		for (int i=0; i<alphabet.length()-10; i++) {
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

		String upperAlphabet = alphabet.replaceAll("[0-9]", "").toUpperCase();
		String upperKey	= key.replaceAll("[0-9]", "").toUpperCase();
		for (int i=0; i<alphabet.length()-10; i++) {
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

	// helper function for checking that key contains no duplicates
	private static boolean isUniqueChars(String str) {
		HashSet<Character> unique = new HashSet<Character>();
		for(int i=0; i<str.length();i++){
			unique.add(str.charAt(i));
		}
		if(unique.size()!=str.length()){
			return false;
		}       
		return true;
	}
}
