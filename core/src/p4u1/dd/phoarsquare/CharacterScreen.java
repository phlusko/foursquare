package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;



/**
 * Created by Paul on 12/15/2016.
 */

public class CharacterScreen implements Screen{
    PhoarSquare game;
    OrthographicCamera camera;
    FlashingFonts flasher;
    CustomWalker walker;
    Face currFace;
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    public CharacterScreen(PhoarSquare arg0) {
        game = arg0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 480);
        flasher = new FlashingFonts();
        walker = new CustomWalker();
        currFace = new Face((int)(Math.random()*walker.eyesTextures.length), (int)(Math.random()*walker.browsTextures.length), (int)(Math.random()*walker.mouthsTextures.length), (int)(Math.random()*walker.nosesTextures.length), (int)(Math.random()*walker.skinColors.length), (int)(Math.random()*walker.eyeColors.length), true);
        Preferences prefs = Gdx.app.getPreferences("face");

        if(prefs.contains("hair")) {
            //currFace = blah
       //     brows = prefs.getInteger("brows");
        } else {
           // currFace = new Face((int)(Math.random()*walker.eyesTextures.length), (int)(Math.random()*walker.browsTextures.length), (int)(Math.random()*walker.mouthsTextures.length), (int)(Math.random()*walker.nosesTextures.length), (int)(Math.random()*walker.skinColors.length), (int)(Math.random()*walker.eyeColors.length), true);
        }



    }
    @Override
    public void show() {

    }
    long last_time = System.currentTimeMillis();
    int frame = 0;
    boolean features = true;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        long curr_time = System.currentTimeMillis();
        if (curr_time - last_time > 125) {
            frame++;
            last_time = curr_time;
            if (frame == 4) {
                frame = 0;
            }
        }
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.med_tit.setColor(flasher.getCurrentColor());
        game.med_tit.draw(game.batch, "Edit Character", 50, 440);
        game.med_tit.setColor(Color.WHITE);
        if (features) {
            game.med_tit.setColor(Color.RED);
        }
        game.med_tit.draw(game.batch, "Features", 50, 370);
        game.med_tit.setColor(Color.WHITE);
        if (!features) {
            game.med_tit.setColor(Color.RED);
        }
        game.med_tit.draw(game.batch, "Colors", 550, 370);
        walker.drawMe(game.batch, 120, 150, currFace, frame);
        game.back_arrow.setPosition(900, 20);
        game.back_arrow.setSize(75, 75);
        game.back_arrow.draw(game.batch);
        game.batch.end();
        if (features) {
            featuresRender();
        } else {
            colorsRender();
        }
        if (Gdx.input.justTouched()) {
            int x = convertX(Gdx.input.getX());
            int y = convertY(Gdx.input.getY());
            Gdx.app.log("phlusko", x +" , " + y);
            if (y <=370 && y >= 325) {
                if (x >= 50 && x <= 470) {
                    features = true;
                }
                if (x >= 550 && x <= 875) {
                    features = false;
                }
            }
        }

    }

    public void colorsRender() {
        game.batch.begin();
        game.med_tit.setColor(Color.WHITE);
        game.med_tit.draw(game.batch, "< Hair", 400, 300);
        game.med_tit.draw(game.batch, "< Body", 400, 250);
        game.med_tit.draw(game.batch, "< Belt", 400, 200);
        game.med_tit.draw(game.batch, "< Legs", 400, 150);
        game.med_tit.draw(game.batch, "< Feet", 400, 100);
        game.med_tit.draw(game.batch, "< Feet", 400, 50);
        game.med_tit.draw(game.batch, ">", 760, 50);
        game.med_tit.draw(game.batch, ">", 760, 100);
        game.med_tit.draw(game.batch, ">", 760, 150);
        game.med_tit.draw(game.batch, ">", 760, 200);
        game.med_tit.draw(game.batch, ">", 760, 250);
        game.med_tit.draw(game.batch, ">", 760, 300);
        game.batch.end();
        if (Gdx.input.justTouched()) {
            int x, y;
            x = convertX(Gdx.input.getX());
            y = convertY(Gdx.input.getY());
            boolean goButtons = false;
            int change = 1;
            if (x >= 400 && x <= 450) {
                goButtons = true;
                change = -1;
            }
            if (x >= 760 && x <= 810) {
                goButtons = true;
                change = 1;
            }
            if (goButtons) {
                if (y <= 300 && y >= 255) {
                    currFace.skinColor += change;
                    if (currFace.hairColor < 0) {
                        currFace.skinColor = walker.skinColors.length - 1;
                    }
                    if (currFace.skinColor >= walker.skinColors.length) {
                        currFace.skinColor = 0;
                    }
                }
            }
        }
    }

    public void featuresRender() {
        game.batch.begin();
        Color blah = Color.valueOf("ffffff");
        game.med_tit.setColor(blah);
        game.med_tit.draw(game.batch, "< Skin", 400, 300);
        game.med_tit.draw(game.batch, "< Hair", 400, 250);
        game.med_tit.draw(game.batch, "< Brow", 400, 200);
        game.med_tit.draw(game.batch, "< Eyes", 400, 150);
        game.med_tit.draw(game.batch, "< Nose", 400, 100);
        game.med_tit.draw(game.batch, "< Lips", 400, 50);
        game.med_tit.draw(game.batch, ">", 760, 50);
        game.med_tit.draw(game.batch, ">", 760, 100);
        game.med_tit.draw(game.batch, ">", 760, 150);
        game.med_tit.draw(game.batch, ">", 760, 200);
        game.med_tit.draw(game.batch, ">", 760, 250);
        game.med_tit.draw(game.batch, ">", 760, 300);


        game.batch.end();
        if (Gdx.input.justTouched()) {
            int x, y;
            x = convertX(Gdx.input.getX());
            y = convertY(Gdx.input.getY());
            boolean goButtons = false;
            int change = 1;
            if (x >= 400 && x <= 450) {
                goButtons = true;
                change = -1;
            }
            if (x >= 760 && x <= 810) {
                goButtons = true;
                change = 1;
            }
            if (goButtons) {
                if (y <= 300 && y >= 255) {
                    Gdx.app.log("phlusko", "skin");
                    currFace.skinColor += change;
                    if (currFace.skinColor < 0) {
                        currFace.skinColor = walker.skinColors.length - 1;
                    }
                    if (currFace.skinColor >= walker.skinColors.length){
                        currFace.skinColor = 0;
                    }
                } else if (y <= 250 && y >= 205) {
                    Gdx.app.log("phlusko", "hair");
                    currFace.hair += change;
                    if (currFace.hair< 0) {
                        currFace.hair = walker.maleHairSprites.length - 1;
                    }
                    if (currFace.hair >= walker.maleHairSprites.length){
                        currFace.hair = 0;
                    }
                } else if (y <= 200 && y >= 155) {
                    currFace.brows += change;
                    if (currFace.brows < 0) {
                        currFace.brows = walker.browsTextures.length - 1;
                    }
                    if (currFace.brows >= walker.browsSprites.length){
                        currFace.brows = 0;
                    }
                    Gdx.app.log("phlusko", "brow");
                } else if (y <= 150 && y >= 105) {
                    Gdx.app.log("phlusko", "eyes");
                    currFace.eyes += change;
                    if (currFace.eyes< 0) {
                        currFace.eyes = walker.eyesSprites.length - 1;
                    }
                    if (currFace.eyes >= walker.eyesSprites.length){
                        currFace.eyes = 0;
                    }
                } else if (y <= 100 && y >= 55) {
                    Gdx.app.log("phlusko", "nose");
                    currFace.nose += change;
                    if (currFace.nose< 0) {
                        currFace.nose = walker.nosesSprites.length - 1;
                    }
                    if (currFace.nose >= walker.nosesSprites.length){
                        currFace.nose = 0;
                    }
                } else if (y <= 50 && y >= 5) {
                    currFace.mouth += change;
                    if (currFace.mouth< 0) {
                        currFace.mouth = walker.mouthsSprites.length - 1;
                    }
                    if (currFace.mouth >= walker.mouthsSprites.length){
                        currFace.mouth = 0;
                    }
                    Gdx.app.log("phlusko", "lips");
                }
            }
        }
    }

    public int convertX(int arg0) {
        return (int)((1000f / (float)width) * arg0);
    }

    public int convertY(int arg0) {
        return (480 - (int)((480f / (float)height) * arg0));
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
