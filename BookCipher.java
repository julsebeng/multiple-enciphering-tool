
import java.util.Vector;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//Pair programming session: Joseph Auguste, Thai Flowers

class BookCipher extends Cipher{
	private String filepath;	// path to file / filename
	private int numCollumns=80;	// collumns per page/rows
	private int numRows=100;	// rows per page
	BookCipher() {
		name = "BookCipher";
		System.out.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	BookCipher(String arg) {
		name = "BookCipher";
		version = 1.0f;
		unicode = false;
		filepath = new String(arg);
	}
	BookCipher(BookCipher other) {
		super(other);
		filepath = new String(other.filepath);
	}
	public void init(String[] args) {
		filepath = new String(args[0]);
	}
	public String encrypt(String input) throws Exception {
		RandomAccessFile reader = new RandomAccessFile(filepath, "r");
		
		StringBuilder output= new StringBuilder("");
		for(int i=0;i<input.length();i++) {
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
					collumn = 1;
				}
				if (row == numRows) {
					currentpage++;
					row = 1;
				}
				if(buffchar == character)
					break;
			}

			output.append(currentpage + "," + row + "," + collumn + ",");
		}

		reader.close();
		return output.toString().substring(0,output.length()-1);
	}
	public String decrypt(String input) throws Exception {
		RandomAccessFile reader = new RandomAccessFile(filepath, "r");
		String output = new String();

		Pattern pat = Pattern.compile(",");
		String[] matches = pat.split(input);

		/*
		if (matches.length != (input.length()/3))
			throw new Exception("Invalid BookCipher input");
		*/
		
		int seekOffset=0;
		int page, row, collumn;

		for (int i=0; i<matches.length; i+=3) {

			page = Integer.parseInt(matches[i]);
			row  = Integer.parseInt(matches[i+1]);
			collumn = Integer.parseInt(matches[i+2]);
			
			// "H" should be 1,1,19 but this returns value at 1,1,32
			seekOffset = 	  ((page-1) * (numCollumns * numRows)) // page * sizeof(page)
					+ (row * numCollumns) // row * sizeof(row)
					+ collumn
					-1; // seek is 0 offset, this is 1 offset
			
			reader.seek(seekOffset);
			output += (char)reader.read();
		}
		return output;
	}
}
