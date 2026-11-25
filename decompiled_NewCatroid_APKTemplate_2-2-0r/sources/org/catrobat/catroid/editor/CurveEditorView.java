package org.catrobat.catroid.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.InputDeviceCompat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.catrobat.catroid.raptor.ParticleCurvePoint;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/CurveEditorView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CurveEditorView extends View {
    private Paint bgPaint;
    private Paint gridPaint;
    private Paint linePaint;
    private float maxVal;
    private float minVal;
    private Runnable onUpdateListener;
    private Paint pointPaint;
    private List<ParticleCurvePoint<Float>> points;
    private int selectedIndex;
    private Paint zeroLinePaint;

    public CurveEditorView(Context context) {
        super(context);
        this.selectedIndex = -1;
        this.minVal = 0.0f;
        this.maxVal = 1.0f;
        init();
    }

    public CurveEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.selectedIndex = -1;
        this.minVal = 0.0f;
        this.maxVal = 1.0f;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setColor(-16711936);
        this.linePaint.setStrokeWidth(5.0f);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        this.pointPaint = paint2;
        paint2.setColor(-1);
        this.pointPaint.setStyle(Paint.Style.FILL);
        this.pointPaint.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.gridPaint = paint3;
        paint3.setColor(1090519039);
        this.gridPaint.setStrokeWidth(2.0f);
        Paint paint4 = new Paint();
        this.zeroLinePaint = paint4;
        paint4.setColor(-2130771968);
        this.zeroLinePaint.setStrokeWidth(3.0f);
        Paint paint5 = new Paint();
        this.bgPaint = paint5;
        paint5.setColor(-14671840);
    }

    public void setData(List<ParticleCurvePoint<Float>> points, float min, float max, Runnable onUpdate) {
        this.points = points;
        if (min >= max) {
            max = min + 1.0f;
        }
        this.minVal = min;
        this.maxVal = max;
        this.onUpdateListener = onUpdate;
        invalidate();
    }

    public void setRange(float min, float max) {
        if (min >= max) {
            max = 1.0f + min;
        }
        this.minVal = min;
        this.maxVal = max;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.bgPaint);
        float width = getWidth();
        float height = getHeight();
        if (this.minVal < 0.0f && this.maxVal > 0.0f) {
            float fMapY = mapY(0.0f);
            canvas.drawLine(0.0f, fMapY, width, fMapY, this.zeroLinePaint);
        }
        float f = width / 2.0f;
        canvas.drawLine(f, 0.0f, f, height, this.gridPaint);
        List<ParticleCurvePoint<Float>> list = this.points;
        if (list == null || list.isEmpty()) {
            return;
        }
        int i = 0;
        while (i < this.points.size() - 1) {
            float fMapX = mapX(this.points.get(i).time);
            float fMapY2 = mapY(this.points.get(i).value.floatValue());
            i++;
            canvas.drawLine(fMapX, fMapY2, mapX(this.points.get(i).time), mapY(this.points.get(i).value.floatValue()), this.linePaint);
        }
        for (int i2 = 0; i2 < this.points.size(); i2++) {
            float fMapX2 = mapX(this.points.get(i2).time);
            float fMapY3 = mapY(this.points.get(i2).value.floatValue());
            if (i2 == this.selectedIndex) {
                this.pointPaint.setColor(InputDeviceCompat.SOURCE_ANY);
                canvas.drawCircle(fMapX2, fMapY3, 16.0f, this.pointPaint);
            } else {
                this.pointPaint.setColor(-1);
                canvas.drawCircle(fMapX2, fMapY3, 12.0f, this.pointPaint);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [T, java.lang.Float] */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.points == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            this.selectedIndex = -1;
            for (int i = 0; i < this.points.size(); i++) {
                if (Math.hypot(x - mapX(this.points.get(i).time), y - mapY(this.points.get(i).value.floatValue())) < 80.0f) {
                    this.selectedIndex = i;
                }
            }
            if (this.selectedIndex != -1) {
                invalidate();
            }
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                if (this.selectedIndex != -1) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float fMax = Math.max(0.0f, Math.min(1.0f, x / getWidth()));
                    float height = 1.0f - (y / getHeight());
                    float f = this.minVal;
                    float f2 = this.maxVal;
                    float fMax2 = Math.max(f, Math.min(f2, (height * (f2 - f)) + f));
                    this.points.get(this.selectedIndex).time = fMax;
                    this.points.get(this.selectedIndex).value = Float.valueOf(fMax2);
                    invalidate();
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(event);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(false);
        if (this.selectedIndex != -1) {
            Collections.sort(this.points, new Comparator() { // from class: org.catrobat.catroid.editor.CurveEditorView$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Float.compare(((ParticleCurvePoint) obj).time, ((ParticleCurvePoint) obj2).time);
                }
            });
            this.selectedIndex = -1;
            Runnable runnable = this.onUpdateListener;
            if (runnable != null) {
                runnable.run();
            }
            invalidate();
        }
        return true;
    }

    private float mapX(float time) {
        return time * getWidth();
    }

    private float mapY(float val) {
        float f = this.maxVal - this.minVal;
        if (Math.abs(f) < 1.0E-4f) {
            f = 1.0f;
        }
        return getHeight() * (1.0f - ((val - this.minVal) / f));
    }

    public boolean deleteSelectedPoint() {
        int i = this.selectedIndex;
        if (i == -1 || i >= this.points.size()) {
            return false;
        }
        this.points.remove(this.selectedIndex);
        this.selectedIndex = -1;
        Runnable runnable = this.onUpdateListener;
        if (runnable != null) {
            runnable.run();
        }
        invalidate();
        return true;
    }

    public boolean hasSelection() {
        return this.selectedIndex != -1;
    }
}
