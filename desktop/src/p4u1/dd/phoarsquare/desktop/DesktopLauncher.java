package p4u1.dd.phoarsquare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import p4u1.dd.phoarsquare.PhoarSquare;
import p4u1.dd.phoarsquare.PhoarSquareGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PhoarSquare(), config);
	}
}
