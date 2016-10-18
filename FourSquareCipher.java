import java.util.*;
import java.lang.Character;

// FourSquareCipher requires 2 cipher keys be provided in order to encrypt or decrypt the text
// four square cipher treats i and j as the same character, this is necessary in order for the cipher to function
    //this may lead to decryption that does not exactly match the input
    // this is expected behavior of this type of cipher

public class FourSquareCipher extends Cipher {
    private String[] keys = new String[2];     // stores the keys for the Cipher
    private Character[][] gridOneOne = new Character[5][5];
    private Character[][] gridOneTwo = new Character[5][5];
    private Character[][] gridTwoOne = new Character[5][5];
    private Character[][] gridTwoTwo = new Character[5][5];

    /*public FourSquareCipher() {
        name = "FourSquareCipher";
        System.out.println("Warning: Invalid Use ");
    }*/

    public FourSquareCipher(String key1, String key2){
        keys[0] = key1.toUpperCase();
        keys[1] = key2.toUpperCase();
        name = "FourSquareCipher";
        unicode = false;
        version = 1.0f;
        // remove duplicate letters from the keys
        StringBuilder removedDuplicates = new StringBuilder();
        for(int x = 0; x < keys[0].length(); x++){
            String temp = keys[0].substring(x, x+1);
            if (removedDuplicates.indexOf(temp) == -1){
                removedDuplicates.append(temp);
            }
        }
        keys[0] = removedDuplicates.toString();

        removedDuplicates = new StringBuilder();    // reset the StringBuilder for the next key
        for(int x = 0; x < keys[1].length(); x++){
            String temp = keys[1].substring(x, x+1);
            if(removedDuplicates.indexOf(temp) == -1){
                removedDuplicates.append(temp);
            }
        }
        keys[1] = removedDuplicates.toString();

        List<Character> alphabetLower = Arrays.asList('a','b','c','d','e','f','g','h','i','k',
                'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
        List<Character> alphabetUpper = Arrays.asList('A','B','C','D','E','F','G','H','I','K',
                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

        // create the grids for the cipher here
        int count = 0;      // count tracks the position in alphabet

        // initialize the two grids that are not dependent on the keys
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                gridOneOne[x][y] = alphabetLower.get(count);
                gridTwoTwo[x][y] = alphabetLower.get(count);
                count++;
            }
        }

        //remove the elements that are in the first key from the upper alphabet
        for(int x = 0; x < keys[0].length(); x++){
            alphabetUpper.remove(keys[0].substring(x, x+1));
        }
        //initialize gridOneTwo using the first keyword
        count = 0;      //reset count
        Iterator alphaIterator = alphabetUpper.iterator();
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                if(count < keys[0].length()){
                    gridOneTwo[x][y] = keys[0].charAt(count);
                    count++;
                }
                else{
                    gridOneTwo[x][y] = alphaIterator.next().toString().charAt(0);
                }
            }
        }
        // reset alphabet upper for the second key
        alphabetUpper = Arrays.asList('A','B','C','D','E','F','G','H','I','K',
                'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
        // remove the elements that are in the second key
        for(int x = 0; x < keys[1].length(); x++){
            alphabetUpper.remove(keys[1].substring(x, x+1));
        }
        // initialize gridTwoOne using the second key
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                if(count < keys[1].length()){
                    gridTwoOne[x][y] = keys[1].charAt(count);
                    count++;
                }
                else{
                    gridTwoOne[x][y] = alphaIterator.next().toString().charAt(0);
                }
            }
        }
    }

    public FourSquareCipher(Cipher other) {
        super(other);
    }

    @Override
    String encrypt(String input) throws Exception {
        StringBuilder cipherOutput = new StringBuilder();
        // replace J's in the input as they are not supported by the cipher
        input = input.replace('J', 'I');
        input = input.replace('j', 'i');
        String inputCopy = input;
        inputCopy = inputCopy.trim().replace(" ", "");
        //if we will have an invalid digraph, add an X to the end of the input
        if(inputCopy.length() % 2 != 0){
            input = input + "X";
        }

        for(int x = 0; x < input.length(); x+=2){
            //case where current character is non alpha, append it and increment iterator x
            if(!Character.isLetter(input.substring(x, x+1).charAt(0))){
                cipherOutput.append(input.substring(x, x+1));
                x++;
            }
            // case where a non alpha character separates two letters in a digraph,
            // this appends it in the middle of the two but still takes the digraph correctly
            if(!Character.isLetter(input.substring(x+1, x+2).charAt(0))){
                int firstX = 0, firstY = 0, secondX = 0, secondY = 0;

                for(int i = 0; i < 5; i++){
                    for(int y = 0; y < 5; y++){
                        if(gridOneOne[x][y] == Character.toLowerCase(input.substring(x, x+1).charAt(0))){
                            firstX = i;
                            firstY = y;
                        }
                        if(gridTwoTwo[x][y] == Character.toLowerCase(input.substring(x+2, x+3).charAt(0))){
                            secondX = i;
                            secondY = y;
                        }
                    }
                }
                cipherOutput.append(gridOneTwo[firstX][secondY]).append(input.substring(x+1, x+2).charAt(0))
                        .append(gridTwoOne[secondX][firstY]);
                x++;        //increment x to account for the non alpha character
            }
            // final case is that we have to alpha characters side by side
            // take the digraph and encipher, append to cipherOutput
            else{
                int firstX = 0, firstY = 0, secondX = 0, secondY = 0;

                for(int i = 0; i < 5; i++){
                    for(int y = 0; y < 5; y++){
                        if(gridOneOne[x][y] == Character.toLowerCase(input.substring(x, x+1).charAt(0))){
                            firstX = i;
                            firstY = y;
                        }
                        if(gridTwoTwo[x][y] == Character.toLowerCase(input.substring(x+1, x+2).charAt(0))){
                            secondX = i;
                            secondY = y;
                        }
                    }
                }
                cipherOutput.append(gridOneTwo[firstX][secondY]).append(gridTwoOne[secondX][firstY]);
            }

        }
        return cipherOutput.toString();
    }

    @Override
    String decrypt(String input) throws Exception {
        StringBuilder cipherOutput = new StringBuilder();

        for(int x = 0; x < input.length(); x+=2){
            //case where current character is non alpha, append it and increment iterator x
            if(!Character.isLetter(input.substring(x, x+1).charAt(0))){
                cipherOutput.append(input.substring(x, x+1));
                x++;
            }
            // case where a non alpha character separates two letters in a digraph,
            // this appends it in the middle of the two but still takes the digraph correctly
            if(!Character.isLetter(input.substring(x+1, x+2).charAt(0))){
                int firstX = 0, firstY = 0, secondX = 0, secondY = 0;

                for(int i = 0; i < 5; i++){
                    for(int y = 0; y < 5; y++){
                        if(gridOneTwo[x][y] == Character.toUpperCase(input.substring(x, x+1).charAt(0))){
                            firstX = i;
                            firstY = y;
                        }
                        if(gridTwoOne[x][y] == Character.toUpperCase(input.substring(x+2, x+3).charAt(0))){
                            secondX = i;
                            secondY = y;
                        }
                    }
                }
                cipherOutput.append(gridOneOne[firstX][secondY]).append(input.substring(x+1, x+2).charAt(0))
                        .append(gridTwoTwo[secondX][firstY]);
                x++;        //increment x to account for the non alpha character
            }
            // final case is that we have to alpha characters side by side
            // take the digraph and encipher, append to cipherOutput
            else{
                int firstX = 0, firstY = 0, secondX = 0, secondY = 0;

                for(int i = 0; i < 5; i++){
                    for(int y = 0; y < 5; y++){
                        if(gridOneTwo[x][y] == Character.toUpperCase(input.substring(x, x+1).charAt(0))){
                            firstX = i;
                            firstY = y;
                        }
                        if(gridTwoOne[x][y] == Character.toUpperCase(input.substring(x+1, x+2).charAt(0))){
                            secondX = i;
                            secondY = y;
                        }
                    }
                }
                cipherOutput.append(gridOneOne[firstX][secondY]).append(gridTwoTwo[secondX][firstY]);
            }

        }
        return cipherOutput.toString();
    }
}
