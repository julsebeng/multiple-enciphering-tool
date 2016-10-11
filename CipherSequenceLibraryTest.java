
class CipherSequenceLibraryTest {
	public static void main(String[] args) {
		CipherSequenceLibrary myLibrary = new CipherSequenceLibrary("./");
		CipherSequence mySeq = myLibraryLoadCipherSequence("test.cyph"); // okay
		CipherSequence mySeq = myLibraryLoadCipherSequence("CipherSequenceLibrary.java"); // fail, not correct format

		// fail, given file not path
		CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("./test.cyph");
		// fail, given invalid path
		CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("./cyph/");
		// fail, given inaccesible path
		CipherSequenceLibrary failLibrary = new CipherSequenceLibrary("/");
	}
}
