package cipher;


// Base class for Ciphers
// All Ciphers must implement this interface inorder for the CipherSequence class to function
public abstract class Cipher {
	// need to add an init function that accepts the string to be parsed from cyph file

	protected String  name;		// name of the Cipher
	protected float   version;	// version of the Cipher
	protected Boolean unicode;	// true if this Cipher supports unicode data

	public Cipher() {
		unicode = false;
	}
	public Cipher(Cipher other) {
		this.name = other.name;
		this.version = other.version;
		this.unicode = other.unicode;
	}
	abstract public Cipher clone();
	public void init(String[] args) {
	}
	public String getName() {
		return new String(name);
	}
	public String getArgsString() {
		return new String("");
	}
	public String toString() {
		String args = this.getArgsString();
		if (args.equals(""))
			return String.format("%s", this.getName());
		else
			return String.format("%s %s", this.getName(), args);
	}
	abstract public String encrypt(String input) throws Exception;
	abstract public String decrypt(String input) throws Exception;
}
