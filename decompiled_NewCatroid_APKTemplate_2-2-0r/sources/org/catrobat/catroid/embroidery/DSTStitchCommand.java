package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import org.catrobat.catroid.content.Sprite;
import org.luaj.vm2.Lua;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTStitchCommand.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTStitchCommand implements StitchCommand {
    private int layer;
    private final Sprite sprite;
    Color threadColor;
    private final float xCoord;
    private final float yCoord;

    public DSTStitchCommand(float x, float y, int layer, Sprite sprite, Color threadColor) {
        this.xCoord = x;
        this.yCoord = y;
        this.layer = layer;
        this.sprite = sprite;
        this.threadColor = threadColor;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public float getX() {
        return this.xCoord;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public float getY() {
        return this.yCoord;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public Color getColor() {
        return this.threadColor;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public int getLayer() {
        return this.layer;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public Sprite getSprite() {
        return this.sprite;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public void act(EmbroideryWorkSpace workSpace, EmbroideryStream stream, StitchCommand previousCommandOfSprite) {
        if (this.xCoord == workSpace.getCurrentX() && this.yCoord == workSpace.getCurrentY() && this.sprite.equals(workSpace.getLastSprite())) {
            return;
        }
        if (workSpace.getLastSprite() != null && !this.sprite.equals(workSpace.getLastSprite())) {
            stream.addColorChange();
            stream.addStitchPoint(workSpace.getCurrentX(), workSpace.getCurrentY(), workSpace.getColor());
            if (DSTFileConstants.getMaxDistanceBetweenPoints(workSpace.getCurrentX(), workSpace.getCurrentY(), this.xCoord, this.yCoord) > 121.0f) {
                stream.addJump();
            }
            stream.addStitchPoint(workSpace.getCurrentX(), workSpace.getCurrentY(), workSpace.getColor());
        } else if (!stream.getPointList().isEmpty() && previousCommandOfSprite != null && previousCommandOfSprite.getLayer() != this.layer) {
            stream.addColorChange();
            if (DSTFileConstants.getMaxDistanceBetweenPoints(previousCommandOfSprite.getX(), previousCommandOfSprite.getY(), this.xCoord, this.yCoord) > 121.0f) {
                stream.addStitchPoint(this.xCoord, this.yCoord, this.threadColor);
            } else {
                stream.addStitchPoint(previousCommandOfSprite.getX(), previousCommandOfSprite.getY(), previousCommandOfSprite.getColor());
                stream.addJump();
                stream.addStitchPoint(previousCommandOfSprite.getX(), previousCommandOfSprite.getY(), previousCommandOfSprite.getColor());
            }
        }
        if (previousCommandOfSprite != null && previousCommandOfSprite.getLayer() != this.layer && DSTFileConstants.getMaxDistanceBetweenPoints(previousCommandOfSprite.getX(), previousCommandOfSprite.getY(), this.xCoord, this.yCoord) < 121.0f) {
            stream.addStitchPoint(previousCommandOfSprite.getX(), previousCommandOfSprite.getY(), previousCommandOfSprite.getColor());
        }
        stream.addStitchPoint(this.xCoord, this.yCoord, this.threadColor);
        workSpace.set(this.xCoord, this.yCoord, this.sprite);
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public boolean equals(Object object) {
        if (!(object instanceof DSTStitchCommand)) {
            return false;
        }
        DSTStitchCommand dSTStitchCommand = (DSTStitchCommand) object;
        return dSTStitchCommand.getLayer() == this.layer && dSTStitchCommand.getSprite().equals(this.sprite) && dSTStitchCommand.getX() == this.xCoord && dSTStitchCommand.getY() == this.yCoord;
    }

    @Override // org.catrobat.catroid.embroidery.StitchCommand
    public int hashCode() {
        int i = ((int) this.xCoord) & Lua.MASK_NOT_Bx;
        int i2 = (((int) this.yCoord) & Lua.MASK_NOT_Bx) << 15;
        return ((i | i2) | (this.layer << 16)) ^ this.sprite.hashCode();
    }
}
