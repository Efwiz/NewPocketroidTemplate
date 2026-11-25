package org.catrobat.catroid.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.badlogic.gdx.graphics.Color;
import java.util.List;
import org.catrobat.catroid.raptor.ParticleCurvePoint;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/GradientPreviewView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GradientPreviewView extends View {
    private Paint paint;
    private List<ParticleCurvePoint<Color>> points;

    public GradientPreviewView(Context context, List<ParticleCurvePoint<Color>> points) {
        super(context);
        this.points = points;
        init();
    }

    public GradientPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.paint.setAntiAlias(true);
    }

    public void setPoints(List<ParticleCurvePoint<Color>> points) {
        this.points = points;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<ParticleCurvePoint<Color>> list = this.points;
        if (list == null || list.isEmpty()) {
            this.paint.setColor(-1);
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paint);
            return;
        }
        if (this.points.size() == 1) {
            this.paint.setColor(gdxToAndroid(this.points.get(0).value));
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paint);
            return;
        }
        int size = this.points.size();
        int[] iArr = new int[size];
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = gdxToAndroid(this.points.get(i).value);
            fArr[i] = Math.max(0.0f, Math.min(1.0f, this.points.get(i).time));
        }
        for (int i2 = 1; i2 < size; i2++) {
            float f = fArr[i2];
            float f2 = fArr[i2 - 1];
            if (f <= f2) {
                fArr[i2] = f2 + 0.001f;
            }
        }
        this.paint.setShader(new LinearGradient(0.0f, 0.0f, getWidth(), 0.0f, iArr, fArr, Shader.TileMode.CLAMP));
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paint);
        this.paint.setShader(null);
    }

    private int gdxToAndroid(Color c) {
        return android.graphics.Color.argb((int) (c.a * 255.0f), (int) (c.r * 255.0f), (int) (c.g * 255.0f), (int) (c.b * 255.0f));
    }
}
