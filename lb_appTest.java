import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by jse13 on 10/13/16.
 */
public class lb_appTest {

    @Test
    public void test() {
        String[] testArgs = new String[] {"enc"};

        try {
            lb_app.main(testArgs);
        }
        catch(Exception e) {
            String testErr = e.getMessage();
            assertEquals("No cipher was given.", testErr);
        }
    }
}