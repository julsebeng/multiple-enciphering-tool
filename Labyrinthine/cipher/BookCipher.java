package cipher;

import java.util.Vector;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//Pair programming session: Joseph Auguste, Thai Flowers

// Only works with UTF-8 files, unless you want to do some major hacking!
// Also, assumes all needed chars are in the given book
// That is if your book is Dante's Inferno in English, you can't handle Chinese with it
public class BookCipher extends Cipher{
	private String fileName;	// path to file / filename
	private int numCollumns=80;	// collumns per page/rows
	private int numRows=100;	// rows per page
	public BookCipher() {
		name = "Book";
		resources = true;
		System.out.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	public BookCipher(String arg) {
		name = "Book";
		version = 1.0f;
		unicode = false;
		resources = true;
		fileName = arg;
		if (!fileName.endsWith(".txt"))
			fileName = fileName + ".txt";
	}
	public BookCipher(BookCipher other) {
		super(other);
		fileName = other.fileName;
		resourcePath = other.resourcePath;
	}
	@Override
	public Object clone() {
		return new BookCipher(this);
	}
	@Override
	public void init(String[] args) {
		fileName = args[0];
		if (!fileName.endsWith(".txt"))
			fileName = fileName + ".txt";
	}
	@Override
	public boolean equals(Cipher other) {
		try {
			BookCipher book = (BookCipher)other;
			return this.fileName.equals(book.fileName);
		}
		catch (Exception ex) {
			return false;
		}
	}
	@Override
	public String getArgsString() {
		return fileName;
	}
	@Override
	public String encrypt(String input) throws Exception {
		String filePath = resourcePath+File.separator+fileName;

		try (RandomAccessFile reader = new RandomAccessFile(filePath, "r"))
		{
			StringBuilder output= new StringBuilder("");
			for(int i=0; i<input.length(); i++) {
				char character = input.charAt(i);
				char buffchar;

				int collumn=0;
				int row=1;
				int currentpage=1;

				for (int cur_byte=0; cur_byte<reader.length(); cur_byte++) {
					buffchar = (char)reader.read();
					collumn++;
					if (collumn == numCollumns) {
						row++;
						collumn = 0;
					}
					if (row == numRows) {
						currentpage++;
						row = 1;
					}
					if(buffchar == character)
						break;
				}

				output.append(currentpage + "," + row + "," + collumn + ",");
				reader.seek(0);
			}

			return output.toString().substring(0,output.length()-1);
		}
	}
	@Override
	public String decrypt(String input) throws Exception {
		String filePath = resourcePath+File.separator+fileName;

		try (RandomAccessFile reader = new RandomAccessFile(filePath, "r"))
		{
			StringBuilder output = new StringBuilder();

			Pattern pat = Pattern.compile(",");
			String[] matches = pat.split(input);

			/*
			if (matches.length != (input.length()/3))
				throw new Exception("Invalid BookCipher input");
			*/
			
			int seekOffset=0;
			int page, row, collumn;

			for (int i=0; i<matches.length; i+=3) {

				reader.seek(0);

				page = Integer.parseInt(matches[i]);
				row  = Integer.parseInt(matches[i+1]);
				collumn = Integer.parseInt(matches[i+2]);
				
				// "H" should be 1,1,19 but this returns value at 1,1,32
				seekOffset = 	  ((page-1) * (numCollumns * numRows)) // page * sizeof(page)
						+ ((row-1) * numCollumns) // row * sizeof(row)
						+ collumn
						-1; // seek is 0 offset, this is 1 offset
				
				reader.seek(seekOffset);
				output.append((char)reader.read());
			}
			return output.toString();
		}
	}
}
