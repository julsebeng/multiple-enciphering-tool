import cipher.*;
import java.util.Vector;

class CipherSequenceLibraryTest {
	public static void main(String[] args) throws Exception {
		CipherSequenceLibrary myLibrary = new CipherSequenceLibrary("./lb-library");
		//CipherSequence mySeq = myLibrary.load("test.cyph"); // okay
		
		Vector<String> lib_files = myLibrary.fileNames();
		for (String file : lib_files)
			System.out.println(file);

		/*
		try { // fail, not correct format
			CipherSequence mySeq = myLibrary.loadSequence("CipherSequenceLibrary.java");
		}
		catch (Exception ex) {
			System.out.println("Okay");
		}

		// fail, given file not path
		failLibrary = new CipherSequenceLibrary("./test.cyph");
		// fail, given invalid path
		failLibrary = new CipherSequenceLibrary("./cyph/");
		// fail, given inaccesible path
		CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("/");
		*/
	}
}
