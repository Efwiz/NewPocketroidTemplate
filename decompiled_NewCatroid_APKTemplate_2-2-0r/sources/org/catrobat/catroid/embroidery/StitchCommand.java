package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/StitchCommand.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface StitchCommand {
    void act(EmbroideryWorkSpace workSpace, EmbroideryStream stream, StitchCommand previousCommandOfSprite);

    boolean equals(Object object);

    Color getColor();

    int getLayer();

    Sprite getSprite();

    float getX();

    float getY();

    int hashCode();
}
