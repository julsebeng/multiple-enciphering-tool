import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by jse13 on 10/13/16.
 */
public class lb_appTest {

	/* ***********************************************************
	 * Single parameter tests
	/*************************************************************/

	@Test
	public void testEnc() {
		String[] testArgs = new String[] {"enc"};

		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			assertEquals("No cipher was given.", testErr);
		}
	}

	@Test
	public void testEncode() {
		String[] testArgs = new String[] {"encode"};

		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			assertEquals("No cipher was given.", testErr);
		}
	}

	@Test
	public void testDec() {
		String[] testArgs = new String[] {"dec"};

		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			assertEquals("No cipher was given.", testErr);
		}
	}

	@Test
	public void testDecode() {
		String[] testArgs = new String[] {"decode"};

		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			assertEquals("No cipher was given.", testErr);
		}
	}

	/* ***********************************************************
	 * Actually useful tests
	/*************************************************************/

	/* Created by Julian Engel */
	@Test
	public void test_1() {
		String[] testArgs = new String[] {"enc", "-o", "output.txt", "-i", "input.txt", "-f", "test.cyph"};

		int error = 0;
		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			error = 1;
			System.out.println(testErr);
		}

		assertEquals(0, error);
	}

	@Test
	public void test_2() {
		String[] testArgs = new String[] {"enc", "-o", "output.txt", "-i", "input.txt", "-f", "test.cyph"};

		int error = 0;
		try {
			lb_app.main(testArgs);
		}
		catch(Exception e) {
			String testErr = e.getMessage();
			error = 1;
			System.out.println(testErr);
		}

		assertEquals(0, error);
	}
}
