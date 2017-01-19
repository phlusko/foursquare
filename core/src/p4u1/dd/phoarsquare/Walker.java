package p4u1.dd.phoarsquare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//i have made a change

public class Walker {
	Sprite[][] sprites = new Sprite[4][3];
	Texture sprite_texture;
	TextureRegion sprite_region;
	
	Sprite[][] sprites2 = new Sprite[4][3];
	Texture sprite_texture2;
	TextureRegion sprite_region2;
	
	Sprite[][] sprites3 = new Sprite[4][3];
	Texture sprite_texture3;
	TextureRegion sprite_region3;
	
	Sprite[][] sprites4 = new Sprite[4][3];
	Texture sprite_texture4;
	TextureRegion sprite_region4;
	CustomWalker custom;
	
	
	public Walker () {
		custom = new CustomWalker();
		this.sprite_texture = new Texture(Gdx.files.internal("data/sprites/pol_sprite.png"));
		this.sprite_texture2 = new Texture(Gdx.files.internal("data/sprites/luna_sprite.png"));
		this.sprite_texture3 = new Texture(Gdx.files.internal("data/sprites/luna_sprite.png"));
		this.sprite_texture4 = new Texture(Gdx.files.internal("data/sprites/pol_sprite.png"));
		int i = 0;
		int di = 0;
		for(int j = 0; j < 4; j++) {
			this.sprite_region = new TextureRegion(this.sprite_texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites[j][i] = new Sprite(sprite_region);
			this.sprite_region2 = new TextureRegion(this.sprite_texture2, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites2[j][i] = new Sprite(sprite_region2);
			this.sprite_region3 = new TextureRegion(this.sprite_texture3, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites3[j][i] = new Sprite(sprite_region3);
			this.sprite_region4 = new TextureRegion(this.sprite_texture4, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites4[j][i] = new Sprite(sprite_region4);
		}
		i = 2;
		di = 2;
		for(int j = 0; j < 4; j++) {
			this.sprite_region = new TextureRegion(this.sprite_texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites[j][i - 1] = new Sprite(sprite_region);
			this.sprite_region2 = new TextureRegion(this.sprite_texture2, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites2[j][i - 1] = new Sprite(sprite_region2);
			this.sprite_region3 = new TextureRegion(this.sprite_texture3, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites3[j][i - 1] = new Sprite(sprite_region3);
			this.sprite_region4 = new TextureRegion(this.sprite_texture4, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites4[j][i - 1] = new Sprite(sprite_region4);
		}
		i = 3;
		di = 3;
		for(int j = 0; j < 4; j++) {
			this.sprite_region = new TextureRegion(this.sprite_texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites[j][i - 1] = new Sprite(sprite_region);
			this.sprite_region2 = new TextureRegion(this.sprite_texture2, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites2[j][i - 1] = new Sprite(sprite_region2);
			this.sprite_region3 = new TextureRegion(this.sprite_texture3, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites3[j][i - 1] = new Sprite(sprite_region3);
			this.sprite_region4 = new TextureRegion(this.sprite_texture4, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.sprites4[j][i - 1] = new Sprite(sprite_region4);
		}
	}

	
	public void drawMe (SpriteBatch arg0, Player player) {
		arg0.begin();
		int x = (int)player.loc.getScreenLocation().x - 64;
		int y = (int)player.loc.getScreenLocation().y;
		int direction = player.direction;
		int model = player.model;
		boolean isMale = player.isMale;
		int frame = player.frame;

		if (model == 0) {
			this.sprites[frame][direction].setPosition(x, y);
			this.sprites[frame][direction].draw(arg0);
		} else if (model ==1){
			this.sprites2[frame][direction].setPosition(x, y);
			this.sprites2[frame][direction].draw(arg0);
		} else if (model == 3){
			custom.drawMe(arg0, x, y, frame, direction);
		}  else if (model > 4){
			custom.drawMe(arg0, x, y, frame, direction, model, isMale);
		} else {
			this.sprites3[frame][direction].setPosition(x, y);
			this.sprites3[frame][direction].draw(arg0);
		
		}
		
		arg0.end();
	}
	
}
