app: lib
	javac lb_app.java

lib:
	javac Cipher.java CipherSequence.java CipherSequenceLibrary.java CoreCipherClassLibrary.java

test:
	javac lb_appTest.java

clean:
	rm *.class
