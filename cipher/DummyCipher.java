package cipher;

// Dummy cipher, does nothing to input
// Example on how to implement Cipher objects
public class DummyCipher extends Cipher {
	public DummyCipher() {
		name = "Dummy";
		version = 1.0f;
		unicode = true;
	}
	public DummyCipher(DummyCipher other) {
		super(other);
	}
	@Override
	public Object clone() {
		return new DummyCipher(this);
	}
	@Override
	public String encrypt(String input) {
		return input;
	}
	@Override
	public String decrypt(String input) {
		return input;
	}
	@Override
	public boolean equals(Cipher other) {
		try {
			DummyCipher dummy = (DummyCipher)other;
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
}
