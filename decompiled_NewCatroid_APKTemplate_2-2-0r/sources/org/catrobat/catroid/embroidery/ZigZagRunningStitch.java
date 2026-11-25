package org.catrobat.catroid.embroidery;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/ZigZagRunningStitch.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ZigZagRunningStitch extends RunningStitchType {
    private float length;
    private Listener listener;
    private Sprite sprite;
    private float width;
    private float firstX = 0.0f;
    private float firstY = 0.0f;
    private int direction = 1;
    private boolean first = true;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/ZigZagRunningStitch$Listener.class
      classes14.dex
     */
    public interface Listener {
        void onAdd(float x, float y);
    }

    public ZigZagRunningStitch(Sprite sprite, float length, float width) {
        this.sprite = sprite;
        this.length = length;
        this.width = width;
        setStartCoordinates(sprite.look.getXInUserInterfaceDimensionUnit(), sprite.look.getYInUserInterfaceDimensionUnit());
    }

    @Override // org.catrobat.catroid.embroidery.RunningStitchType
    public void setStartCoordinates(float x, float y) {
        this.firstX = x;
        this.firstY = y;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override // org.catrobat.catroid.embroidery.RunningStitchType
    public void update(float currentX, float currentY) {
        if (this.sprite != null) {
            float distanceToPoint = getDistanceToPoint(currentX, this.firstX, currentY, this.firstY);
            float f = this.length;
            if (distanceToPoint >= f) {
                float f2 = (distanceToPoint - (distanceToPoint % f)) / distanceToPoint;
                float f3 = this.firstX;
                float f4 = f3 + ((currentX - f3) * f2);
                float f5 = this.firstY;
                float f6 = f5 + (f2 * (currentY - f5));
                interpolateStitches((int) Math.floor((distanceToPoint - (distanceToPoint % f)) / f), f4, f6);
                setStartCoordinates(f4, f6);
            }
        }
    }

    private void interpolateStitches(int interpolationCount, float currentX, float currentY) {
        float motionDirectionInUserInterfaceDimensionUnit = this.sprite.look.getMotionDirectionInUserInterfaceDimensionUnit();
        if (this.first) {
            this.first = false;
            addPointInDirection(this.firstX, this.firstY, motionDirectionInUserInterfaceDimensionUnit);
        }
        for (int i = 1; i < interpolationCount; i++) {
            float f = i / interpolationCount;
            addPointInDirection(interpolate(currentX, this.firstX, f), interpolate(currentY, this.firstY, f), motionDirectionInUserInterfaceDimensionUnit);
        }
        addPointInDirection(currentX, currentY, motionDirectionInUserInterfaceDimensionUnit);
    }

    private void addPointInDirection(float x, float y, float degrees) {
        double d = degrees + 90.0f;
        float fSin = (float) (((double) x) - ((((double) (this.width / 2.0f)) * Math.sin(Math.toRadians(d))) * ((double) this.direction)));
        double dCos = ((double) (this.width / 2.0f)) * Math.cos(Math.toRadians(d));
        int i = this.direction;
        float f = (float) (((double) y) - (dCos * ((double) i)));
        this.direction = i * (-1);
        EmbroideryPatternManager embroideryPatternManager = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex = this.sprite.look.getZIndex();
        Sprite sprite = this.sprite;
        embroideryPatternManager.addStitchCommand(new DSTStitchCommand(fSin, f, zIndex, sprite, sprite.getEmbroideryThreadColor()));
        Listener listener = this.listener;
        if (listener != null) {
            listener.onAdd(fSin, f);
        }
    }
}
