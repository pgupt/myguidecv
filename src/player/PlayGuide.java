package player;

import org.sikuli.guide.Guide;
import org.sikuli.script.Screen;
import common.*;

public class PlayGuide {
	DB db;
	
	
	public void fetchAndPlay(String guideTitle) {
		db = new DB();
		db.queryGuideInfo(guideTitle);
		
	}

}
