package ui;

import org.sikuli.script.*;

// import static org.junit.Assert.*;

public class DemoTest {

public void demo() {

        Screen s = new Screen();

     try {

            s.click("tourImages/8.png");

            s.click("tourImages/multiply.png");

            s.click("tourImages/3.png");

            s.click("tourImages/equals.png");

            //assertNotNull("Verify correct value", s.exists("images/result.png"));     

        } catch (FindFailed e) {     

            e.printStackTrace();

        }

    }

}