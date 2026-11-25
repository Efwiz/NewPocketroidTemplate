package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/EmbroideryWorkSpace.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface EmbroideryWorkSpace {
    Color getColor();

    float getCurrentX();

    float getCurrentY();

    Sprite getLastSprite();

    void set(float currentX, float currentY, Sprite currentSprite);
}
