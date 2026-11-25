package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/StitchPoint.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface StitchPoint {
    void appendToStream(FileOutputStream fileStream) throws IOException;

    boolean equals(Object object);

    Color getColor();

    float getX();

    float getY();

    int hashCode();

    boolean isColorChangePoint();

    boolean isConnectingPoint();

    boolean isJumpPoint();

    void setColor(Color color);

    void setColorChange(boolean colorChangePoint);

    void setJump(boolean jumpPoint);

    void setRelativeCoordinatesToPreviousPoint(float previousX, float previousY);
}
