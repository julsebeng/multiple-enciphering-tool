package cipher;

import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;

public class CipherSequenceLibrary implements Cloneable {
	private Path libraryDirectoryPath;
	public CipherSequenceLibrary(String libraryDirectory) throws Exception {
		libraryDirectoryPath = Paths.get(libraryDirectory);

		// Make sure library is a valid and accessible directory
		if (Files.notExists(libraryDirectoryPath))
			throw new Exception("Library directory not found");
		if (Files.isRegularFile(libraryDirectoryPath))
			throw new Exception("Given file not directory!");
		if (!Files.isReadable(libraryDirectoryPath) || !Files.isWritable(libraryDirectoryPath))
			throw new Exception("We don't have permission to access that directory!");
	}
	public CipherSequenceLibrary(CipherSequenceLibrary other) {
		// Path does not have a clone() method, and is only instatiatied by factories, but we need a new copy
		// Thus we have to extract the string form of the path and pass that to the factory for a new (and equivalent) object
		this.libraryDirectoryPath = Paths.get(other.libraryDirectoryPath.toString());
	}
	@Override
	public Object clone() {
		return new CipherSequenceLibrary(this);
	}
	public boolean equals(CipherSequenceLibrary other) {
		return this.libraryDirectoryPath.equals(other.libraryDirectoryPath);
	}

	// adds .cyph to file names that dont end in .cyph
	private String addCyphExtension(String fileName){
		if (fileName.endsWith(".cyph"))
			return fileName;
		else
			return fileName+".cyph"; 
	}

	// fetch list of files in Library diectory
	// Choose an easy interface, but DirectoryStream can be iterated over directly
	public Vector<String> fileNames() throws Exception {
		Vector<String> names = new Vector<String>();

		DirectoryStream<Path> stream = Files.newDirectoryStream(libraryDirectoryPath, "*.cyph");
		for (Path file: stream) {
			names.add(file.getFileName().toString());
		}

		stream.close();
		return names;
	}
	public void installFile(String filePath) throws Exception {
		Path source = Paths.get(filePath);
		if (Files.notExists(source))
			throw new Exception("File not found");

		Path destination = libraryDirectoryPath.resolve(source.getFileName());
		// produces FileExistsException when the cyph file is already in the library
		Files.copy(source, destination, StandardCopyOption.COPY_ATTRIBUTES);
	}
	public void getFile(String fileName, String targetDirectory) throws Exception {
		Path source = libraryDirectoryPath.resolve(addCyphExtension(fileName));
		if (Files.notExists(source))
			throw new Exception("File not in library");
		Path destination = Paths.get(targetDirectory).resolve(fileName);
		Files.copy(source, destination, StandardCopyOption.COPY_ATTRIBUTES);
	}
	public void removeFile(String fileName) throws Exception {
		Path source = libraryDirectoryPath.resolve(addCyphExtension(fileName));
		if (Files.notExists(source))
			throw new Exception("File not in library");
		source.toFile().delete();
	}
	public CipherSequence loadSequence(String fileName) throws Exception {
		Path source = libraryDirectoryPath.resolve(addCyphExtension(fileName));
		if (Files.notExists(source))
			throw new Exception("File not in library");

		CipherSequence result = new CipherSequence();
		result.loadFromFile(source.toString());
			
		return result;
	}
	public void saveSequence(CipherSequence seq, String fileName, boolean overwrite) throws Exception {
		Path destination = libraryDirectoryPath.resolve(addCyphExtension(fileName));
		if (!overwrite && Files.exists(destination))
			throw new Exception("Attempting to overwite cyph file");

		seq.saveToFile(destination.toString());
	}
}
