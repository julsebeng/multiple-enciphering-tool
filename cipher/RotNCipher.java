package cipher;

// Rotate by N cipher, leaves non alphabetic characters unchanged
public class RotNCipher extends Cipher {
	protected int rotN;
	public RotNCipher(int N) throws Exception {
		if ((N < 0) || (26 < N))
			throw new Exception("Invalid rotation, provide integer N such that 0<N<26");
		rotN = N;
		name = "RotN";
		version = 1.0f;
	}
	public RotNCipher(RotNCipher other) {
		super(other);
		this.rotN = other.rotN;
	}
	@Override
	public Object clone() {
		return new RotNCipher(this);
	}
	public RotNCipher() {
		name = "RotN";
		//System.err.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	@Override
	public void init(String[] args) throws Exception {
		assert(args.length == 1);
		int N = Integer.parseInt(args[0]);
		if (N < 0 || N > 26)
			throw new Exception("Invalid rotation, provide integer N such that 0<N<26");
		rotN = N;
	}
	@Override
	public boolean equals(Cipher other) {
		try {
			RotNCipher rotN = (RotNCipher)other;
			return this.rotN == rotN.rotN;
		}
		catch (Exception ex) {
			return false;
		}
	}
	@Override
	public String getArgsString() {
		return Integer.toString(rotN);
	}
	public void setN(int n) {
		rotN = n;
	}
	public int getN() {
		return rotN;
	}
	@Override
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
	@Override
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
