


import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Vector;
//Pair programming session: Joseph Auguste, Thai Flowers


class BookCipher extends Cipher{
	private String filepath;
	BookCipher()
	{
		name = "BookCipher";
		
		System.out.println("Warning: using 0 arg construtor, make sure to follow with init() with valid args array before use!");
	}
	BookCipher(String arg)
	{
		name = "BookCipher";
		version = 1.0f;
		unicode = false;
		filepath = new String(arg);
	}
	//Bookcipher(Bookcipher other)
	


	
	
	


	
	public String encrypt(String input) throws Exception
	{
		FileReader fr = new FileReader(filepath); // load file as stream
		BufferedReader reader = new BufferedReader(fr); // allow for quick line (block) loading
		
		String output= new String();
		char character;
		int numCollumns=80;//collumns per page/rows
		int numRows=100;//rows per page
		int collumn=1;
		int row=0;
		int currentpage=0;
		

		for(int i=0;i<input.length();i++)
			{		
			reader.reset();
			character = input.charAt(i);
			char buffchar;
				while (((buffchar = (char)reader.read()) != -1) && (buffchar != character) )
				{

					collumn=collumn % numCollumns;
					//you should probably make sure you set collumn to numcollumns and vs versa for rows when finishing at 0(possibly);
					if(collumn==1)		row++;
					row=row % numRows;
					if(row==1)	currentpage++;
					if(buffchar == character)	break;
					
					
											
					
				}
				output.concat(Integer.toString(currentpage));
				output.concat(Character.toString(','));
				output.concat(Integer.toString(row));
				output.concat(Character.toString(','));
				output.concat(Integer.toString(collumn));
				output.concat(Character.toString(','));
				
			}
		reader.close();
		fr.close();
		return output;

	}
	
	public String decrypt(String input)
	{return new String("");}

}

	
