# multiple-enciphering-tool
Repo for CEN4020 2016 project
## Created by:
* Nicholas Purdy
* Joseph Auguste
* Thai Flowers
* Christopher Pasquith
* Sergio Andres
* Julian Engel
* Keaton Clements
* Kris Stolarczyk

## Compiling Labyrinthine
####To compile the CLI
1. Run `make clean`
2. Run `make app`

## Running Labyrinthine
####To run the CLI:
The CLI is invoked by running `java lb_app`. Please check the wiki for what
flags are supported.

## Running the Unit Test Cases
Building the JUnit test cases relies on two assumptions:
1. The current JUnit jar is named `junit.jar` and the curren Hamcrest jar is named `hamcrest-core.jar`.
2. Both jar files are located in the same directory as the source files.
So in order to run the test cases at all, you can just run:
```
make testprep
```
Or, to install the JUnit files manually:
```
wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar && mv junit-4.12.jar junit.jar
wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar && mv hamcrest-core-1.3.jar hamcrest-core.jar
```
Next, run `make buildtest` to compile the test cases.
####Running the CLI tests
To test `lb_app`, Simply run:
```
make runtest
```


## Acceptance Tests
####Acceptance tests for CLI
Using the provided `input.txt` and `test.cyph`:

```
java lb_app -i input.txt -f test.cyph enc
```
should print this line to the terminal:
```
Zkld ld a qfddaif zkaz vffrd zb gf fvsbrfr.
```
-
Using the provided `output.txt` and `test.cyph`:

```
java lb_app -i output.txt -f test.cyph dec
```
should print this line to the terminal:
```
This is a message that needs to be encoded.
```
-

Using the provided `input2.txt`, which is a large amount of text, and `test.cyph`:

```
java lb_app enc -i input.txt -f -o out.txt test.cyph
```

Should output a lengthy amount of code to the file `out.txt`. `out.txt` does not need to exist prior to running the command. The output should match that of the provided `output2.txt`.
-
Using the provided `test.cyph`:
```
java lb_app enc -f test.cyph
```
Will wait for input from `stdin` and output the encrypted sequence to `stdout`.
