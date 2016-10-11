
class CipherSequenceTest {
	public static void main(String[] args) throws Exception {
		CipherSequence tstSeq = new CipherSequence();
		tstSeq.loadFromFile("example1.cyph");

		System.out.println();
		System.out.println("Testing loading and encode/decode");
		String test = tstSeq.encrypt("Hello World");
		System.out.println(test);
		System.out.println(tstSeq.decrypt(test));

		System.out.println();
		System.out.println("Testing Saving to file");
		tstSeq.saveToFile("test.cyph");
	}
}
