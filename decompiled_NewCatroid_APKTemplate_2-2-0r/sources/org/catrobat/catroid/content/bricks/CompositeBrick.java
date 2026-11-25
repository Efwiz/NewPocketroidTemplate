package org.catrobat.catroid.content.bricks;

import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CompositeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface CompositeBrick extends Brick {
    List<Brick> getNestedBricks();

    List<Brick> getSecondaryNestedBricks();

    boolean hasSecondaryList();
}
