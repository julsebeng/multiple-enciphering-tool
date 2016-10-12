//Core CLI code written by Julian Engel
/* Normal CLI for the cipher library. Allows a message to be encrypted or
 * decrypted with the specified cipher sequence.
 * Flags:
 * 	-i: specifies input file
 * 	-o: specifies output file
 * 	-f: path to a .cyph file
 * 	-F:
 * 	-L: override default library directory
 * 	enc(ode) | dec(ode): what direction to perform cipher
 * If no -i or -o is given, will default to stdio.
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

public class lb_app {

	//Member data
	private static boolean encrypt = false;

	//If these remain null, will use stdio
	private static String output = null;
	private static String input = null;
	private static String ciphPath = null;
	private static String ciphName = null;

	private static String libPath = System.getProperty("user.home") + "/Labyrinthine/cipherlib";

	//Generic error message holder
	private static String error = "An error has occurred.";

	public static void main(String[] args) {

		//If an error occurs when reading the command line, print the error
		if(!parseCommandLine(args)) {
			System.out.println(error);
		}

	}


	private static boolean parseCommandLine(String[] args) {

		//Since only one of -f or -F can be specified
		boolean cyphSpecified = false;
		//Can only have enc or dec specified
		int dirSet = -1;

		//Temporary variables to hold paths; don't want to change
		//the instance data until its been validated
		boolean locEncrypt = false;

		String locOutput = null;
		String locInput = null;
		String locLibPath = null;
		String locCiphPath = null;
		String locCiphName = null;

		//Loop through and parse args
		for(int i = 0; i < args.length; i++) {

			//TODO: if arg is a flag
			if((args[i].substring(0, 1)).equals("-")) {

				char ch = args[i].charAt(1);

				switch (ch) {
				//TODO: if -i, next arg should be filpath
					case 'i':
						locInput = args[i+1];
						break;
				//TODO: if -o, next arg should be filepath
					case 'o':
						locOutput = args[i+1];
						break;
				//TODO: if -f, next arg should be filepath
					case 'f':
						locCiphPath = args[i+1];
						break;
				//TODO: if -F, next arg should be name
					case 'F':
						locCiphName = args[i+1];
						break;
				//TODO: if -L, next arg should be path
					case 'L':
						locLibPath = args[i+1];
						break;
					default:
						error = "Invalid flag.";
						return false;
				}
				
			}
			else if(args[i].equals("enc") || args[i].equals("encode")) {
				if(dirSet == -1) {
					dirSet = 1;
				}
				else {
					error = "Multiple encode/decode parameters given.";
					return false;
				}
			}
			else if(args[i].equals("dec") || args[i].equals("decode")) {
				if(dirSet == -1) {
					dirSet = 0;
				}
				else {
					error = "Multiple encode/decode parameters given.";
					return false;
				}
			}
			
		}

		//TODO: check paths and files given to make sure files exist and are readable/writable
		//If encode or decode isn't given, send error
		if(dirSet == -1) {
			error = "Please specify encode or decode.";
			return false;
		}

		return true; 

	}

	private static boolean runCipherSequence() {

		return false; //Only here so it compiles!

	}

	private static void cleanup() {



	}
}
