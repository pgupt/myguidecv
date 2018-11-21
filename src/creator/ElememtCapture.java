package creator;

import org.sikuli.script.*;

// import javafx.scene.shape.Rectangle;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.awt.*;
import javax.imageio.ImageIO;

public class ElememtCapture {
	/*
	 * This method takes screenshot of desktop
	 */
	public void screenshot() throws AWTException, IOException {
		
		Robot capture = new Robot();
		Rectangle rect  = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage buf  = capture.createScreenCapture(rect);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		//ImageIO.write(buf, "png", new File(""+t.getTime()+".png"));
		ImageIO.write(buf, "png", new File("screenshot.png"));

		System.out.println("screenshot has been captured");
		//should probably add a mouse listener event and caputre positon x and y of mouse	
	}
	
	public BufferedImage cropImage(String imageFile, javafx.scene.shape.Rectangle rect, String guideName, int stepCount) {
		try {
			BufferedImage image = ImageIO.read(new File(imageFile));
			BufferedImage subImage = image.getSubimage((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
			// Timestamp t = new Timestamp(System.currentTimeMillis()); // implementing stepCount instead
			String croppedImageFilename = "./Guides/"+ guideName+"/"+stepCount+".png";
			ImageIO.write(subImage, "png", new File(croppedImageFilename));
			System.out.println("File created: "+croppedImageFilename);
			return subImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	

}
