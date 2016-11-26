package cipher;

import java.util.Vector;

public interface CipherSequenceLibrary extends Cloneable {
	public String getLibraryPathString();
	public Vector<String> fileNames() throws Exception;
	public void installFile(String filePath) throws Exception;
	public void getFile(String fileName, String targetDirectory) throws Exception;
	public void removeFile(String fileName) throws Exception;
	public CipherSequence loadSequence(String fileName) throws Exception;
	public void saveSequence(CipherSequence seq, String fileName, boolean overwrite) throws Exception; 
	public Object clone();
	public boolean equals(CipherSequenceLibrary other);
}
