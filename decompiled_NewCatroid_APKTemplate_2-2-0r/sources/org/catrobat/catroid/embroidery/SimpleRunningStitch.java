package org.catrobat.catroid.embroidery;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/SimpleRunningStitch.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SimpleRunningStitch extends RunningStitchType {
    private int length;
    private Sprite sprite;
    private float firstX = 0.0f;
    private float firstY = 0.0f;
    private boolean first = true;

    public SimpleRunningStitch(Sprite sprite, int length) {
        this.sprite = sprite;
        this.length = length;
        setStartCoordinates(sprite.look.getXInUserInterfaceDimensionUnit(), sprite.look.getYInUserInterfaceDimensionUnit());
    }

    @Override // org.catrobat.catroid.embroidery.RunningStitchType
    public void setStartCoordinates(float x, float y) {
        this.firstX = x;
        this.firstY = y;
    }

    @Override // org.catrobat.catroid.embroidery.RunningStitchType
    public void update(float currentX, float currentY) {
        float distanceToPoint = getDistanceToPoint(currentX, this.firstX, currentY, this.firstY);
        int i = this.length;
        if (distanceToPoint >= i) {
            float f = (distanceToPoint - (distanceToPoint % i)) / distanceToPoint;
            float f2 = this.firstX;
            float f3 = f2 + ((currentX - f2) * f);
            float f4 = this.firstY;
            float f5 = f4 + (f * (currentY - f4));
            interpolateStitches((int) Math.floor((distanceToPoint - (distanceToPoint % i)) / i), f3, f5);
            setStartCoordinates(f3, f5);
        }
    }

    private void interpolateStitches(int interpolationCount, float currentX, float currentY) {
        if (this.first) {
            this.first = false;
            EmbroideryPatternManager embroideryPatternManager = StageActivity.getActiveStageListener().embroideryPatternManager;
            float f = this.firstX;
            float f2 = this.firstY;
            int zIndex = this.sprite.look.getZIndex();
            Sprite sprite = this.sprite;
            embroideryPatternManager.addStitchCommand(new DSTStitchCommand(f, f2, zIndex, sprite, sprite.getEmbroideryThreadColor()));
        }
        for (int i = 1; i <= interpolationCount; i++) {
            float f3 = i / interpolationCount;
            float fInterpolate = interpolate(currentX, this.firstX, f3);
            float fInterpolate2 = interpolate(currentY, this.firstY, f3);
            EmbroideryPatternManager embroideryPatternManager2 = StageActivity.getActiveStageListener().embroideryPatternManager;
            int zIndex2 = this.sprite.look.getZIndex();
            Sprite sprite2 = this.sprite;
            embroideryPatternManager2.addStitchCommand(new DSTStitchCommand(fInterpolate, fInterpolate2, zIndex2, sprite2, sprite2.getEmbroideryThreadColor()));
        }
    }
}
