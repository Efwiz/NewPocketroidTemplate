package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTStream.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTStream implements EmbroideryStream {
    private EmbroideryHeader header;
    private boolean colorChangeBitSet = false;
    private boolean jumpBitSet = false;
    private ArrayList<StitchPoint> points = new ArrayList<>();

    public DSTStream(EmbroideryHeader header) {
        this.header = header;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public void addColorChange() {
        this.header.addColorChange();
        this.colorChangeBitSet = true;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public void addJump() {
        this.jumpBitSet = true;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public void addStitchPoint(float x, float y, Color color) {
        DSTStitchPoint dSTStitchPoint = new DSTStitchPoint(x, y, color);
        dSTStitchPoint.setColorChange(this.colorChangeBitSet);
        dSTStitchPoint.setJump(this.jumpBitSet);
        this.colorChangeBitSet = false;
        this.jumpBitSet = false;
        if (this.points.isEmpty()) {
            this.header.initialize(x, y);
            dSTStitchPoint.setRelativeCoordinatesToPreviousPoint(x, y);
        } else {
            addInterpolatedPoints(x, y, color);
            this.header.update(x, y);
            ArrayList<StitchPoint> arrayList = this.points;
            StitchPoint stitchPoint = arrayList.get(arrayList.size() - 1);
            dSTStitchPoint.setRelativeCoordinatesToPreviousPoint(stitchPoint.getX(), stitchPoint.getY());
        }
        this.points.add(dSTStitchPoint);
    }

    private void addInterpolatedPoints(float currentX, float currentY, Color color) {
        ArrayList<StitchPoint> arrayList = this.points;
        StitchPoint stitchPoint = arrayList.get(arrayList.size() - 1);
        float maxDistanceBetweenPoints = DSTFileConstants.getMaxDistanceBetweenPoints(currentX, currentY, stitchPoint.getX(), stitchPoint.getY());
        if (maxDistanceBetweenPoints > 121.0f) {
            int iCeil = (int) Math.ceil(maxDistanceBetweenPoints / 121.0f);
            addJump();
            addStitchPoint(stitchPoint.getX(), stitchPoint.getY(), stitchPoint.getColor());
            for (int i = 1; i < iCeil; i++) {
                float f = i / iCeil;
                float fInterpolate = interpolate(currentX, stitchPoint.getX(), f);
                float fInterpolate2 = interpolate(currentY, stitchPoint.getY(), f);
                addJump();
                addStitchPoint(fInterpolate, fInterpolate2, stitchPoint.getColor());
            }
            addJump();
            addStitchPoint(currentX, currentY, color);
        }
    }

    private float interpolate(float endValue, float startValue, float percentage) {
        return Math.round(startValue + (percentage * (endValue - startValue)));
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public void addAllStitchPoints(ArrayList<StitchPoint> stitchPoints) {
        if (!this.points.isEmpty() && !stitchPoints.isEmpty()) {
            addJump();
            ArrayList<StitchPoint> arrayList = this.points;
            float x = arrayList.get(arrayList.size() - 1).getX();
            ArrayList<StitchPoint> arrayList2 = this.points;
            float y = arrayList2.get(arrayList2.size() - 1).getY();
            ArrayList<StitchPoint> arrayList3 = this.points;
            addStitchPoint(x, y, arrayList3.get(arrayList3.size() - 1).getColor());
        }
        for (StitchPoint stitchPoint : stitchPoints) {
            if (stitchPoint.isColorChangePoint()) {
                addColorChange();
            } else if (stitchPoint.isJumpPoint()) {
                addJump();
            }
            addStitchPoint(stitchPoint.getX(), stitchPoint.getY(), stitchPoint.getColor());
        }
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public ArrayList<StitchPoint> getPointList() {
        return this.points;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public EmbroideryHeader getHeader() {
        return this.header;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryStream
    public void reset() {
        this.colorChangeBitSet = false;
        this.points.clear();
        this.header.reset();
    }
}
