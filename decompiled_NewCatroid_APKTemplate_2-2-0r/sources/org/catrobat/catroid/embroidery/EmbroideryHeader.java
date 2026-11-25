package org.catrobat.catroid.embroidery;

import java.io.FileOutputStream;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/EmbroideryHeader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface EmbroideryHeader {
    void addColorChange();

    void appendToStream(FileOutputStream fileStream) throws IOException;

    void initialize(float currentX, float currentY);

    void reset();

    void update(float currentX, float currentY);
}
