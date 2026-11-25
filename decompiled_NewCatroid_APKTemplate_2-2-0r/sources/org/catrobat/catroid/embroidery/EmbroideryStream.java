package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/EmbroideryStream.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface EmbroideryStream {
    void addAllStitchPoints(ArrayList<StitchPoint> stitchPoints);

    void addColorChange();

    void addJump();

    void addStitchPoint(float x, float y, Color color);

    EmbroideryHeader getHeader();

    ArrayList<StitchPoint> getPointList();

    void reset();
}
