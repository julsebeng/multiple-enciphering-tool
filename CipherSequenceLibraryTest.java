import cipher.*;
import java.util.Vector;

class CipherSequenceLibraryTest {
	public static void main(String[] args) throws Exception {
		CipherSequenceLibrary myLibrary = new CipherSequenceLibrary("./test-library");
		//CipherSequence mySeq = myLibrary.load("test.cyph"); // okay
		
		Vector<String> lib_files = myLibrary.fileNames();
		for (String file : lib_files)
			System.out.println(file);

		CipherSequence mySeq;
		try { // succeed file is in test library
			System.out.println("Testing loadSequence (with extension)");
			mySeq = myLibrary.loadSequence("test.cyph");
			System.out.println("Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Not Okay");
		}
		try { // succeed file is in test library w/o extension
			System.out.println("Testing loadSequence (without extension)");
			mySeq = myLibrary.loadSequence("test");
			System.out.println("Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Not Okay");
		}

		try { // fail, does not exist
			System.out.println("Testing loadSequence (with missing file)");
			mySeq = myLibrary.loadSequence("test2.cyph");
			System.out.println("Not Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay");
		}

		// fail, given file not path
		try {
			System.out.println("Testing invalid directory (given file)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("./test.cyph");
			System.out.println("Not Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay");
		}
		// fail, given invalid path
		try {
			System.out.println("Testing invalid directory (given bad path)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("./cyph/");
			System.out.println("Not Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay");
		}
		// fail, given inaccessible path
		try {
			System.out.println("Testing invalid directory (given inaccessible path)");
			CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("/");
			System.out.println("Not Okay");
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("Okay");
		}
	}
}
