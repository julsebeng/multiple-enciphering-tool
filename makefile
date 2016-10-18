app: lib
	javac lb_app.java

lib:
	javac Cipher.java CipherSequence.java CipherSequenceLibrary.java CoreCipherClassLibrary.java

buildtest:
	javac -cp .:junit.jar:hamcrest-core.jar lb_appTest.java
	
runtest:
	java -cp .:junit.jar:hamcrest-core.jar org.junit.runner.JUnitCore lb_appTest
	
testprep:
	wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar && mv junit-4.12.jar junit.jar
	wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar && mv hamcrest-core-1.3.jar hamcrest-core.jar

clean:
	rm *.class
