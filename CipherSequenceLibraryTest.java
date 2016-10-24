
class CipherSequenceLibraryTest {
	public static void main(String[] args) {
		CipherSequenceLibrary myLibrary = new CipherSequenceLibrary("./");
		CipherSequence mySeq = myLibrary.loadSequence("test.cyph"); // okay
		CipherSequence mySeq = myLibrary.loadSequence("CipherSequenceLibrary.java"); // fail, not correct format
		CipherSequenceLibrary = null;

		// fail, given file not path
		failLibrary = new CipherSequenceLibrary("./test.cyph");
		// fail, given invalid path
		failLibrary = new CipherSequenceLibrary("./cyph/");
		// fail, given inaccesible path
		CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("/");
	}
}
