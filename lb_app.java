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
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

//libs for reading stdio
import java.io.Console;

//Imported exceptions
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

public class lb_app {

	//Member data
	private static boolean encrypt = false;

	//If these remain null, will use stdio
	private static String output = null;
	private static String input = null;
	private static String ciphPath = null;

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

		if(!runCipherSequence()) {
			throw new Exception(error);

			//System.out.println(error);
		}

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
			// -L should specify a directory, not a file
			else if(!tempLibFile.isDirectory()) {
				error = "Library path is not a directory.";
				return false;
			}
			else if(!tempLibFile.canRead()) {
				error = "Library path is not readable.";
				return false;
			}
		}

		//Make sure name given in CiphName is actually found in
		//the library
    if(locCiphName != null) {
			File tempCiphName = new File(libPath + locCiphName);
			if(!tempCiphName.exists()) {
				error = "Ciph name not found in library.";
				return false;
			}
			else if(tempCiphName.isDirectory()) {
				error = "Ciph in library is a directory, not a file.";
				return false;
			}
			else if(!tempCiphName.canRead()) {
				error = "Ciph in library is not readable.";
				return false;
			}
    }
    

		//Check if output file exists; if it does, must be writable; else, make file
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
      else if(!tempOutFile.exists()) {
        try {
          PrintWriter writer = new PrintWriter("output", "UTF-8");
        }
        catch(FileNotFoundException e) {
          error = e.getMessage();
          return false;
        }
        catch(UnsupportedEncodingException e) {
          error = e.getMessage();
          return false;
        }
      }
		}

		//if made it this far, change class member data

		//Encrypt is false by default, but explicitly set it anyways
		if(dirSet == 1)
			encrypt = true;
		else
			encrypt = false;

		if(locOutput != null)
			output = locOutput;

		if(locInput != null)
			input = locInput;

    //If specific path given, use that, else use the library path
		if(locCiphPath != null)
			ciphPath = locCiphPath;
    else if(locCiphName != null)
			ciphPath = libPath + locCiphName;

		if(locLibPath != null)
			libPath = locLibPath;

		return true;

	}


/* ***********************************************************
 * Encrypt/decrypt process
/*************************************************************/
private static boolean runCipherSequence() {

    String returnData = new String();      
    String inputData = new String();

	//TODO: make sure the given file is encoded in a way that matches the cipher

    /* ***********************************************************
	 * Create a new CipherSequence for the ciphering
	/*************************************************************/
    CipherSequence cSeq = null;
    try {
      cSeq = new CipherSequence();
    }
    catch(Exception e) {
      error = e.getMessage();
      return false;
    }


    //Load a cipher sequence from given file
    try {
      cSeq.loadFromFile(ciphPath);
    }
    catch (Exception e) {
      error = e.getMessage();
      return false;
    }

    //If input is from a file, load it into a string, else ask for input
    if(input != null)  {

      //To maximize compatibility, I want to avoid using any Apache or Java 7
      //libs for reading the input file, in case they aren't available on Android
      Scanner scanner = null;
      try {
        scanner = new Scanner(new File(input));
      }
      catch(FileNotFoundException e) {
        error = e.getMessage();
        return false;
      }

      inputData = scanner.useDelimiter("\\A").next();
      scanner.close();
    }

    //Otherwise, prompt for input from stdin
    else {
      
      //Console c = System.console();
      //if(c == null) {
      //  error = "Error: could not obtain console for standard I/O.";
      //  return false;
      //}

      //inputData = c.readLine();

      InputStreamReader isReader = new InputStreamReader(System.in);
      BufferedReader bufReader = new BufferedReader(isReader);

      StringBuilder builder = new StringBuilder();
      while (true) {
        try {
            String input = null;
            if((input = bufReader.readLine()) != null) {
                builder.append(input);
                builder.append("\n");
            }
            else {
                break;
            }
        }
        catch(Exception e) {
            error = e.getMessage();
            return false;
        }
      }
      inputData = builder.toString();
  }



  //Before enciphering or deciphering, make sure there is something to cipher.
  if(inputData == null) {
    error = "error: no input given.";
    return false;
  }


	/* ***********************************************************
	 * Encode or decode the file
	/*************************************************************/
  
    if(encrypt) {

      try {
        returnData = cSeq.encrypt(inputData);
      }
      catch(Exception e) {
        error = e.getMessage();
        return false;
      }

    }
    else {
           
      try {
        returnData = cSeq.decrypt(inputData);
      }
      catch(Exception e) {
        error = e.getMessage();
        return false;
      }

    }
    

	/* ***********************************************************
	 * Output result
	/*************************************************************/
    //This ought to determine where returnData is going
    if(returnData == null) {
      error = "Error: no data to output.";
      return false;
    }

    //If an output file was specified, write to that file
    if(output != null) {

      BufferedWriter writeBuf = null;

      try {
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        writer.println(returnData);
        writer.close();
      }
      catch(IOException e) {
         error = e.getMessage();
         return false;   
      }

    }

    //Else, print to stdout
    else {

     // Console c = System.console();
     // if(c == null) {
     //   error = "Error: could not obtain console for standard I/O.";
     //   return false;
     // }

     //c.format(returnData); 
     System.out.println(returnData);

    }

		return true; 

	}

  //No cleanup function is necessary since Java has garbage collection...

}
