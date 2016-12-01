package cipher;

// Compatability implementation of CipherSequenceLibrary for Android and other platforms without full java 7
// http://docs.oracle.com/javase/tutorial/essential/io/legacy.html

import java.util.Vector;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;


public class CipherSequenceLibrary_File implements CipherSequenceLibrary, Cloneable {
	private File libraryDirectoryPath;
	public CipherSequenceLibrary_File(String libraryDirectory) throws Exception {
		libraryDirectoryPath = new File(libraryDirectory);

		// Make sure library is a valid and accessible directory
		if (!libraryDirectoryPath.exists())
			throw new Exception("Library directory not found");
		if (libraryDirectoryPath.isFile())
			throw new Exception("Given file not directory!");
		if (!libraryDirectoryPath.canRead() || !libraryDirectoryPath.canWrite())
			throw new Exception("We don't have permission to access that directory!");
	}
	public CipherSequenceLibrary_File(CipherSequenceLibrary_File other) {
		this.libraryDirectoryPath = new File(other.libraryDirectoryPath.toString());
	}
	@Override
	public Object clone() {
		return new CipherSequenceLibrary_File(this);
	}
	public boolean equals(CipherSequenceLibrary other) {
		String left = this.getLibraryPathString();
		String right = other.getLibraryPathString();
		return left.equals(right);
	}
	private String addCyphExtension(String fileName){
		if (fileName.endsWith(".cyph"))
			return fileName;
		else
			return fileName+".cyph"; 
	}
	private void copy(FileInputStream IS, FileOutputStream OS) throws IOException {
		FileChannel inChan = (FileChannel)Channels.newChannel(IS);
		FileChannel ouChan = (FileChannel)Channels.newChannel(OS);

		long position = 0;
		long size = inChan.size();
		// transferTo may fail for files > 1 MB in size
		// use loop to transfer by chunks
		while (position < size) {
			long count = inChan.transferTo(position, size, ouChan);
			if (count > 0) {
				position += count;
				size -= count;
			}
		}
	}
	public String getLibraryPathString() {
		return this.libraryDirectoryPath.toString();
	}
	public Vector<String> fileNames() throws Exception {
		Vector<String> names = new Vector<String>();
		File[] temp = libraryDirectoryPath.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".cyph");
			}
		});
		for (File name : temp)
			names.add(name.getName());

		return names;
	}
	public void installFile(String filePath) throws Exception {
		File source = new File(filePath);
		if (!source.exists())
			throw new Exception("File not found");

		File destination = new File(libraryDirectoryPath, source.getName());

		FileInputStream  IS = null;
		FileOutputStream OS = null;
		try {
			IS = new FileInputStream(source);
			OS = new FileOutputStream(destination);
			copy(IS,OS);
		}
		finally {
			if (IS!=null) IS.close();
			if (OS!=null) OS.close();
		}

	}
	public void getFile(String fileName, String targetDirectory) throws Exception {
		File source = new File(libraryDirectoryPath, addCyphExtension(fileName));
		if (!source.exists())
			throw new Exception("File not in library");
		File destination = new File(targetDirectory, fileName);

		FileInputStream  IS = null;
		FileOutputStream OS = null;
		try {
			IS = new FileInputStream(source);
			OS = new FileOutputStream(destination);
			copy(IS,OS);
		}
		finally {
			if (IS!=null) IS.close();
			if (OS!=null) OS.close();
		}
	}
	public void removeFile(String fileName) throws Exception {
		File source = new File(libraryDirectoryPath, addCyphExtension(fileName));
		if (!source.exists())
			throw new Exception("File not in library");
		source.delete();
	}
	public CipherSequence loadSequence(String fileName) throws Exception {
		File source = new File(libraryDirectoryPath, addCyphExtension(fileName));
		if (!source.exists())
			throw new Exception("File not in library");

		CipherSequence result = new CipherSequence(libraryDirectoryPath.toString());
		result.loadFromFile(source.toString());
			
		return result;
	}
	public void saveSequence(CipherSequence seq, String fileName, boolean overwrite) throws Exception { 
		File destination = new File(libraryDirectoryPath, addCyphExtension(fileName));
		if (!overwrite && !destination.exists())
			throw new Exception("Attempting to overwite cyph file");

		seq.saveToFile(destination.toString());
	}
}
