
import cipher.*;

import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;

class CipherSequenceTest {
	public static void main(String[] args) throws Exception {
		String resourcePath = "./test-library";

		System.out.println();
		System.out.println("Testing loading and encrypt/decrypt");

		CipherSequence tstSeq = new CipherSequence(resourcePath);
		tstSeq.loadFromFile("test.cyph");

		String source = new String("Hello World");
		String test = tstSeq.encrypt(source);
		String out  = tstSeq.decrypt(test);
		System.out.println(test);
		System.out.println(out);

		if (out.equals(source))
			System.out.println("Ok.");
		else
			System.out.println("FAIL");

		System.out.println();
		System.out.println("Testing Saving to file");
		// clear contents so previous test doesn't disturb current
		PrintWriter writer = new PrintWriter("test2.cyph");
		writer.print("");
		writer.close();
		tstSeq.saveToFile("test2.cyph");

		byte[] f1 = Files.readAllBytes(Paths.get("./test.cyph"));
		byte[] f2 = Files.readAllBytes(Paths.get("./test2.cyph"));
		if (Arrays.equals(f1,f2))
			System.out.println("Ok.");
		else
			System.out.println("FAIL");

		System.out.println();
		System.out.println("***Testing programmatic CipherSequence editing/creation***");

		System.out.println();
		System.out.println("First Build a new sequence from scratch");
		System.out.println("Tests add() insertAt() and setAt()");
		CipherSequence newSeq = new CipherSequence(resourcePath);
		newSeq.add(new DummyCipher());
		newSeq.add(new DummyCipher());
		newSeq.insertAt(new Rot13Cipher(), 0);
		newSeq.insertAt(new RotNCipher(2), 1);
		newSeq.setAt(new SubstitutionCipher("13579zyxwvutsrqponmlkjihgfedcba02468"), 1);

		/*
		writer = new PrintWriter("test2.cyph");
		writer.print("");
		writer.close();
		newSeq.saveToFile("test2.cyph");
		*/

		test = newSeq.encrypt(source);
		out  = newSeq.decrypt(test);

		System.out.println(source);
		System.out.println(out);
		if (out.equals(source))
			System.out.println("Ok.");
		else
			System.out.println("FAIL");


		System.out.println();
		System.out.println("Copy sequence and delete a few operations");
		System.out.println("Tests clone() removeLast() and removeAt()");
		CipherSequence cpySeq = new CipherSequence(newSeq);
		//cpySeq.removeAt(cpySeq.size()-1);
		cpySeq.removeLast();
		cpySeq.removeAt(0);

		for (int i=0; i<cpySeq.size(); i++)
			System.out.println(cpySeq.getAt(i).getName());

		if (cpySeq.size() == (newSeq.size() - 2))
			System.out.println("Ok.");
		else
			System.out.println("FAIL");

		System.out.println();
		System.out.println("Make sure copy can en/decrypt as well");

		test = cpySeq.encrypt(source);
		out  = cpySeq.decrypt(test);

		System.out.println(source);
		System.out.println(out);
		if (out.equals(source))
			System.out.println("Ok.");
		else
			System.out.println("FAIL");
	}
}
