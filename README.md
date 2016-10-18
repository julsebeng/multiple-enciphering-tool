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


## Acceptance Tests
####Acceptance tests for CLI
Using the provided `input.txt` and `test.cyph`:

```
java lb_app -i input.txt -f text.cyph enc
```
should print this line to the terminal:
```
Zkld ld a qfddaif zkaz vffrd zb gf fvsbrfr.
```
### Inputs -> Expected Outputs
* Foo -> Bar
