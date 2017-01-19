package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Paul on 12/15/2016.
 */

public class SettingsScreen implements Screen {
    OrthographicCamera camera;
    FlashingFonts flasher;
    PhoarSquare game;
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    public SettingsScreen(final PhoarSquare arg0) {
        Gdx.input.setCatchBackKey(true);
        game = arg0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 480);
        flasher = new FlashingFonts();
    }

    @Override
    public void show() {

    }
    boolean backReset = false;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.med_tit.setColor(flasher.getCurrentColor());
        game.med_tit.draw(game.batch, "Settings", 50, 440);
        game.back_arrow.setPosition(900, 20);
        game.back_arrow.setSize(75, 75);
        game.back_arrow.draw(game.batch);
        game.batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            if (backReset) {
                game.setScreen(new SplashScreen(game));
            }
        } else {
            backReset = true;
        }
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() >= 900 * ((float) width / 1000f) && Gdx.input.getY() >= 380 * ((float) height / 480f)) {
                //game.setScreen(new PhoarSquareGame(game, true));
                //Gdx.app.log("phlusko", Gdx.input.getX() + " , " + Gdx.input.getY() + " settings");
                game.setScreen(new SplashScreen(game));
            } else {
                game.setScreen(new CharacterScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
