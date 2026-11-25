package com.huawei.hms.mlsdk.common;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import com.huawei.hms.common.size.Size;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.common.ha.HianalyticsLog;
import com.huawei.hms.mlkit.common.ha.HianalyticsLogProvider;
import com.huawei.hms.mlsdk.common.lens.LensRequest;
import com.huawei.hms.mlsdk.common.lens.LensResponse;
import com.huawei.hms.mlsdk.common.lens.MLLensSelector;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class LensEngine {
    public static final int BACK_LENS = 0;
    public static final int FRONT_LENS = 1;
    public static final String LENS_ENGINE_KEY = "MLKitLensEngine";
    public Context b;
    public LensRequest c;
    public MLLensSelector d;
    public LensResponse e;
    public Thread g;
    public LensAnalyzerRunable h;
    public Map<byte[], FrameMetadataHolder> a = new IdentityHashMap();
    public Map<byte[], ByteBuffer> f = new IdentityHashMap();

    public static class Creator {
        public MLAnalyzer<?> a;
        public LensEngine b;

        public Creator(Context context, MLAnalyzer<?> mLAnalyzer) {
            this(context, mLAnalyzer, null, null);
        }

        public Creator applyDisplayDimension(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                throw new IllegalArgumentException(new StringBuilder(45).append("Invalid display dimension, width=").append(i).append(", height=").append(i2).toString());
            }
            this.b.c.setDisplaySize(new Size(i, i2));
            return this;
        }

        public Creator applyFps(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException(new StringBuilder(28).append("Invalid fps: ").append(f).toString());
            }
            this.b.c.setFps((int) f);
            return this;
        }

        public LensEngine create() {
            HianalyticsLogProvider.getInstance().initTimer(LensEngine.LENS_ENGINE_KEY);
            LensEngine lensEngine = this.b;
            lensEngine.getClass();
            lensEngine.h = lensEngine.new LensAnalyzerRunable(this.a);
            return this.b;
        }

        public Creator enableAutomaticFocus(boolean z) {
            if (z) {
                this.b.c.setFocusMode("continuous-video");
            }
            return this;
        }

        public Creator setFlashMode(String str) {
            if (!"auto".equals(str) && !"on".equals(str) && !"off".equals(str)) {
                throw new IllegalArgumentException(new StringBuilder(27).append("Invalid flash mode: ").append(str).toString());
            }
            this.b.c.setFlashMode(str);
            return this;
        }

        public Creator setFocusMode(String str) {
            if (!"continuous-picture".equals(str) && !"continuous-video".equals(str)) {
                throw new IllegalArgumentException(new StringBuilder(27).append("Invalid focus mode: ").append(str).toString());
            }
            this.b.c.setFocusMode(str);
            return this;
        }

        public Creator setLensType(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException(new StringBuilder(27).append("Invalid lens type: ").append(i).toString());
            }
            this.b.c.setLensType(i);
            return this;
        }

        public Creator(Context context, MLAnalyzer<?> mLAnalyzer, LensRequest lensRequest) {
            this(context, mLAnalyzer, lensRequest, null);
        }

        public Creator(Context context, MLAnalyzer<?> mLAnalyzer, LensRequest lensRequest, MLLensSelector mLLensSelector) {
            this.b = new LensEngine(null);
            if (context == null) {
                throw new IllegalArgumentException("Not any context specified.");
            }
            if (mLAnalyzer != null) {
                if (lensRequest == null) {
                    lensRequest = new LensRequest();
                    lensRequest.setLensType(0);
                    lensRequest.setFps(20);
                    lensRequest.setDisplaySize(new Size(640, 480));
                    lensRequest.setFocusMode("continuous-video");
                    lensRequest.setFlashMode("auto");
                }
                mLLensSelector = mLLensSelector == null ? new MLLensSelector() : mLLensSelector;
                LensEngine lensEngine = this.b;
                lensEngine.b = context;
                lensEngine.c = lensRequest;
                lensEngine.d = mLLensSelector;
                this.a = mLAnalyzer;
                return;
            }
            throw new IllegalArgumentException("Not any analyzer specified.");
        }
    }

    public static class FrameMetadataHolder {
        public int facing;
        public int height;
        public int quadrant;
        public int width;

        public FrameMetadataHolder() {
        }

        public /* synthetic */ FrameMetadataHolder(AnonymousClass1 anonymousClass1) {
        }
    }

    public class LensAnalyzerRunable implements Runnable {
        public MLAnalyzer<?> b;
        public long d;
        public ByteBuffer g;
        public final Object a = new Object();
        public long c = SystemClock.elapsedRealtime();
        public boolean e = true;
        public int f = 0;

        public LensAnalyzerRunable(MLAnalyzer<?> mLAnalyzer) {
            this.b = mLAnalyzer;
        }

        public final boolean a(ByteBuffer byteBuffer, Camera.Size size, int i, int i2, FrameMetadataHolder frameMetadataHolder) {
            return this.e && frameMetadataHolder != null && frameMetadataHolder.facing == LensEngine.this.c.getLensType() && frameMetadataHolder.width == size.width && frameMetadataHolder.height == size.height && frameMetadataHolder.quadrant == i && byteBuffer.array().length == i2;
        }

        public void processNextFrame(byte[] bArr, Camera camera) {
            synchronized (this.a) {
                ByteBuffer byteBuffer = this.g;
                if (byteBuffer != null) {
                    this.g = null;
                    byteBuffer.clear();
                    camera.addCallbackBuffer(byteBuffer.array());
                }
                if (LensEngine.this.f.containsKey(bArr)) {
                    this.d = SystemClock.elapsedRealtime() - this.c;
                    this.f++;
                    this.g = LensEngine.this.f.get(bArr);
                    this.a.notifyAll();
                }
            }
        }

        public void release() {
            MLAnalyzer<?> mLAnalyzer = this.b;
            if (mLAnalyzer != null) {
                mLAnalyzer.destroy();
                this.b = null;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            ByteBuffer byteBuffer;
            Throwable th;
            MLFrame mLFrameCreate;
            Bundle bundle = MLApplication.initialize(LensEngine.this.b).toBundle();
            while (true) {
                HianalyticsLog apkVersion = !HianalyticsLogProvider.getInstance().sdkForbiddenHiLog(LensEngine.this.b) ? HianalyticsLogProvider.getInstance().logBegin(LensEngine.this.b, bundle).setModuleName(LensEngine.LENS_ENGINE_KEY).setApiName(LensEngine.LENS_ENGINE_KEY).setApkVersion("3.7.0.302") : null;
                synchronized (this.a) {
                    while (true) {
                        z = this.e;
                        if (!z || this.g != null) {
                            break;
                        }
                        try {
                            this.a.wait();
                        } catch (InterruptedException e) {
                            SmartLog.e("LensAnalyzerRunable", "Frame analyzing interrupted.", e);
                            return;
                        }
                    }
                    if (!z) {
                        this.g = null;
                        return;
                    }
                    try {
                        Camera.Parameters parameters = LensEngine.this.e.getLens().getParameters();
                        mLFrameCreate = new MLFrame.Creator().writeByteBufferData(this.g, parameters.getPreviewSize().width, parameters.getPreviewSize().height, 17).setItemIdentity(this.f).setTimestamp(this.d).setQuadrant(LensEngine.this.e.getQuadrant()).setFramePropertyExt(new MLFrame.Property.Ext.Creator().setZoom(parameters.getZoom()).setMaxZoom(parameters.getMaxZoom()).build()).create();
                        try {
                            byteBuffer = this.g;
                            try {
                                this.g = null;
                            } catch (Throwable th2) {
                                th = th2;
                                SmartLog.e("LensAnalyzerRunable", "first Parameters, Exception thrown from receiver.", th);
                            }
                        } catch (Throwable th3) {
                            byteBuffer = null;
                            th = th3;
                        }
                    } catch (Throwable th4) {
                        byteBuffer = null;
                        th = th4;
                        mLFrameCreate = null;
                    }
                    ByteBuffer byteBuffer2 = byteBuffer;
                    if (byteBuffer2 == null) {
                        return;
                    }
                    try {
                        Camera.Size previewSize = LensEngine.this.e.getLens().getParameters().getPreviewSize();
                        try {
                            if (a(byteBuffer2, previewSize, LensEngine.this.e.getQuadrant(), (((previewSize.width * previewSize.height) * 3) / 2) + 1, LensEngine.this.a.get(byteBuffer2.array()))) {
                                this.b.obtainPicture(mLFrameCreate);
                            }
                        } catch (NullPointerException e2) {
                            SmartLog.e("LensAnalyzerRunable", "null pointer exception.", e2);
                        }
                        byteBuffer2.clear();
                        LensEngine.this.e.getLens().addCallbackBuffer(byteBuffer2.array());
                        if (apkVersion != null) {
                            HianalyticsLogProvider.getInstance().logEnd(apkVersion);
                        }
                    } catch (Throwable th5) {
                        try {
                            SmartLog.e("LensAnalyzerRunable", "second Parameters, Exception thrown from receiver.", th5);
                            byteBuffer2.clear();
                            LensEngine.this.e.getLens().addCallbackBuffer(byteBuffer2.array());
                            if (apkVersion != null) {
                            }
                        } catch (Throwable th6) {
                            byteBuffer2.clear();
                            LensEngine.this.e.getLens().addCallbackBuffer(byteBuffer2.array());
                            if (apkVersion != null) {
                                HianalyticsLogProvider.getInstance().logEnd(apkVersion);
                            }
                            throw th6;
                        }
                    }
                }
            }
        }

        public void setActive(boolean z) {
            synchronized (this.a) {
                this.e = z;
                this.a.notifyAll();
            }
        }
    }

    public class LensPhotograph implements Camera.PictureCallback {
        public PhotographListener a;

        public /* synthetic */ LensPhotograph(PhotographListener photographListener, AnonymousClass1 anonymousClass1) {
            this.a = photographListener;
        }

        @Override // android.hardware.Camera.PictureCallback
        public void onPictureTaken(byte[] bArr, Camera camera) {
            PhotographListener photographListener = this.a;
            if (photographListener != null) {
                photographListener.takenPhotograph(bArr);
            }
            if (LensEngine.this.e.getLens() != null) {
                LensEngine.this.e.getLens().startPreview();
            }
        }
    }

    public static class LensPhotographListener implements PhotographListener {
        @Override // com.huawei.hms.mlsdk.common.LensEngine.PhotographListener
        public void takenPhotograph(byte[] bArr) {
        }
    }

    public class LensPreviewer implements Camera.PreviewCallback {
        public /* synthetic */ LensPreviewer(AnonymousClass1 anonymousClass1) {
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] bArr, Camera camera) {
            LensEngine.this.h.processNextFrame(bArr, camera);
        }
    }

    public static class LensShutter implements Camera.ShutterCallback {
        public ShutterListener a;

        public /* synthetic */ LensShutter(ShutterListener shutterListener, AnonymousClass1 anonymousClass1) {
            this.a = shutterListener;
        }

        @Override // android.hardware.Camera.ShutterCallback
        public void onShutter() {
            ShutterListener shutterListener = this.a;
            if (shutterListener != null) {
                shutterListener.clickShutter();
            }
        }
    }

    public static class LensShutterListener implements ShutterListener {
        @Override // com.huawei.hms.mlsdk.common.LensEngine.ShutterListener
        public void clickShutter() {
        }
    }

    public interface PhotographListener {
        void takenPhotograph(byte[] bArr);
    }

    public interface ShutterListener {
        void clickShutter();
    }

    public static class SurfaceWrapper {
        public SurfaceHolder a;
        public SurfaceTexture b;

        public SurfaceWrapper() {
        }

        public SurfaceHolder getSurfaceHolder() {
            return this.a;
        }

        public SurfaceTexture getSurfaceTexture() {
            return this.b;
        }

        public int getSurfaceType() {
            return this.a != null ? 1 : 2;
        }

        public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
            this.a = surfaceHolder;
        }

        public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
            this.b = surfaceTexture;
        }

        public /* synthetic */ SurfaceWrapper(AnonymousClass1 anonymousClass1) {
        }
    }

    public LensEngine() {
    }

    public final LensEngine a(SurfaceWrapper surfaceWrapper) throws IOException {
        LensResponse lensResponse = this.e;
        if (lensResponse != null && lensResponse.getLens() != null) {
            return this;
        }
        LensResponse lensResponseSelectLens = this.d.selectLens(this.b, this.c);
        this.e = lensResponseSelectLens;
        if (lensResponseSelectLens == null || lensResponseSelectLens.getLens() == null) {
            throw new IOException("Failed to select a suitable lens");
        }
        AnonymousClass1 anonymousClass1 = null;
        this.e.getLens().setPreviewCallbackWithBuffer(new LensPreviewer(anonymousClass1));
        Camera lens = this.e.getLens();
        Camera.Parameters parameters = lens.getParameters();
        for (int i = 0; i < 4; i++) {
            Size size = new Size(parameters.getPreviewSize().width, parameters.getPreviewSize().height);
            byte[] bArr = new byte[((int) Math.ceil((((long) (size.getWidth() * size.getHeight())) * ((long) ImageFormat.getBitsPerPixel(17))) / 8.0d)) + 1];
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            if (!byteBufferWrap.hasArray() || byteBufferWrap.array() != bArr) {
                throw new IllegalStateException("Failed to create frame buffer with specified size.");
            }
            this.f.put(bArr, byteBufferWrap);
            FrameMetadataHolder frameMetadataHolder = new FrameMetadataHolder(anonymousClass1);
            frameMetadataHolder.width = parameters.getPreviewSize().width;
            frameMetadataHolder.height = parameters.getPreviewSize().height;
            frameMetadataHolder.quadrant = this.e.getQuadrant();
            frameMetadataHolder.facing = this.c.getLensType();
            this.a.put(bArr, frameMetadataHolder);
            lens.addCallbackBuffer(bArr);
        }
        if (surfaceWrapper.getSurfaceType() == 1) {
            lens.setPreviewDisplay(surfaceWrapper.getSurfaceHolder());
        } else {
            lens.setPreviewTexture(surfaceWrapper.getSurfaceTexture());
        }
        lens.startPreview();
        this.g = new Thread(this.h);
        this.h.setActive(true);
        this.g.start();
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0023 A[DONT_GENERATE] */
    /* JADX WARN: Code duplicated, block: B:19:0x0025 A[Catch: all -> 0x004b, TRY_ENTER, TryCatch #2 {, blocks: (B:3:0x0001, B:8:0x000a, B:11:0x0012, B:14:0x001d, B:15:0x001f, B:19:0x0025, B:21:0x002b, B:22:0x0031, B:25:0x003c, B:24:0x0035, B:26:0x0044, B:13:0x0016), top: B:36:0x0001, inners: #0, #1 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x002b A[Catch: all -> 0x004b, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:8:0x000a, B:11:0x0012, B:14:0x001d, B:15:0x001f, B:19:0x0025, B:21:0x002b, B:22:0x0031, B:25:0x003c, B:24:0x0035, B:26:0x0044, B:13:0x0016), top: B:36:0x0001, inners: #0, #1 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:17:0x0023, please report this as an issue */
    public synchronized void close() {
        LensResponse lensResponse;
        Camera lens;
        LensAnalyzerRunable lensAnalyzerRunable = this.h;
        if (lensAnalyzerRunable.e) {
            lensAnalyzerRunable.setActive(false);
            Thread thread = this.g;
            if (thread == null) {
                lensResponse = this.e;
                if (lensResponse == null) {
                    return;
                }
                lens = lensResponse.getLens();
                if (lens != null) {
                    lens.stopPreview();
                    lens.setPreviewCallbackWithBuffer(null);
                    lens.setPreviewTexture(null);
                    lens.release();
                    this.e.setLens(null);
                }
                this.f.clear();
                return;
            }
            try {
                thread.join();
            } catch (InterruptedException unused) {
                SmartLog.e("LensEngine", "Analyse thread interrupted.");
            }
            this.g = null;
            lensResponse = this.e;
            if (lensResponse == null) {
                return;
            }
            lens = lensResponse.getLens();
            if (lens != null) {
                lens.stopPreview();
                lens.setPreviewCallbackWithBuffer(null);
                try {
                    lens.setPreviewTexture(null);
                } catch (IOException unused2) {
                    SmartLog.e("LensEngine", "Failed to stop lens");
                }
                lens.release();
                this.e.setLens(null);
            }
            this.f.clear();
            return;
            throw th;
        }
    }

    public synchronized int doZoom(float f) {
        int i = 0;
        if (this.e.getLens() == null) {
            return 0;
        }
        Camera.Parameters parameters = this.e.getLens().getParameters();
        if (!parameters.isZoomSupported()) {
            return 0;
        }
        int maxZoom = parameters.getMaxZoom();
        float zoom = parameters.getZoom() + 1;
        int iRound = Math.round(f > 1.0f ? ((f * maxZoom) / 10.0f) + zoom : f * zoom) - 1;
        if (iRound >= 0) {
            i = iRound > maxZoom ? maxZoom : iRound;
        }
        parameters.setZoom(i);
        this.e.getLens().setParameters(parameters);
        return i;
    }

    public Size getDisplayDimension() {
        Camera lens = getLens();
        if (lens == null) {
            return new Size(640, 480);
        }
        Camera.Size previewSize = lens.getParameters().getPreviewSize();
        return new Size(previewSize.width, previewSize.height);
    }

    public Camera getLens() {
        LensResponse lensResponse = this.e;
        if (lensResponse != null) {
            return lensResponse.getLens();
        }
        return null;
    }

    public int getLensType() {
        return this.c.getLensType();
    }

    public void photograph(ShutterListener shutterListener, PhotographListener photographListener) {
        LensResponse lensResponse = this.e;
        if (lensResponse == null || lensResponse.getLens() == null) {
            return;
        }
        AnonymousClass1 anonymousClass1 = null;
        this.e.getLens().takePicture(new LensShutter(shutterListener, anonymousClass1), null, null, new LensPhotograph(photographListener, anonymousClass1));
    }

    public synchronized void release() {
        if (this.g != null) {
            close();
            this.h.release();
        }
        HianalyticsLogProvider.getInstance().reportAndCancelTimer(LENS_ENGINE_KEY);
    }

    public synchronized LensEngine run() throws IOException {
        return run(new SurfaceTexture(100));
    }

    public synchronized LensEngine run(SurfaceHolder surfaceHolder) throws IOException {
        SurfaceWrapper surfaceWrapper;
        surfaceWrapper = new SurfaceWrapper(null);
        surfaceWrapper.setSurfaceHolder(surfaceHolder);
        return a(surfaceWrapper);
    }

    public synchronized LensEngine run(SurfaceTexture surfaceTexture) throws IOException {
        SurfaceWrapper surfaceWrapper;
        surfaceWrapper = new SurfaceWrapper(null);
        surfaceWrapper.setSurfaceTexture(surfaceTexture);
        return a(surfaceWrapper);
    }

    public /* synthetic */ LensEngine(AnonymousClass1 anonymousClass1) {
    }
}
