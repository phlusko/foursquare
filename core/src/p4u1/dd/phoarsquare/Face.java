package p4u1.dd.phoarsquare;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Paul on 12/15/2016.
 */

public class Face {
    int eyes, brows, mouth, nose, skinColor, eyeColor;
    int shirtColor, beltColor, pantsColor, shoeColor1, shoeColor2;
    boolean isMale = true;
    int hair;
    int hairColor;
    public Face(int eyes, int brows, int mouth, int nose, int skinColor, int eyeColor, boolean isMale){
        this.isMale = isMale;
        this.eyes = eyes;
        this.brows = brows;
        this.mouth = mouth;
        this.nose = nose;
        this.skinColor = skinColor;
        this.eyeColor = eyeColor;
        this.shirtColor = (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.beltColor =  (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.pantsColor = (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.shoeColor1 = (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.shoeColor2 = (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.hairColor =  (int)(Math.random() * CustomWalker.COLORS_COUNT);
        this.hair = (int)(Math.random() * CustomWalker.maleHairCount);
    }
}
