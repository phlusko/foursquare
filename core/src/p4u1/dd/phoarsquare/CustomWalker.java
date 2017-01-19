package p4u1.dd.phoarsquare;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CustomWalker {
	Sprite[][] linesSprites = new Sprite[4][3];
	Texture linesTexture;
	TextureRegion linesRegion;
	
	Sprite[][] skinSprites = new Sprite[4][3];
	Texture skinTexture;
	TextureRegion skinRegion;

	Sprite[][] shirtSprites = new Sprite[4][3];
	Texture shirtTexture;
	TextureRegion shirtRegion;
	
	Sprite[][] beltSprites = new Sprite[4][3];
	Texture beltTexture;
	TextureRegion beltRegion;
	
	Sprite[][] pantsSprites = new Sprite[4][3];
	Texture pantsTexture;
	TextureRegion pantsRegion;
	
	Sprite[][] shoes1Sprites = new Sprite[4][3];
	Texture shoes1Texture;
	TextureRegion shoes1Region;
	
	Sprite[][] shoes2Sprites = new Sprite[4][3];
	Texture shoes2Texture;
	TextureRegion shoes2Region;
	
	Texture headTexture, eyesTexture, hairTexture, hairBehindTexture, mouthTexture, noseTexture;
	Sprite headSprite, eyesSprite, hairSprite, hairBehindSprite, mouthSprite, noseSprite;
	
	Texture[] eyesTextures = new Texture[7];
	Sprite[] eyesSprites = new Sprite[7];
	
	Texture[] browsTextures = new Texture[7];
	Sprite[] browsSprites = new Sprite[7];
	
	Texture[] nosesTextures = new Texture[9];
	Sprite[] nosesSprites = new Sprite[9];
	
	Texture[] mouthsTextures = new Texture[7];
	Sprite[] mouthsSprites = new Sprite[7];

	public static final int maleHairCount = 7;

	Texture[] maleHairTextures = new Texture[this.maleHairCount];
	Sprite[] maleHairSprites = new Sprite[this.maleHairCount];

	//public static final int femaleHairCount = 3;
	//Texture[] femaleHairTextures = new Texture[this.femaleHairCount];
	//Sprite[] femaleHairSprites = new Sprite[this.femaleHairCount];
	
	HashMap<Integer, Face> faces = new HashMap<Integer, Face>();
	float[] skinColors = new float[5];
	float[] eyeColors = new float[6];

	public static final int COLORS_COUNT = 12;
	float[] standardColors = new float[COLORS_COUNT];

	public CustomWalker() {
		standardColors[0] = Color.valueOf("ED0A3FFF").toFloatBits();
		standardColors[1] = Color.valueOf("FF3F34FF").toFloatBits();
		standardColors[2] = Color.valueOf("FF861FFF").toFloatBits();
		standardColors[3] = Color.valueOf("FBE870FF").toFloatBits();
		standardColors[4] = Color.valueOf("C5E17AFF").toFloatBits();
		standardColors[5] = Color.valueOf("01A368FF").toFloatBits();
		standardColors[6] = Color.valueOf("76d7EAFF").toFloatBits();
		standardColors[7] = Color.valueOf("0066FFFF").toFloatBits();
		standardColors[8] = Color.valueOf("8359A3FF").toFloatBits();
		standardColors[9] = Color.valueOf("FFFFFFFF").toFloatBits();
		standardColors[10] = Color.valueOf("AF593EFF").toFloatBits();
		standardColors[11] = Color.valueOf("000000FF").toFloatBits();
		eyeColors[0] = Color.toFloatBits(74, 108, 204, 255);
		eyeColors[1] = Color.toFloatBits(50, 192, 245, 255);
		eyeColors[2] = Color.toFloatBits(82, 73, 15, 255);
		eyeColors[3] = Color.toFloatBits(144, 126, 3, 255);
		eyeColors[4] = Color.toFloatBits(68, 33, 7, 255);
		eyeColors[5] = Color.toFloatBits(102, 163, 112, 255);
		skinColors[0] = Color.toFloatBits(144, 85, 36, 255);
		skinColors[1] = Color.toFloatBits(198, 134, 66, 255);
		skinColors[2] = Color.toFloatBits(244, 172, 105, 255);
		skinColors[3] = Color.toFloatBits(241, 194, 125, 255);
		skinColors[4] = Color.toFloatBits(255, 219, 172, 255);
		
		this.linesTexture = new Texture(Gdx.files.internal("data/sprites/krystal_lines.png"));
		this.skinTexture = new Texture(Gdx.files.internal("data/sprites/krystal_skin.png"));
		this.shirtTexture = new Texture(Gdx.files.internal("data/sprites/krystal_shirt.png"));
		this.beltTexture = new Texture(Gdx.files.internal("data/sprites/krystal_belt.png"));
		this.pantsTexture = new Texture(Gdx.files.internal("data/sprites/krystal_pants.png"));
		this.shoes1Texture = new Texture(Gdx.files.internal("data/sprites/krystal_shoes1.png"));
		this.shoes2Texture = new Texture(Gdx.files.internal("data/sprites/krystal_shoes2.png"));
		
		this.headTexture = new Texture(Gdx.files.internal("data/sprites/krystal_head.png"));
		this.eyesTexture = new Texture(Gdx.files.internal("data/sprites/krystal_eyes.png"));
		this.hairTexture = new Texture(Gdx.files.internal("data/sprites/krystal_hair.png"));
		this.hairBehindTexture = new Texture(Gdx.files.internal("data/sprites/krystal_hair_behind.png"));
		this.mouthTexture = new Texture(Gdx.files.internal("data/sprites/krystal_mouth.png"));
		this.noseTexture = new Texture(Gdx.files.internal("data/sprites/krystal_nose.png"));
		
		this.headSprite = new Sprite(this.headTexture);
		this.eyesSprite = new Sprite(this.eyesTexture);
		this.hairSprite = new Sprite(this.hairTexture);
		this.hairBehindSprite = new Sprite(this.hairBehindTexture);
		this.mouthSprite = new Sprite(this.mouthTexture);
		this.noseSprite = new Sprite(this.noseTexture);

		for (int loop = 1; loop <=4; loop++) {
			maleHairTextures[loop - 1] = new Texture(Gdx.files.internal("data/sprites/lib_hair_m" + loop + ".png"));
			maleHairSprites[loop-1] = new Sprite(maleHairTextures[loop-1]);
		}
		for (int loop = 1; loop <=3; loop++) {
			maleHairTextures[4 + loop - 1] = new Texture(Gdx.files.internal("data/sprites/lib_hair_f" + (loop) + ".png"));
			maleHairSprites[4 + loop-1] = new Sprite(maleHairTextures[4 + loop-1]);
		}
		for (int loop = 1; loop <=7; loop++){
			eyesTextures[loop-1] = new Texture(Gdx.files.internal("data/sprites/lib_eyes" + loop + ".png"));
			eyesSprites[loop-1] = new Sprite(eyesTextures[loop-1]);
			browsTextures[loop-1] = new Texture(Gdx.files.internal("data/sprites/lib_brows" + loop + ".png"));
			browsSprites[loop-1] = new Sprite(browsTextures[loop-1]);
			nosesTextures[loop-1] = new Texture(Gdx.files.internal("data/sprites/lib_nose" + loop + ".png"));
			nosesSprites[loop-1] = new Sprite(nosesTextures[loop-1]);
			mouthsTextures[loop-1] = new Texture(Gdx.files.internal("data/sprites/lib_mouth" + loop + ".png"));
			mouthsSprites[loop-1] = new Sprite(mouthsTextures[loop-1]);
		}
		
		nosesTextures[7] = new Texture(Gdx.files.internal("data/sprites/lib_nose8.png"));
		nosesSprites[7] = new Sprite(nosesTextures[7]);
		nosesTextures[8] = new Texture(Gdx.files.internal("data/sprites/lib_nose9.png"));
		nosesSprites[8] = new Sprite(nosesTextures[8]);
		
		int i = 0;
		int di = 0;
		for(int j = 0; j < 4; j++) {
			this.linesRegion = new TextureRegion(this.linesTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.linesSprites[j][i] = new Sprite(linesRegion);
			this.skinRegion = new TextureRegion(this.skinTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.skinSprites[j][i] = new Sprite(skinRegion);
			this.shirtRegion = new TextureRegion(this.shirtTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shirtSprites[j][i] = new Sprite(shirtRegion);
			this.beltRegion = new TextureRegion(this.beltTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.beltSprites[j][i] = new Sprite(beltRegion);
			this.shoes1Region = new TextureRegion(this.shoes1Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes1Sprites[j][i] = new Sprite(shoes1Region);
			this.shoes2Region = new TextureRegion(this.shoes2Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes2Sprites[j][i] = new Sprite(shoes2Region);
			this.pantsRegion = new TextureRegion(this.pantsTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.pantsSprites[j][i] = new Sprite(pantsRegion);
		}
		i = 2;
		di = 2;
		for(int j = 0; j < 4; j++) {
			this.linesRegion = new TextureRegion(this.linesTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.linesSprites[j][i-1] = new Sprite(linesRegion);
			this.skinRegion = new TextureRegion(this.skinTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.skinSprites[j][i-1] = new Sprite(skinRegion);
			this.shirtRegion = new TextureRegion(this.shirtTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shirtSprites[j][i-1] = new Sprite(shirtRegion);
			this.beltRegion = new TextureRegion(this.beltTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.beltSprites[j][i-1] = new Sprite(beltRegion);
			this.shoes1Region = new TextureRegion(this.shoes1Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes1Sprites[j][i-1] = new Sprite(shoes1Region);
			this.shoes2Region = new TextureRegion(this.shoes2Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes2Sprites[j][i-1] = new Sprite(shoes2Region);
			this.pantsRegion = new TextureRegion(this.pantsTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.pantsSprites[j][i-1] = new Sprite(pantsRegion);
		}
		i = 3;
		di = 3;
		for(int j = 0; j < 4; j++) {
			this.linesRegion = new TextureRegion(this.linesTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.linesSprites[j][i-1] = new Sprite(linesRegion);
			this.skinRegion = new TextureRegion(this.skinTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.skinSprites[j][i-1] = new Sprite(skinRegion);
			this.shirtRegion = new TextureRegion(this.shirtTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shirtSprites[j][i-1] = new Sprite(shirtRegion);
			this.beltRegion = new TextureRegion(this.beltTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.beltSprites[j][i-1] = new Sprite(beltRegion);
			this.shoes1Region = new TextureRegion(this.shoes1Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes1Sprites[j][i-1] = new Sprite(shoes1Region);
			this.shoes2Region = new TextureRegion(this.shoes2Texture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.shoes2Sprites[j][i-1] = new Sprite(shoes2Region);
			this.pantsRegion = new TextureRegion(this.pantsTexture, 0 + (j * 128), 0 + (di * 128), 128, 128);
			this.pantsSprites[j][i-1] = new Sprite(pantsRegion);
		}
	}
	
	public void drawMe(SpriteBatch arg0, int x, int y, int frame, int direction) {
		this.hairBehindSprite.setColor(Color.toFloatBits(83, 57, 15, 255));
		this.hairBehindSprite.setPosition(x, y);
		this.hairBehindSprite.draw(arg0);
		
		
		
		this.shirtSprites[frame][direction].setColor(Color.toFloatBits(51, 204, 255, 255));
		this.shirtSprites[frame][direction].setPosition(x, y);
		this.shirtSprites[frame][direction].draw(arg0);
		
		this.skinSprites[frame][direction].setColor(Color.toFloatBits(233, 192, 178, 255));
		this.skinSprites[frame][direction].setPosition(x, y);
		this.skinSprites[frame][direction].draw(arg0);
		
		this.beltSprites[frame][direction].setColor(Color.ORANGE);
		this.beltSprites[frame][direction].setPosition(x, y);
		this.beltSprites[frame][direction].draw(arg0);
		
		this.pantsSprites[frame][direction].setColor(Color.GRAY);
		this.pantsSprites[frame][direction].setPosition(x, y);
		this.pantsSprites[frame][direction].draw(arg0);
		
		this.shoes1Sprites[frame][direction].setColor(Color.toFloatBits(51, 204, 255, 255));
		this.shoes1Sprites[frame][direction].setPosition(x, y);
		this.shoes1Sprites[frame][direction].draw(arg0);
		
		this.shoes2Sprites[frame][direction].setColor(Color.DARK_GRAY);
		this.shoes2Sprites[frame][direction].setPosition(x, y);
		this.shoes2Sprites[frame][direction].draw(arg0);
		
		this.linesSprites[frame][direction].setPosition(x, y);
		this.linesSprites[frame][direction].draw(arg0);
		this.headSprite.setColor(Color.toFloatBits(233, 192, 178, 255));
		this.headSprite.setPosition(x, y);
		this.headSprite.draw(arg0);
		this.eyesSprite.setColor(Color.toFloatBits(18, 15, 15, 255));
		this.eyesSprite.setPosition(x, y);
		this.eyesSprite.draw(arg0);
		this.noseSprite.setPosition(x, y);
		this.noseSprite.draw(arg0);
		this.mouthSprite.setPosition(x, y);
		this.mouthSprite.draw(arg0);
		this.noseSprite.setPosition(x, y);
		this.noseSprite.draw(arg0);
		this.hairSprite.setColor(Color.toFloatBits(83, 57, 15, 255));
		this.hairSprite.setPosition(x, y);
		this.hairSprite.draw(arg0);
	}

	public void drawMe(SpriteBatch arg0, int x, int y, Face myFace, int frame) {


		this.shirtSprites[frame][0].setColor(standardColors[myFace.shirtColor]);
		this.shirtSprites[frame][0].setPosition(x, y);
		this.shirtSprites[frame][0].draw(arg0);

		this.skinSprites[frame][0].setColor(skinColors[myFace.skinColor]);
		this.skinSprites[frame][0].setPosition(x, y);
		this.skinSprites[frame][0].draw(arg0);

		this.beltSprites[frame][0].setColor(standardColors[myFace.beltColor]);
		this.beltSprites[frame][0].setPosition(x, y);
		this.beltSprites[frame][0].draw(arg0);

		this.pantsSprites[frame][0].setColor(standardColors[myFace.pantsColor]);
		this.pantsSprites[frame][0].setPosition(x, y);
		this.pantsSprites[frame][0].draw(arg0);

		this.shoes1Sprites[frame][0].setColor(standardColors[myFace.shoeColor1]);
		this.shoes1Sprites[frame][0].setPosition(x, y);
		this.shoes1Sprites[frame][0].draw(arg0);

		this.shoes2Sprites[frame][0].setColor(standardColors[myFace.shoeColor2]);
		this.shoes2Sprites[frame][0].setPosition(x, y);
		this.shoes2Sprites[frame][0].draw(arg0);

		this.linesSprites[frame][0].setPosition(x, y);
		this.linesSprites[frame][0].draw(arg0);
		this.headSprite.setColor(skinColors[myFace.skinColor]);
		this.headSprite.setPosition(x, y);
		this.headSprite.draw(arg0);
		this.eyesSprites[myFace.eyes].setColor(eyeColors[myFace.eyeColor]);
		this.eyesSprites[myFace.eyes].setPosition(x, y);
		this.eyesSprites[myFace.eyes].draw(arg0);
		this.browsSprites[myFace.brows].setPosition(x, y);
		this.browsSprites[myFace.brows].draw(arg0);
		this.nosesSprites[myFace.nose].setPosition(x, y);
		this.nosesSprites[myFace.nose].draw(arg0);
		this.mouthsSprites[myFace.mouth].setPosition(x, y);
		this.mouthsSprites[myFace.mouth].draw(arg0);
		this.maleHairSprites[myFace.hair].setColor(standardColors[myFace.hairColor]);
		this.maleHairSprites[myFace.hair].setPosition(x, y);
		this.maleHairSprites[myFace.hair].draw(arg0);
	}

	
	public void drawMe(SpriteBatch arg0, int x, int y, int frame, int direction, int model, boolean isMale) {
		//this.hairBehindSprite.setColor(Color.toFloatBits(83, 57, 15, 255));
		//this.hairBehindSprite.setPosition(x, y);
		//this.hairBehindSprite.draw(arg0);
		
		Face myFace;
		if (faces.get(model) == null) {
			faces.put(model, new Face((int)(Math.random()*eyesTextures.length), (int)(Math.random()*browsTextures.length), (int)(Math.random()*mouthsTextures.length), (int)(Math.random()*nosesTextures.length), (int)(Math.random()*skinColors.length), (int)(Math.random()*eyeColors.length), isMale));
		}
		myFace = faces.get(model);
		
		this.shirtSprites[frame][direction].setColor(standardColors[myFace.shirtColor]);
		this.shirtSprites[frame][direction].setPosition(x, y);
		this.shirtSprites[frame][direction].draw(arg0);
		
		this.skinSprites[frame][direction].setColor(skinColors[myFace.skinColor]);
		this.skinSprites[frame][direction].setPosition(x, y);
		this.skinSprites[frame][direction].draw(arg0);
		
		this.beltSprites[frame][direction].setColor(standardColors[myFace.beltColor]);
		this.beltSprites[frame][direction].setPosition(x, y);
		this.beltSprites[frame][direction].draw(arg0);
		
		this.pantsSprites[frame][direction].setColor(standardColors[myFace.pantsColor]);
		this.pantsSprites[frame][direction].setPosition(x, y);
		this.pantsSprites[frame][direction].draw(arg0);
		
		this.shoes1Sprites[frame][direction].setColor(standardColors[myFace.shoeColor1]);
		this.shoes1Sprites[frame][direction].setPosition(x, y);
		this.shoes1Sprites[frame][direction].draw(arg0);
		
		this.shoes2Sprites[frame][direction].setColor(standardColors[myFace.shoeColor2]);
		this.shoes2Sprites[frame][direction].setPosition(x, y);
		this.shoes2Sprites[frame][direction].draw(arg0);
		
		this.linesSprites[frame][direction].setPosition(x, y);
		this.linesSprites[frame][direction].draw(arg0);
		this.headSprite.setColor(skinColors[myFace.skinColor]);
		this.headSprite.setPosition(x, y);
		this.headSprite.draw(arg0);
		this.eyesSprites[myFace.eyes].setColor(eyeColors[myFace.eyeColor]);
		this.eyesSprites[myFace.eyes].setPosition(x, y);
		this.eyesSprites[myFace.eyes].draw(arg0);
		this.browsSprites[myFace.brows].setPosition(x, y);
		this.browsSprites[myFace.brows].draw(arg0);
		this.nosesSprites[myFace.nose].setPosition(x, y);
		this.nosesSprites[myFace.nose].draw(arg0);
		this.mouthsSprites[myFace.mouth].setPosition(x, y);
		this.mouthsSprites[myFace.mouth].draw(arg0);
		this.maleHairSprites[myFace.hair].setColor(standardColors[myFace.hairColor]);
		this.maleHairSprites[myFace.hair].setPosition(x, y);
		this.maleHairSprites[myFace.hair].draw(arg0);
	}



}
