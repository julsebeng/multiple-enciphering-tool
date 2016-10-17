
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

//Pair programming session: Joseph Auguste, Thai Flowers

class BookCipher extends Cipher{
	private String filepath;
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
		char character;
		int numCollumns=80;	//collumns per page/rows
		int numRows=100;	//rows per page
		int collumn=1;
		int row=0;
		int currentpage=0;

		for(int i=0;i<input.length();i++) {
			character = input.charAt(i);
			char buffchar;
			for (int cur_byte=0; cur_byte<reader.length(); cur_byte++) {
				buffchar = (char)reader.read();
				collumn=collumn % numCollumns;
				//you should probably make sure you set collumn to numcollumns and vs versa for rows when finishing at 0(possibly);
				if(collumn==1)
					row++;
				row=row % numRows; if(row==1)
					currentpage++;
				if(buffchar == character)
					break;
			}

			output.append(currentpage + "," + row + "," + collumn + " ");
		}

		reader.close();
		return output.toString().substring(0,output.length()-2);
	}
	
	public String decrypt(String input)
	{return input;}
}

	
