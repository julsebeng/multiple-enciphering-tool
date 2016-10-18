// Simple program for testing Ciphers
// The first section is hardcoded examples
// The second section reads the name of a Cipher off of the command-line (args[0])
// and instantiates it for the example

import java.util.Arrays;

class CipherTest {
	public static void main(String[] args) {
		String demoString = new String("Hello "+args[0]);
		String cipherText = new String();
		String output = new String();

		/*
		System.out.println("Running Dummy");
		Cipher dCipher = new DummyCipher();
		cipherText  = dCipher.encrypt("Hello Dummy");
		output = dCipher.decrypt(cipherText);
		System.out.println(output);

		System.out.println("Running Rot 10");
		Cipher rCipher = new RotNCipher(10);
		cipherText  = rCipher.encrypt("Hello Rot 10");
		output = rCipher.decrypt(cipherText);
		System.out.println(output);

		System.out.println("Running Substition qwertyiopasdfghjklzxcvbnm");
		Cipher sCipher = new SubstitutionCipher("qwertyuiopasdfghjklzxcvbnm");
		cipherText  = sCipher.encrypt("Running Substition qwertyiopasdfghjklzxcvbnm");
		output = sCipher.decrypt(cipherText);
		System.out.println(output);
		*/

		if (args.length > 0) {
			System.out.println("Testing "+args[0]);

			Cipher myCipher = null; // here to allow access outside try block
			// load the cipher
			try {
				myCipher = (Cipher)Class.forName(args[0]+"Cipher").newInstance();
				if (args.length > 1) {
					myCipher.init(Arrays.copyOfRange(args, 1, args.length));
				}

				System.out.println();
				System.out.println("Testing encrypt and decrypt");
				cipherText  = myCipher.encrypt(demoString);
				output = myCipher.decrypt(cipherText);
				//System.out.println(demoString);
				//System.out.println(output);
				if (output.equals(demoString))
					System.out.println("Ok.");
				else
					System.out.println("FAIL");

				/* Can't actually work, would need to use dynamic instantiation like above and 1 arg constructor
				System.out.println();
				System.out.println("Testing Copy Constructor");
				Cipher myCopy = Cipher(myCipher);
				String copyCipherText = myCopy.encrypt(demoString);
				if (copyCipherText == cipherText)
					System.out.println("Ok.");
				else
					System.out.println("FAIL");
				*/

				System.out.println();
				System.out.println("Testing toString()");
				// When converted to string arrays add "[" and "]" on each side, keep to check for extra spaces
				String outToString   = "[" + myCipher.toString() + "]";
				// strip out seperating commas in converted array so matching works
				String origArgString = Arrays.toString(args).replace(",","");
				System.out.println(outToString);
				System.out.println(origArgString);
				if (outToString.equals(origArgString))
					System.out.println("Ok.");
				else
					System.out.println("FAIL");

			}
			catch (ClassNotFoundException ex) {
				System.out.println("Not a valid cipher");
				return;
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}
