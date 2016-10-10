// Simple program for testing Ciphers
// The first section is hardcoded examples
// The second section reads the name of a Cipher off of the command-line (args[0])
// and instantiates it for the example
class CipherTest {
	public static void main(String[] args) {
		String input = new String();
		String output = new String();

		/*
		System.out.println("Running Dummy");
		Cipher dCipher = new DummyCipher();
		input  = dCipher.encrypt("Hello Dummy");
		output = dCipher.decrypt(input);
		System.out.println(output);

		System.out.println("Running Rot 10");
		Cipher rCipher = new RotNCipher(10);
		input  = rCipher.encrypt("Hello Rot 10");
		output = rCipher.decrypt(input);
		System.out.println(output);

		System.out.println("Running Substition qwertyiopasdfghjklzxcvbnm");
		Cipher sCipher = new SubstitutionCipher("qwertyuiopasdfghjklzxcvbnm");
		input  = sCipher.encrypt("Running Substition qwertyiopasdfghjklzxcvbnm");
		output = sCipher.decrypt(input);
		System.out.println(output);
		*/

		if (args.length > 0) {
			System.out.println("Running "+args[0]);
			try {
				Cipher myCipher = (Cipher)Class.forName(args[0]+"Cipher").newInstance();
				input  = myCipher.encrypt("Hello "+args[0]);
				output = myCipher.decrypt(input);
				System.out.println(output);
			}
			catch (Exception ex) {
				System.out.println("Not a valid cipher");
			}
	
		}
	}
}
