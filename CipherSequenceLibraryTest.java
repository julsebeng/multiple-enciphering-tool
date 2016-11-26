import cipher.*;
import java.util.Vector;

class CipherSequenceLibraryTest {
	public static void main(String[] args) throws Exception {
		CipherSequenceLibrary myLibrary = new CipherSequenceLibrary_File("./test-library");
		System.out.println("List of .cyph files in ./test-library");
		Vector<String> lib_files = myLibrary.fileNames();
		for (String file : lib_files)
			System.out.println(file);
		System.out.println();

		CipherSequence mySeq;
		try {	
			System.out.println("testing clone() and equals()");
			CipherSequenceLibrary mylib = (CipherSequenceLibrary)myLibrary.clone();
			if (myLibrary.equals(mylib) && mylib.equals(myLibrary))
				System.out.println("Okay\n");
			else
				System.out.println("Not Okay\n");
		}
		catch (Exception ex) {
			System.out.println("Not Okay\n");
		}

		try { // succeed file is in test library
			System.out.println("Testing loadSequence (with extension)");
			mySeq = myLibrary.loadSequence("test.cyph");
			System.out.println("Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Not Okay\n");
		}
		try { // succeed file is in test library w/o extension
			System.out.println("Testing loadSequence (without extension)");
			mySeq = myLibrary.loadSequence("test");
			System.out.println("Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Not Okay\n");
		}

		try { // fail, does not exist
			System.out.println("Testing loadSequence (with missing file)");
			mySeq = myLibrary.loadSequence("test2.cyph");
			System.out.println("Not Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay\n");
		}

		// fail, given file not path
		try {
			System.out.println("Testing invalid directory (given file)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary_File("./test.cyph");
			System.out.println("Not Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay\n");
		}

		// fail, given invalid path
		try {
			System.out.println("Testing invalid directory (given bad path)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary_File("./cyph/");
			System.out.println("Not Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay\n");
		}

		// fail, given inaccessible path
		try {
			System.out.println("Testing invalid directory (given inaccessible path)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary_File("/");
			System.out.println("Not Okay\n");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay\n");
		}

		System.out.println();
		System.out.println("***Testing the file movement functions***");
	}
}
