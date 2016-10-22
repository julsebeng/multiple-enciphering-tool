JFLAGS = -g
JC = javac

# clear default targets for building .java and .class files
.SUFFIXES: .java .class

# set our own default targets
# map from .java files to .class files, $* is basename of current target
.java.class:
	$(JC) $(JFLAGS) $*.java

LIB-CLASSES = \
	Cipher.java \
	CipherSequence.java \
	CipherSequenceLibrary.java \
	CoreCipherClassLibrary.java

# typing 'make' will compile app, otherwise first listed target is used
default: app

app: lib lb_app.java
	$(JC) lb_app.java

# Make sure each .class associated with each .java is present
# Take class names in LIB-CLASSES and substitute .java for .class
# No body, b/c it uses the default target defined above
lib: $(LIB-CLASSES:.java=.class)

buildtest:
	$(JC) -cp .:junit.jar:hamcrest-core.jar lb_appTest.java
	
runtest:
	java -cp .:junit.jar:hamcrest-core.jar org.junit.runner.JUnitCore lb_appTest
	
testprep:
	wget 'http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar' -O  junit.jar
	wget 'http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar' -O 'hamcrest-core.jar'


# Ad-hoc tests
AHT-CLASSES = \
	CipherTest.java \
	CipherSequenceTest.java
	#CipherSequenceLibraryTest.java

ad-hoc-tests: $(AHT-CLASSES:.java=.class)
	@echo "These tests generally require input, or recommend viewing source while running"
	@echo "Run these manually like so:"
	@echo "	java CipherTest <Cipher Name> <Cipher args>"
	@echo "	java CipherSequenceTest"

clean:
	$(RM) *.class
