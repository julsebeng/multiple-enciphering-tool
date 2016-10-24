package cipher;
import java.util.Vector;

public class CipherSequenceLibrary {
	private Vector<String> fileNames;
	private String directoryPath;
	CipherSequenceLibrary(String directory) {
		directoryPath = directory;
	}
	// copy constructor
	CipherSequenceLibrary(CipherSequenceLibrary other) {
		this.directoryPath = other.directoryPath;
	}
	// fetch list of files in Library diectory
	/*
	String[] listFiles() {
	}
	*/
}
