package com.huawei.hms.mlplugin.asr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public abstract class RenderView extends SurfaceView implements SurfaceHolder.Callback {
    private static final Object d = new byte[0];
    private boolean a;
    private b b;
    private List<a> c;

    public interface a {
        void a(Canvas canvas, long j);
    }

    private static class b extends Thread {
        private WeakReference<RenderView> a;
        private boolean b;
        private boolean c;

        public b(RenderView renderView) {
            super("RenderThread");
            this.b = false;
            this.c = false;
            this.a = new WeakReference<>(renderView);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            while (!this.c) {
                synchronized (RenderView.d) {
                    if (this.b) {
                        RenderView renderView = this.a.get();
                        SurfaceHolder holder = renderView != null ? renderView.getHolder() : null;
                        RenderView renderView2 = this.a.get();
                        if (holder == null || renderView2 == null) {
                            this.b = false;
                            return;
                        }
                        Canvas canvasLockCanvas = holder.lockCanvas();
                        if (canvasLockCanvas != null) {
                            renderView2.a(canvasLockCanvas);
                            if (renderView2.a) {
                                RenderView.a(renderView2, canvasLockCanvas, SystemClock.elapsedRealtime() - jElapsedRealtime);
                            }
                            holder.unlockCanvasAndPost(canvasLockCanvas);
                        }
                    }
                }
                try {
                    Thread.sleep(16L);
                } catch (InterruptedException e) {
                    SmartLogger.e("RenderView", "InterruptedException e = " + e.getMessage());
                }
            }
        }

        public void a(boolean z) {
            this.b = z;
        }
    }

    public RenderView(Context context) {
        this(context, null);
    }

    private void g() {
        b bVar = this.b;
        if (bVar == null || bVar.b) {
            return;
        }
        this.b.a(true);
        try {
            if (this.b.getState() == Thread.State.NEW) {
                this.b.start();
            }
        } catch (Exception e) {
            SmartLogger.e("RenderView", "startThread e = " + e.getMessage());
        }
    }

    protected List<a> a() {
        return null;
    }

    protected abstract void a(Canvas canvas);

    protected abstract void a(Canvas canvas, long j);

    public void b() {
        SmartLogger.d("RenderView", "ASR excute onPause method");
    }

    public void c() {
        SmartLogger.d("RenderView", "ASR excute onResume method");
    }

    public void d() {
        this.a = true;
        g();
    }

    public void e() {
        this.a = false;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        if (z && this.a) {
            d();
        } else {
            g();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        List<a> listA = a();
        this.c = listA;
        if (listA != null && listA.isEmpty()) {
            throw new IllegalStateException();
        }
        this.b = new b(this);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (d) {
            this.b.a(false);
            this.b.c = true;
        }
    }

    public RenderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    static /* synthetic */ void a(RenderView renderView, Canvas canvas, long j) {
        List<a> list = renderView.c;
        if (list == null) {
            renderView.a(canvas, j);
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            renderView.c.get(i).a(canvas, j);
        }
    }

    public RenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = false;
        getHolder().addCallback(this);
    }
}
