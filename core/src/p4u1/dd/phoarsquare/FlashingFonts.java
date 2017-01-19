package p4u1.dd.phoarsquare;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;


public class FlashingFonts {
	
	ArrayList<Color> colors = new ArrayList<Color>();
	long last_time = System.currentTimeMillis();
	int currentColor = 0;
	static final int COLOR_DELAY = 75;
	public FlashingFonts() {
		colors.add(Color.ORANGE);
		colors.add(Color.MAGENTA);
		colors.add(Color.YELLOW);
		colors.add(Color.BLUE);
		colors.add(Color.RED);
		colors.add(Color.PINK);
	}
	
	public Color getCurrentColor(){
		long curr_time = System.currentTimeMillis();
		if (curr_time - last_time > FlashingFonts.COLOR_DELAY) {
			last_time = System.currentTimeMillis();
			currentColor++;
			if (currentColor == colors.size()) {
				currentColor = 0;
			}
		}
		return colors.get(currentColor);
	}
}