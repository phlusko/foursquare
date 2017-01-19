package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SplashScreen  implements Screen {

	OrthographicCamera camera;
	final PhoarSquare game;
	FlashingFonts flasher;
	boolean backReset = false;
	int width = Gdx.graphics.getWidth();
	int height = Gdx.graphics.getHeight();
	
	public SplashScreen(final PhoarSquare arg0) {
		this.game = arg0;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 480);
        flasher = new FlashingFonts();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.med_tit.setColor(Color.WHITE);	
		game.med_tit.draw(game.batch, "Four Square", 50, 440);
		game.med_tit.draw(game.batch, "   Ball Game", 50, 360);
		
		game.small_font.setColor(Color.WHITE);
		//game.small_font.draw(game.batch, "by Ramenman Studios 2014-2016", 20, 200);
		game.med_tit.setColor(flasher.getCurrentColor());	
		game.med_tit.draw(game.batch, "     [play]", 30, 100);
		game.gearSprite.setPosition(900, 20);
		game.gearSprite.setSize(75f, 75f);
		game.gearSprite.draw(game.batch);
		game.batch.end();


		if (Gdx.input.justTouched()) {
			if (Gdx.input.getX() >= 900 * ((float)width / 1000f) && Gdx.input.getY() >= 380 * ((float)height / 480f)) {
				//game.setScreen(new PhoarSquareGame(game, true));
				//Gdx.app.log("phlusko", Gdx.input.getX() + " , " + Gdx.input.getY() + " settings");
				game.setScreen(new SettingsScreen(game));
			} else {
				game.setScreen(new PhoarSquareGame(game, true));
				//Gdx.app.log("phlusko", (900 * ((float)width / 1000f)) + " , " +  + " play");
			 	//Gdx.app.log("phlusko", Gdx.input.getX() + " , " + Gdx.input.getY() + " play");
			}

			//dispose();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
			if (backReset) {
				Gdx.app.exit();
			}
		} else {
			backReset = true;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}