
// Instantiates a Cipher subclass from the name of the Cipher
// at this point it only calls the 0 argument default constructor
//
// Later it will call an init() function that takes configuration options stored as a space delineated string

class CoreCipherClassLibrary {
	public static Cipher instantiateFromString(String cipherName, String args[]) throws Exception {
		try { // uses the static forName function of Class to load the named class, and then calls that classes newInstance() method to instantiate it
			Cipher myCipher = (Cipher)Class.forName(cipherName+"Cipher").newInstance();
			myCipher.init(args);
			return myCipher;
		}
		catch (ClassNotFoundException ex) {
			throw new ClassNotFoundException(cipherName + " is not a valid Cipher");
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	public static Cipher instantiateFromString(String cipherName) throws Exception {
		String[] args = {""};
		return instantiateFromString(cipherName, args);
	}
}
