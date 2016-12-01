// Simple program for testing Ciphers
// The first section is hardcoded examples
// The second section reads the name of a Cipher off of the command-line (args[0])
// and instantiates it for the example

import cipher.*;
import java.util.Arrays;

class CipherTest {
	public static void main(String[] args) throws Exception {
		String resourcePath = "./test-library";
		String demoString = new String("Hello "+args[0]);
		String cipherText = new String();
		String output = new String();

		if (args.length > 0) {
			System.out.println("Testing "+args[0]);

			Cipher myCipher = null; // here to allow access outside try block
			// load the cipher
			myCipher = CoreCipherClassLibrary.instantiateFromString(args[0], Arrays.copyOfRange(args,1,args.length));
			if (myCipher.needsResources())
				myCipher.setResourcePath(resourcePath);

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

			System.out.println();
			System.out.println("Testing clone()");
			Cipher myCopy = (Cipher)myCipher.clone();
			String copyCipherText = myCopy.encrypt(demoString);
			if (copyCipherText.equals(cipherText))
				System.out.println("Ok.");
			else
				System.out.println("FAIL");

			System.out.println();
			System.out.println("Testing equals() with cloned item");
			if (myCopy.equals(myCipher) && myCipher.equals(myCopy))
				System.out.println("Ok.");
			else
				System.out.println("FAIL");

			// Only run this test if NOT given Dummy as input
			if (!args[0].equals("Dummy"))
			{
				System.out.println();
				System.out.println("Testing equals() with Dummy");
				DummyCipher myDummy = new DummyCipher();
				if (myCopy.equals(myDummy) || myCipher.equals(myDummy))
					System.out.println("FAIL");
				else
					System.out.println("Ok.");
			}

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
			else {
				System.out.println("FAIL");
				if (myCipher.needsResources())
					System.out.println("OR ADDED EXTENSION");
			}
		}
	}
}
