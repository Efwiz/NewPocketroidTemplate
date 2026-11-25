package org.catrobat.catroid.embroidery;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/TripleRunningStitch.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TripleRunningStitch extends RunningStitchType {
    private Sprite sprite;
    private int steps;
    private float firstX = 0.0f;
    private float firstY = 0.0f;
    private boolean first = true;

    public TripleRunningStitch(Sprite sprite, int steps) {
        this.sprite = sprite;
        this.steps = steps;
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
        int i = this.steps;
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
        int i = 1;
        float f3 = this.firstX;
        float f4 = this.firstY;
        while (i <= interpolationCount) {
            float f5 = i / interpolationCount;
            float fInterpolate = interpolate(currentX, this.firstX, f5);
            float fInterpolate2 = interpolate(currentY, this.firstY, f5);
            EmbroideryPatternManager embroideryPatternManager2 = StageActivity.getActiveStageListener().embroideryPatternManager;
            int zIndex2 = this.sprite.look.getZIndex();
            Sprite sprite2 = this.sprite;
            embroideryPatternManager2.addStitchCommand(new DSTStitchCommand(fInterpolate, fInterpolate2, zIndex2, sprite2, sprite2.getEmbroideryThreadColor()));
            EmbroideryPatternManager embroideryPatternManager3 = StageActivity.getActiveStageListener().embroideryPatternManager;
            int zIndex3 = this.sprite.look.getZIndex();
            Sprite sprite3 = this.sprite;
            embroideryPatternManager3.addStitchCommand(new DSTStitchCommand(f3, f4, zIndex3, sprite3, sprite3.getEmbroideryThreadColor()));
            EmbroideryPatternManager embroideryPatternManager4 = StageActivity.getActiveStageListener().embroideryPatternManager;
            int zIndex4 = this.sprite.look.getZIndex();
            Sprite sprite4 = this.sprite;
            embroideryPatternManager4.addStitchCommand(new DSTStitchCommand(fInterpolate, fInterpolate2, zIndex4, sprite4, sprite4.getEmbroideryThreadColor()));
            i++;
            f4 = fInterpolate2;
            f3 = fInterpolate;
        }
    }
}
