package com.huawei.hms.mlplugin.asr.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import com.huawei.hms.mlplugin.asr.R;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class VoiceWaveView extends RenderView {
    private int A;
    private int B;
    private int e;
    private float f;
    private float g;
    private int h;
    private float i;
    private int j;
    private int k;
    private int l;
    private final Paint m;
    private List<Path> n;
    private float[] o;
    private float[] p;
    private int q;
    private int r;
    private int s;
    private float t;
    private SparseArray<Double> u;
    private boolean v;
    private int w;
    private boolean x;
    private boolean y;
    private int z;

    public VoiceWaveView(Context context) {
        this(context, null);
    }

    private void b(Canvas canvas) {
        int i;
        this.q = canvas.getWidth();
        int height = canvas.getHeight();
        this.r = height;
        int i2 = this.q;
        if (i2 == 0 || height == 0 || (i = this.e) == 0) {
            return;
        }
        this.s = height >> 1;
        this.t = height / 3.0f;
        this.i = this.j * 0.35f;
        int i3 = i + 1;
        this.o = new float[i3];
        this.p = new float[i3];
        float f = i2 / i;
        for (int i4 = 0; i4 <= this.e; i4++) {
            float f2 = i4 * f;
            this.o[i4] = f2;
            this.p[i4] = ((f2 / this.q) * 4.0f) - 2.0f;
        }
        this.m.setStyle(Paint.Style.STROKE);
    }

    private void h() {
        if (this.j > 10) {
            this.j = 10;
        }
        if (this.j < 1) {
            this.j = 1;
        }
    }

    private void i() {
        if (this.h > 35) {
            this.h = 35;
        }
    }

    private boolean j() {
        return this.o == null || this.p == null;
    }

    private void k() {
        for (int i = 0; i < this.n.size(); i++) {
            this.n.get(i).rewind();
            this.n.get(i).moveTo(0.0f, this.s);
        }
    }

    @Override // com.huawei.hms.mlplugin.asr.view.RenderView
    protected void a(Canvas canvas) {
        if (canvas == null) {
            SmartLogger.e("VoiceWaveView", "doDrawBackground(); canvas is null");
        } else if (this.y) {
            canvas.drawColor(this.k, PorterDuff.Mode.CLEAR);
        } else {
            canvas.drawColor(this.k);
        }
    }

    @Override // com.huawei.hms.mlplugin.asr.view.RenderView
    public void d() {
        this.w = 0;
        this.v = false;
        this.o = null;
        super.d();
    }

    @Override // com.huawei.hms.mlplugin.asr.view.RenderView
    public void e() {
        super.e();
        g();
    }

    public void g() {
        Canvas canvasLockCanvas = null;
        try {
            try {
                canvasLockCanvas = getHolder().lockCanvas(null);
                if (canvasLockCanvas == null) {
                    SmartLogger.e("VoiceWaveView", "clearDraw(), canvas is null");
                    if (canvasLockCanvas != null) {
                        getHolder().unlockCanvasAndPost(canvasLockCanvas);
                        return;
                    }
                    return;
                }
                canvasLockCanvas.drawColor(this.k);
                k();
                for (int i = 0; i < this.n.size(); i++) {
                    canvasLockCanvas.drawPath(this.n.get(i), this.m);
                }
                getHolder().unlockCanvasAndPost(canvasLockCanvas);
            } catch (RuntimeException e) {
                SmartLogger.e("VoiceWaveView", "RuntimeException e = " + e.getMessage());
                if (0 == 0) {
                }
            } catch (Exception e2) {
                SmartLogger.e("VoiceWaveView", "Exception e = " + e2.getMessage());
                if (0 == 0) {
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                getHolder().unlockCanvasAndPost(null);
            }
            throw th;
        }
    }

    public int getMaxVolume() {
        return 35;
    }

    public void setBackGroundColor(int i) {
        this.k = i;
        this.y = i == 0;
    }

    public void setMoveSpeed(float f) {
        this.f = f;
    }

    public void setSensibility(int i) {
        this.j = i;
        h();
    }

    public void setVolume(int i) {
        if (i < 1) {
            i = 1;
        }
        if (Math.abs(this.h - i) > this.i) {
            this.h = i;
            if (i > 35) {
                this.h = 35;
            }
        }
    }

    public VoiceWaveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VoiceWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = 0.0f;
        this.h = 12;
        this.k = -1;
        Paint paint = new Paint();
        this.m = paint;
        this.n = new ArrayList();
        this.u = new SparseArray<>();
        this.v = false;
        this.w = 0;
        this.x = false;
        this.y = false;
        this.z = Color.parseColor("#FB832E");
        this.A = Color.parseColor("#AC73FB");
        this.B = Color.parseColor("#43DAEE");
        paint.setDither(true);
        paint.setAntiAlias(true);
        for (int i2 = 0; i2 < 150; i2++) {
            this.n.add(new Path());
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.mlkitAsrWaveView);
        this.k = typedArrayObtainStyledAttributes.getColor(R.styleable.mlkitAsrWaveView_wlvBackgroundColor, -1);
        this.e = typedArrayObtainStyledAttributes.getInt(R.styleable.mlkitAsrWaveView_wlvSamplingSize, 64);
        this.l = (int) getResources().getDimension(R.dimen.mlkit_asr_wave_line_width);
        this.f = typedArrayObtainStyledAttributes.getFloat(R.styleable.mlkitAsrWaveView_wlvMoveSpeed, 500.0f);
        this.j = typedArrayObtainStyledAttributes.getInt(R.styleable.mlkitAsrWaveView_wlvSensibility, 5);
        this.y = this.k == 0;
        typedArrayObtainStyledAttributes.recycle();
        i();
        h();
        setZOrderOnTop(true);
        SurfaceHolder holder = getHolder();
        if (holder != null) {
            holder.setFormat(-3);
        }
    }

    @Override // com.huawei.hms.mlplugin.asr.view.RenderView
    protected void a(Canvas canvas, long j) {
        double dSin;
        double dPow;
        float f = j / this.f;
        if (j() || this.q != getWidth()) {
            b(canvas);
        }
        float f2 = 2.0f;
        int i = 0;
        if (!this.v && this.x) {
            this.n.get(0).moveTo(0.0f, this.s);
            this.n.get(1).moveTo(this.q, this.s);
            int i2 = 1;
            while (true) {
                int i3 = this.e;
                if (i2 > i3) {
                    break;
                }
                float f3 = ((i2 * 1.0f) * this.w) / i3;
                this.n.get(0).lineTo(f3, this.s);
                this.n.get(1).lineTo(this.q - f3, this.s);
                i2++;
            }
            this.n.get(0).moveTo(this.q / 2.0f, this.s);
            this.n.get(1).moveTo(this.q / 2.0f, this.s);
            this.w = (this.q / 60) + this.w;
            canvas.drawPath(this.n.get(0), this.m);
            canvas.drawPath(this.n.get(1), this.m);
            if (this.w <= this.q / 2) {
                return;
            } else {
                this.v = true;
            }
        }
        k();
        float f4 = this.g;
        float f5 = this.h;
        float f6 = this.i;
        if (f4 < f5 - f6) {
            this.g = f4 + f6;
        } else if (f4 > f5 + f6) {
            float f7 = f6 * 2.0f;
            if (f4 < f7) {
                this.g = f7;
            } else {
                this.g = f4 - f6;
            }
        } else {
            this.g = f5;
        }
        int i4 = 0;
        while (i4 <= this.e) {
            if (j()) {
                b(canvas);
                if (j()) {
                    return;
                }
            }
            float f8 = this.o[i4];
            int i5 = i;
            while (i5 < this.n.size()) {
                double d = this.t;
                float f9 = this.p[i4];
                int i6 = (int) (1000.0f * f9);
                float f10 = f % f2;
                if (i5 <= 30) {
                    double d2 = i5;
                    dSin = (Math.sin((((0.005d * d2) * 3.141592653589793d) + ((((double) f10) * 3.141592653589793d) + (((double) f9) * 3.7699111843077517d))) + 3.7699111843077517d) * (0.04d * d2)) - ((d2 * 0.01d) * 3.141592653589793d);
                } else {
                    double d3 = i5 - 90;
                    dSin = (Math.sin(((0.005d * d3) * 3.141592653589793d) + ((((double) f10) * 3.141592653589793d) + (((double) f9) * 3.141592653589793d))) * (0.03d * d3)) - ((d3 * 0.01d) * 3.141592653589793d);
                }
                if (this.u.indexOfKey(i6) >= 0) {
                    dPow = this.u.get(i6).doubleValue();
                } else {
                    double d4 = f9;
                    dPow = Math.pow(d4, 2.0d) + 4.0d != 0.0d ? 4.0d / (Math.pow(d4, 2.0d) + 4.0d) : 0.0d;
                    this.u.put(i6, Double.valueOf(dPow));
                }
                float f11 = ((float) (d * dSin * dPow)) * this.g * 0.02f;
                int i7 = i5;
                Path path = this.n.get(i7);
                float f12 = this.s + f11;
                float f13 = f8;
                path.lineTo(f13, f12);
                i5 = i7 + 1;
                f8 = f13;
                f = f;
                f2 = 2.0f;
            }
            i4++;
            f2 = 2.0f;
            i = 0;
        }
        for (int i8 = 0; i8 < this.n.size(); i8++) {
            this.n.get(i8).moveTo(this.q, this.s);
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, getWidth(), 0.0f, new int[]{-1, this.z, this.A, this.B, -1}, new float[]{0.005f, 0.1f, 0.5f, 0.9f, 0.995f}, Shader.TileMode.CLAMP);
        this.m.setStrokeWidth(this.l);
        this.m.setShader(linearGradient);
        for (int i9 = 0; i9 < this.n.size(); i9++) {
            this.m.setAlpha((int) (((double) i9) * 0.2d));
            canvas.drawPath(this.n.get(i9), this.m);
        }
    }
}
