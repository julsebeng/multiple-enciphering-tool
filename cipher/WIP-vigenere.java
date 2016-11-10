public class VigenereCipher {
	
	//see wiki for how a vigenere cipher works, otherwise this will not make much sense I imagine
	//not sure if this compiles yet, but i think it should work
	
    public static void main(String[] args) {
		//here is a test example how it should work
        String key = "PASSWORD";
        String message = "My first highly secure encryption hope all goes well";
        String enc = encipher(message, key);
        System.out.println(enc);
        System.out.println(decipher(enc, key));
    }
 
    static String encipher(String message, final String key) {	//final just means it always contains same value
        String temp = "";				//the new message to be
        message = message.toUpperCase();	//makes things easier for now....but will not preserve case ofc
        for (int i = 0, j = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c < 'A' || c > 'Z') continue;//if character is not upper case A-Z...skip. this does skip spaces atm
            temp += (char)(((c - 'A') + (key.charAt(j) - 'A')) % 26 + 'A'); 
			//A-Z ascii is values 65-90. want to add letters together, but has to wrap
			//back to beginning after 90. subtract 65 via 'A', so modulo works, and add two letters together.
			//then it gets modulo'd (is that a word?) and add 65 back. then cast to char.
            j = ++j % key.length();
        }
        return temp;
    }
 
    static String decipher(String message, final String key) {
        String temp = "";
        message = message.toUpperCase();
        for (int i = 0, j = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            temp += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return temp;
    }
}