package org.catrobat.catroid.embroidery;

import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/EmbroideryPatternManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface EmbroideryPatternManager {
    void addStitchCommand(StitchCommand stitchCommand);

    void clear();

    List<StitchPoint> getEmbroideryPatternList();

    EmbroideryStream getEmbroideryStream();

    boolean validPatternExists();
}
