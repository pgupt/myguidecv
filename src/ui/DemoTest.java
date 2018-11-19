package ui;

import org.sikuli.script.*;
import java.util.*;
// import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.awt.*;
import javax.imageio.ImageIO;
public class DemoTest {

	public void demo() {

        Screen s = new Screen();

        try {
            s.click("tourImages/8.png");
            s.click("tourImages/multiply.png");
            s.click("tourImages/3.png");
            s.click("tourImages/equals.png");
            //assertNotNull("Verify correct value", s.exists("images/result.png"));     

        }catch (FindFailed e) {     
            e.printStackTrace();
        }
    }
	public void snipTool() throws AWTException, IOException {
		Robot capture = new Robot();
		Rectangle rect  = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage buf  = capture.createScreenCapture(rect);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		ImageIO.write(buf, "png", new File(""+t.getTime()+".png"));
		System.out.println("screen has been captured");
		//should probably add a mouse listener event and caputre positon x and y of mouse
		
	}

}