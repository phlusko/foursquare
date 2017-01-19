package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StudioScreen  implements Screen {

	OrthographicCamera camera;
	final PhoarSquare game;
	long open_time;
	Sprite sprite;
	float alpha = 0;
	int delta = 150;
	int delta_count = 0;
	
	public StudioScreen(final PhoarSquare arg0) {
		this.game = arg0;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 480);
        open_time = System.currentTimeMillis();
        sprite = new Sprite(new Texture(Gdx.files.internal("data/ramenman.png")));
        sprite.setPosition(0, -480f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(255, 255, 255, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		sprite.setColor(1f, 1f, 1f, alpha);
		sprite.draw(game.batch);
		//game.text.draw(game.batch, "Ramenman Studios", 350, 280);
		//game.text.draw(game.batch, "   presents", 350, 240);
		String level_string = "";
		game.batch.end();
		logic();

	}
	public void logic() {
		if (alpha < 1f) {
			alpha += .05f;
		} else {
			alpha = 1f;
		}
		if (this.delta_count > this.delta) {
			this.game.setScreen(new SplashScreen(this.game));
			//this.game.setScreen(new WKStoreScreen(this.game));
		}
		this.delta_count++;
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