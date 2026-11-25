package io.github.rosemoe.sora.graphics;

import android.graphics.Canvas;

/* JADX INFO: loaded from: classes11.dex */
public class BufferedDrawPoints {
    private int pointCount;
    private float[] points = new float[128];

    public void drawPoint(float f, float f2) {
        float[] fArr = this.points;
        int length = fArr.length;
        int i = this.pointCount;
        if (length < (i + 1) * 2) {
            float[] fArr2 = new float[fArr.length << 1];
            System.arraycopy(fArr, 0, fArr2, 0, i * 2);
            this.points = fArr2;
        }
        float[] fArr3 = this.points;
        int i2 = this.pointCount;
        fArr3[i2 * 2] = f;
        fArr3[(i2 * 2) + 1] = f2;
        this.pointCount = i2 + 1;
    }

    public void commitPoints(Canvas canvas, android.graphics.Paint paint) {
        int i = this.pointCount;
        if (i == 0) {
            return;
        }
        canvas.drawPoints(this.points, 0, i * 2, paint);
        this.pointCount = 0;
    }
}
