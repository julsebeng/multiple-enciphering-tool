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

    @Test
    public void test_o() {
        String[] testArgs = new String[] {"-o", "file"};

        try {
            lb_app.main(testArgs);
        }
        catch(Exception e) {
            String testErr = e.getMessage();
            assertEquals("Invalid arguments.", testErr);
        }

    }

    /* ***********************************************************
     * Actually useful tests
    /*************************************************************/

    public void test_j() {
        String[] testArgs = new String[] {"-o", };

        try {
            lb_app.main(testArgs);
        }
        catch(Exception e) {
            String testErr = e.getMessage();
            assertEquals("Invalid arguments.", testErr);
        }

    }


}