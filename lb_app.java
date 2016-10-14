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

import java.io.File;
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

	private static String libPath = System.getProperty("user.home") + "/Labyrinthine/";

	//Generic error message holder
	private static String error = "An error has occurred.";

	public static void main(String[] args) throws Exception {

		//If an error occurs when reading the command line, print the error
		if(!parseCommandLine(args)) {
            //Printing the error isn't necessary, but it looks nicer than the exception handling
            //System.out.println(error);
			throw new Exception(error);
		}

		//if(!runCipherSequence()) {
		//	System.out.println(error);
		//}

	}

	/* Implemented by Julian and Sergio */
	private static boolean parseCommandLine(String[] args) {

		//Since only one of -f or -F can be specified, this is a sentinel
		boolean cyphSpecified = false;

		//Can only have enc or dec specified
		//This is implemented as a bool in the class member data, but
		//here an additional value is needed to determine if no direction
		//was set at all:
		//-1: not given by user (error)
		//0: set to decode
		//1: set to encode
		int dirSet = -1;

		//Temporary variables to hold paths; don't want to change
		//the instance data until its been validated
		String locOutput = null;
		String locInput = null;
		String locLibPath = null;
		String locCiphPath = null;
		String locCiphName = null;


		/* ***********************************************************
		 * Pull input from args
		/*************************************************************/

		//Loop through and parse args
		for(int i = 0; i < args.length; i++) {

			//If arg is a flag; check if first char in argument is
			//a '-'
			//Note that combined flags aren't checked (meaning
			//something like -io is invalid). Since every flag
			//Requires some other piece of data to go with it
			//(paths and names), flags can't be combined in an
			//unambiguous way:
			//ex. lb_app -io foo.txt bar.txt
			//Which one is input and which one is output?
			if((args[i].substring(0, 1)).equals("-")) {

				char ch = args[i].charAt(1);

				switch (ch) {
				//if -i, next arg should be filpath
					case 'i':
						//Avoid out-of-bounds error
						if(args.length > (i + 1)) {
							locInput = args[i+1];
						}
						else {
							error = "Invalid arguments.";
							return false;
						}
						break;
				//if -o, next arg should be filepath
					case 'o':
						//Avoid out-of-bounds error
						if(args.length > (i + 1)) {
							locOutput = args[i+1];
						}
						else {
							error = "Invalid arguments.";
							return false;
						}
						break;
				//if -f, next arg should be filepath
					case 'f':
						//Make sure a cipher hasn't already been specified
						if(!cyphSpecified) {
							cyphSpecified = true;

							//Avoid out-of-bounds error
							if(args.length > (i + 1)) {
								locCiphPath = args[i+1];
							}
							else {
								error = "Invalid arguments.";
								return false;
							}
						}
						else {
							error = "Multiple ciphers given.";
							return false;
						}
						break;
				//if -F, next arg should be name
					case 'F':
						//Make sure a cipher hasn't already been specified
						if(!cyphSpecified) {
							cyphSpecified = true;
							//Avoid out-of-bounds error
							if(args.length > (i + 1)) {
								locCiphName = args[i+1];
							}
							else {
								error = "Invalid arguments.";
								return false;
							}
						}
						else {
							error = "Multiple ciphers given.";
							return false;
						}
						break;
				//if -L, next arg should be path
					case 'L':
						//Avoid out-of-bounds error
						if(args.length > (i + 1)) {
							locLibPath = args[i+1];
						}
						else {
							error = "Invalid arguments.";
							return false;
						}
						break;
					default:
						error = "Invalid flag.";
						return false;
				}

			}
			//Check if encode was given
			//Explicit equality is checked instead of a substring
			//of "enc" since "encore" or an equivalent word should
			//not be treated as valid input/
			else if(args[i].toLowerCase().equals("enc") || args[i].toLowerCase().equals("encode")) {
				if(dirSet == -1) {
					dirSet = 1;
				}
				else {
					error = "Multiple encode/decode parameters given.";
					return false;
				}
			}
			//Check if decode was given
			else if(args[i].toLowerCase().equals("dec") || args[i].toLowerCase().equals("decode")) {
				if(dirSet == -1) {
					dirSet = 0;
				}
				else {
					error = "Multiple encode/decode parameters given.";
					return false;
				}
			}

		}

		/* ***********************************************************
		 * Verify Input
		/*************************************************************/
		/* Implemented by Julian Engel */

		//If dir not set, generate an error
		if(dirSet == -1) {
			error = "Please specify encode or decode.";
			return false;
		}
		//If -f or -F was never given, generate an error
		if(!cyphSpecified) {
			error = "No cipher was given.";
			return false;
		}

		//Check to make sure input file exists and is readable
		if(locInput != null) {
			//Expand any relative paths
			if(locInput.startsWith("~" + File.separator)) {
				locInput = System.getProperty("user.home") + locInput.substring(1);
			}
			File tempInFile = new File(locInput);
			if(!tempInFile.exists()) {
				error = "Input file does not exist.";
				return false;
			}
			else if(tempInFile.isDirectory()) {
				error = "Input file is a directory.";
				return false;
			}
			else if(!tempInFile.canRead()) {
				error = "Input file is not readable.";
				return false;
			}
		}

		//Check to make sure cipher file is given and is readable
		if(locCiphPath != null) {
			//Expand any relative paths
			if(locCiphPath.startsWith("~" + File.separator)) {
				locCiphPath = System.getProperty("user.home") + locCiphPath.substring(1);
			}
			File tempCyphFile = new File(locCiphPath);
			if(!tempCyphFile.exists()) {
				error = "Cyph file does not exist.";
				return false;
			}
			else if(tempCyphFile.isDirectory()) {
				error = "Cyph file is a directory.";
				return false;
			}
			else if(!tempCyphFile.canRead()) {
				error = "Cyph file is not readable.";
				return false;
			}
		}

		//check if the library given exists
		if(locLibPath != null) {
			//Expand any relative paths
			if(locLibPath.startsWith("~" + File.separator)) {
				locLibPath = System.getProperty("user.home") + locLibPath.substring(1);
			}
			File tempLibFile = new File(locLibPath);
			if(!tempLibFile.exists()) {
				error = "Library path does not exist.";
				return false;
			}
			//-L specifies a path, not a lib file
			else if(!tempLibFile.isDirectory()) {
				error = "Library path is not a directory.";
				return false;
			}
			else if(!tempLibFile.canRead()) {
				error = "Library path is not readable.";
				return false;
			}
		}

		//TODO: make sure name given in CiphName is actually found in
		//the library



		//Check if output file exists; if it does, must be writable; else, make file
		//Note: do this after all the other input has been verified, so that
		//it's not making a file and then erroring out from something else.
		if(locOutput != null) {
			//Expand any relative paths
			if(locOutput.startsWith("~" + File.separator)) {
				locOutput = System.getProperty("user.home") + locOutput.substring(1);
			}
			File tempOutFile = new File(locOutput);
			if(tempOutFile.exists() && !tempOutFile.canWrite()) {
				error = "Output file is not writable.";
				return false;
			}
			else if(tempOutFile.isDirectory()) {
				error = "Output path is a directory.";
				return false;
			}
		}

		//if made it this far, change class member data and create
		//output file if necessary

		//Encrypt is false by default, but explicitly set it anyways
		if(dirSet == 1)
			encrypt = true;
		else
			encrypt = false;

		if(locOutput != null)
			output = locOutput;

		if(locInput != null)
			input = locInput;

		if(locCiphPath != null)
			ciphPath = locCiphPath;

		if(locCiphName != null)
			ciphName = locCiphName;

		if(locLibPath != null)
			libPath = locLibPath;

		//TODO: create file for output if it doesn't exist
		return true;

	}

	private static boolean runCipherSequence() {

		//TODO: determine what cipher to run
		//TODO: make sure the given file is encoded in a way that matches the cipher
		//TODO: read input, run it through the cipher, and produce output
			//TODO: determine whether files or stdio is used
		return true; //Only here so it compiles!

	}

	private static void cleanup() {



	}
}
