package org.catrobat.catroid.embroidery;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/RunningStitchType.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class RunningStitchType {
    public abstract void setStartCoordinates(float x, float y);

    public abstract void update(float currentX, float currentY);

    float interpolate(float endValue, float startValue, float percentage) {
        return Math.round(startValue + (percentage * (endValue - startValue)));
    }

    float getDistanceToPoint(float currentX, float firstX, float currentY, float firstY) {
        return (float) Math.hypot(currentX - firstX, currentY - firstY);
    }
}
