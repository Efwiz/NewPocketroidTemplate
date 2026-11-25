package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTWorkSpace.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTWorkSpace implements EmbroideryWorkSpace {
    private float currentX;
    private float currentY;
    private Sprite lastSprite;
    private Color threadColor;

    @Override // org.catrobat.catroid.embroidery.EmbroideryWorkSpace
    public void set(float currentX, float currentY, Sprite currentSprite) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.lastSprite = currentSprite;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryWorkSpace
    public float getCurrentX() {
        return this.currentX;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryWorkSpace
    public float getCurrentY() {
        return this.currentY;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryWorkSpace
    public Color getColor() {
        return this.threadColor;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryWorkSpace
    public Sprite getLastSprite() {
        return this.lastSprite;
    }
}
