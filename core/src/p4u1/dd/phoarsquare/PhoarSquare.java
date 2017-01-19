package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PhoarSquare extends Game{
	SpriteBatch batch;
    BitmapFont font;
    BitmapFont sub_tit;
    BitmapFont med_tit;
    BitmapFont small_font;
    Texture gearTexture;
    Sprite gearSprite;
    Sprite back_arrow;
	@Override
	public void create() {
		// TODO Auto-generated method stub
        Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("data/ak_title.fnt"),
                false);
        med_tit = new BitmapFont(Gdx.files.internal("data/ak_med.fnt"),
                false);
        sub_tit = new BitmapFont(Gdx.files.internal("data/dejavu_sans_light.fnt"),
                false);
        small_font = new BitmapFont(Gdx.files.internal("data/ak_small.fnt"),
                false);
        gearTexture = new Texture(Gdx.files.internal("data/sprites/gear.png"));
        gearSprite = new Sprite(gearTexture);
        back_arrow = new Sprite(new Texture(Gdx.files.internal("data/sprites/back_arrow.png")));
		//this.setScreen(new SplashScreen(this));
        this.setScreen(new StudioScreen(this));
	}
	
	 public void render() {
         super.render(); //important!
	 }
}
