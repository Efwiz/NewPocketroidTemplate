package com.badlogic.gdx.backends.android;

import android.app.WallpaperColors;
import android.os.Build;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes8.dex */
public abstract class AndroidLiveWallpaperService extends WallpaperService {
    static boolean DEBUG = false;
    static final String TAG = "WallpaperService";
    protected int viewFormat;
    protected int viewHeight;
    protected int viewWidth;

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    protected volatile AndroidLiveWallpaper f90app = null;
    protected SurfaceHolder.Callback view = null;
    protected int engines = 0;
    protected int visibleEngines = 0;
    protected volatile AndroidWallpaperEngine linkedEngine = null;
    protected volatile boolean isPreviewNotified = false;
    protected volatile boolean notifiedPreviewState = false;
    volatile int[] sync = new int[0];

    protected void setLinkedEngine(AndroidWallpaperEngine androidWallpaperEngine) {
        synchronized (this.sync) {
            this.linkedEngine = androidWallpaperEngine;
        }
    }

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public void onCreate() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onCreate() " + hashCode());
        }
        Log.i(TAG, "service created");
        super.onCreate();
    }

    @Override // android.service.wallpaper.WallpaperService
    public WallpaperService.Engine onCreateEngine() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onCreateEngine()");
        }
        Log.i(TAG, "engine created");
        return new AndroidWallpaperEngine();
    }

    public void onCreateApplication() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onCreateApplication()");
        }
    }

    public void initialize(ApplicationListener applicationListener) {
        initialize(applicationListener, new AndroidApplicationConfiguration());
    }

    public void initialize(ApplicationListener applicationListener, AndroidApplicationConfiguration androidApplicationConfiguration) {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - initialize()");
        }
        this.f90app.initialize(applicationListener, androidApplicationConfiguration);
        if (!androidApplicationConfiguration.getTouchEventsForLiveWallpaper || Integer.parseInt(Build.VERSION.SDK) < 7) {
            return;
        }
        this.linkedEngine.setTouchEventsEnabled(true);
    }

    public SurfaceHolder getSurfaceHolder() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - getSurfaceHolder()");
        }
        synchronized (this.sync) {
            if (this.linkedEngine == null) {
                return null;
            }
            return this.linkedEngine.getSurfaceHolder();
        }
    }

    public void onDeepPauseApplication() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onDeepPauseApplication()");
        }
        if (this.f90app != null) {
            this.f90app.graphics.clearManagedCaches();
        }
    }

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public void onDestroy() {
        if (DEBUG) {
            Log.d(TAG, " > AndroidLiveWallpaperService - onDestroy() " + hashCode());
        }
        Log.i(TAG, "service destroyed");
        super.onDestroy();
        if (this.f90app != null) {
            this.f90app.onDestroy();
            this.f90app = null;
            this.view = null;
        }
    }

    protected void finalize() throws Throwable {
        Log.i(TAG, "service finalized");
        super.finalize();
    }

    public AndroidLiveWallpaper getLiveWallpaper() {
        return this.f90app;
    }

    public WindowManager getWindowManager() {
        return (WindowManager) getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
    }

    public class AndroidWallpaperEngine extends WallpaperService.Engine {
        protected int engineFormat;
        protected int engineHeight;
        protected boolean engineIsVisible;
        protected int engineWidth;
        boolean iconDropConsumed;
        boolean offsetsConsumed;
        int xIconDrop;
        float xOffset;
        float xOffsetStep;
        int xPixelOffset;
        int yIconDrop;
        float yOffset;
        float yOffsetStep;
        int yPixelOffset;

        public AndroidWallpaperEngine() {
            super(AndroidLiveWallpaperService.this);
            this.engineIsVisible = false;
            this.iconDropConsumed = true;
            this.offsetsConsumed = true;
            this.xOffset = 0.0f;
            this.yOffset = 0.0f;
            this.xOffsetStep = 0.0f;
            this.yOffsetStep = 0.0f;
            this.xPixelOffset = 0;
            this.yPixelOffset = 0;
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine() " + hashCode());
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onCreate(SurfaceHolder surfaceHolder) {
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onCreate() " + hashCode() + " running: " + AndroidLiveWallpaperService.this.engines + ", linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this) + ", thread: " + Thread.currentThread().toString());
            }
            super.onCreate(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            AndroidLiveWallpaperService.this.engines++;
            AndroidLiveWallpaperService.this.setLinkedEngine(this);
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onSurfaceCreated() " + hashCode() + ", running: " + AndroidLiveWallpaperService.this.engines + ", linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this));
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface created");
            super.onSurfaceCreated(surfaceHolder);
            if (AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.visibleEngines = 0;
            }
            if (AndroidLiveWallpaperService.this.engines == 1 && AndroidLiveWallpaperService.this.f90app == null) {
                AndroidLiveWallpaperService.this.viewFormat = 0;
                AndroidLiveWallpaperService.this.viewWidth = 0;
                AndroidLiveWallpaperService.this.viewHeight = 0;
                AndroidLiveWallpaperService.this.f90app = new AndroidLiveWallpaper(AndroidLiveWallpaperService.this);
                AndroidLiveWallpaperService.this.onCreateApplication();
                if (AndroidLiveWallpaperService.this.f90app.graphics == null) {
                    throw new Error("You must override 'AndroidLiveWallpaperService.onCreateApplication' method and call 'initialize' from its body.");
                }
            }
            AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
            androidLiveWallpaperService.view = androidLiveWallpaperService.f90app.graphics.view;
            getSurfaceHolder().removeCallback(AndroidLiveWallpaperService.this.view);
            this.engineFormat = AndroidLiveWallpaperService.this.viewFormat;
            this.engineWidth = AndroidLiveWallpaperService.this.viewWidth;
            this.engineHeight = AndroidLiveWallpaperService.this.viewHeight;
            if (AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.view.surfaceCreated(surfaceHolder);
            } else {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(surfaceHolder);
                notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                AndroidLiveWallpaperService.this.view.surfaceCreated(surfaceHolder);
            }
            notifyPreviewState();
            notifyOffsetsChanged();
            if (Gdx.graphics.isContinuousRendering()) {
                return;
            }
            Gdx.graphics.requestRendering();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onSurfaceChanged() isPreview: " + isPreview() + ", " + hashCode() + ", running: " + AndroidLiveWallpaperService.this.engines + ", linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this) + ", sufcace valid: " + getSurfaceHolder().getSurface().isValid());
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface changed");
            super.onSurfaceChanged(surfaceHolder, i, i2, i3);
            notifySurfaceChanged(i, i2, i3, true);
        }

        private void notifySurfaceChanged(int i, int i2, int i3, boolean z) {
            if (!z && i == AndroidLiveWallpaperService.this.viewFormat && i2 == AndroidLiveWallpaperService.this.viewWidth && i3 == AndroidLiveWallpaperService.this.viewHeight) {
                if (AndroidLiveWallpaperService.DEBUG) {
                    Log.d(AndroidLiveWallpaperService.TAG, " > surface is current, skipping surfaceChanged event");
                    return;
                }
                return;
            }
            this.engineFormat = i;
            this.engineWidth = i2;
            this.engineHeight = i3;
            if (AndroidLiveWallpaperService.this.linkedEngine == this) {
                AndroidLiveWallpaperService.this.viewFormat = this.engineFormat;
                AndroidLiveWallpaperService.this.viewWidth = this.engineWidth;
                AndroidLiveWallpaperService.this.viewHeight = this.engineHeight;
                AndroidLiveWallpaperService.this.view.surfaceChanged(getSurfaceHolder(), AndroidLiveWallpaperService.this.viewFormat, AndroidLiveWallpaperService.this.viewWidth, AndroidLiveWallpaperService.this.viewHeight);
                return;
            }
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > engine is not active, skipping surfaceChanged event");
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onVisibilityChanged(boolean z) {
            boolean zIsVisible = isVisible();
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onVisibilityChanged(paramVisible: " + z + " reportedVisible: " + zIsVisible + ") " + hashCode() + ", sufcace valid: " + getSurfaceHolder().getSurface().isValid());
            }
            super.onVisibilityChanged(z);
            if (!zIsVisible && z) {
                if (AndroidLiveWallpaperService.DEBUG) {
                    Log.d(AndroidLiveWallpaperService.TAG, " > fake visibilityChanged event! Android WallpaperService likes do that!");
                    return;
                }
                return;
            }
            notifyVisibilityChanged(z);
        }

        private void notifyVisibilityChanged(boolean z) {
            if (this.engineIsVisible != z) {
                this.engineIsVisible = z;
                if (z) {
                    onResume();
                    return;
                } else {
                    onPause();
                    return;
                }
            }
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > visible state is current, skipping visibilityChanged event!");
            }
        }

        public void onResume() {
            AndroidLiveWallpaperService.this.visibleEngines++;
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onResume() " + hashCode() + ", running: " + AndroidLiveWallpaperService.this.engines + ", linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this) + ", visible: " + AndroidLiveWallpaperService.this.visibleEngines);
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine resumed");
            if (AndroidLiveWallpaperService.this.linkedEngine != null) {
                if (AndroidLiveWallpaperService.this.linkedEngine != this) {
                    AndroidLiveWallpaperService.this.setLinkedEngine(this);
                    AndroidLiveWallpaperService.this.view.surfaceDestroyed(getSurfaceHolder());
                    notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                    AndroidLiveWallpaperService.this.view.surfaceCreated(getSurfaceHolder());
                } else {
                    notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                }
                if (AndroidLiveWallpaperService.this.visibleEngines == 1) {
                    AndroidLiveWallpaperService.this.f90app.onResume();
                }
                notifyPreviewState();
                notifyOffsetsChanged();
                if (Gdx.graphics.isContinuousRendering()) {
                    return;
                }
                Gdx.graphics.requestRendering();
            }
        }

        public void onPause() {
            AndroidLiveWallpaperService.this.visibleEngines--;
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onPause() " + hashCode() + ", running: " + AndroidLiveWallpaperService.this.engines + ", linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this) + ", visible: " + AndroidLiveWallpaperService.this.visibleEngines);
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine paused");
            if (AndroidLiveWallpaperService.this.visibleEngines >= AndroidLiveWallpaperService.this.engines) {
                Log.e(AndroidLiveWallpaperService.TAG, "wallpaper lifecycle error, counted too many visible engines! repairing..");
                AndroidLiveWallpaperService androidLiveWallpaperService = AndroidLiveWallpaperService.this;
                androidLiveWallpaperService.visibleEngines = Math.max(androidLiveWallpaperService.engines - 1, 0);
            }
            if (AndroidLiveWallpaperService.this.linkedEngine != null && AndroidLiveWallpaperService.this.visibleEngines == 0) {
                AndroidLiveWallpaperService.this.f90app.onPause();
            }
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onPause() done!");
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            AndroidLiveWallpaperService.this.engines--;
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onSurfaceDestroyed() " + hashCode() + ", running: " + AndroidLiveWallpaperService.this.engines + " ,linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this) + ", isVisible: " + this.engineIsVisible);
            }
            Log.i(AndroidLiveWallpaperService.TAG, "engine surface destroyed");
            if (AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.onDeepPauseApplication();
            }
            if (AndroidLiveWallpaperService.this.linkedEngine == this && AndroidLiveWallpaperService.this.view != null) {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(surfaceHolder);
            }
            this.engineFormat = 0;
            this.engineWidth = 0;
            this.engineHeight = 0;
            if (AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.linkedEngine = null;
            }
            super.onSurfaceDestroyed(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onDestroy() {
            super.onDestroy();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            if (AndroidLiveWallpaperService.DEBUG) {
                Log.d(AndroidLiveWallpaperService.TAG, " > AndroidWallpaperEngine - onCommand(" + str + " " + i + " " + i2 + " " + i3 + " " + bundle + " " + z + "), linked: " + (AndroidLiveWallpaperService.this.linkedEngine == this));
            }
            if (str.equals("android.home.drop")) {
                this.iconDropConsumed = false;
                this.xIconDrop = i;
                this.yIconDrop = i2;
                notifyIconDropped();
            }
            return super.onCommand(str, i, i2, i3, bundle, z);
        }

        protected void notifyIconDropped() {
            if (AndroidLiveWallpaperService.this.linkedEngine == this && (AndroidLiveWallpaperService.this.f90app.listener instanceof AndroidWallpaperListener) && !this.iconDropConsumed) {
                this.iconDropConsumed = true;
                AndroidLiveWallpaperService.this.f90app.postRunnable(new Runnable() { // from class: com.badlogic.gdx.backends.android.AndroidLiveWallpaperService.AndroidWallpaperEngine.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AndroidWallpaperEngine androidWallpaperEngine;
                        boolean z;
                        synchronized (AndroidLiveWallpaperService.this.sync) {
                            AndroidWallpaperEngine androidWallpaperEngine2 = AndroidLiveWallpaperService.this.linkedEngine;
                            androidWallpaperEngine = AndroidWallpaperEngine.this;
                            z = androidWallpaperEngine2 == androidWallpaperEngine;
                        }
                        if (z) {
                            ((AndroidWallpaperListener) AndroidLiveWallpaperService.this.f90app.listener).iconDropped(AndroidWallpaperEngine.this.xIconDrop, AndroidWallpaperEngine.this.yIconDrop);
                        }
                    }
                });
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onTouchEvent(MotionEvent motionEvent) {
            if (AndroidLiveWallpaperService.this.linkedEngine == this) {
                AndroidLiveWallpaperService.this.f90app.input.onTouch(null, motionEvent);
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            this.offsetsConsumed = false;
            this.xOffset = f;
            this.yOffset = f2;
            this.xOffsetStep = f3;
            this.yOffsetStep = f4;
            this.xPixelOffset = i;
            this.yPixelOffset = i2;
            notifyOffsetsChanged();
            if (!Gdx.graphics.isContinuousRendering()) {
                Gdx.graphics.requestRendering();
            }
            super.onOffsetsChanged(f, f2, f3, f4, i, i2);
        }

        protected void notifyOffsetsChanged() {
            if (AndroidLiveWallpaperService.this.linkedEngine == this && (AndroidLiveWallpaperService.this.f90app.listener instanceof AndroidWallpaperListener) && !this.offsetsConsumed) {
                this.offsetsConsumed = true;
                AndroidLiveWallpaperService.this.f90app.postRunnable(new Runnable() { // from class: com.badlogic.gdx.backends.android.AndroidLiveWallpaperService.AndroidWallpaperEngine.2
                    @Override // java.lang.Runnable
                    public void run() {
                        AndroidWallpaperEngine androidWallpaperEngine;
                        boolean z;
                        synchronized (AndroidLiveWallpaperService.this.sync) {
                            AndroidWallpaperEngine androidWallpaperEngine2 = AndroidLiveWallpaperService.this.linkedEngine;
                            androidWallpaperEngine = AndroidWallpaperEngine.this;
                            z = androidWallpaperEngine2 == androidWallpaperEngine;
                        }
                        if (z) {
                            ((AndroidWallpaperListener) AndroidLiveWallpaperService.this.f90app.listener).offsetChange(AndroidWallpaperEngine.this.xOffset, AndroidWallpaperEngine.this.yOffset, AndroidWallpaperEngine.this.xOffsetStep, AndroidWallpaperEngine.this.yOffsetStep, AndroidWallpaperEngine.this.xPixelOffset, AndroidWallpaperEngine.this.yPixelOffset);
                        }
                    }
                });
            }
        }

        protected void notifyPreviewState() {
            if (AndroidLiveWallpaperService.this.linkedEngine == this && (AndroidLiveWallpaperService.this.f90app.listener instanceof AndroidWallpaperListener)) {
                final boolean zIsPreview = AndroidLiveWallpaperService.this.linkedEngine.isPreview();
                AndroidLiveWallpaperService.this.f90app.postRunnable(new Runnable() { // from class: com.badlogic.gdx.backends.android.AndroidLiveWallpaperService.AndroidWallpaperEngine.3
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean z;
                        AndroidLiveWallpaper androidLiveWallpaper;
                        synchronized (AndroidLiveWallpaperService.this.sync) {
                            if (AndroidLiveWallpaperService.this.isPreviewNotified && AndroidLiveWallpaperService.this.notifiedPreviewState == zIsPreview) {
                                z = false;
                            } else {
                                AndroidLiveWallpaperService.this.notifiedPreviewState = zIsPreview;
                                AndroidLiveWallpaperService.this.isPreviewNotified = true;
                                z = true;
                            }
                        }
                        if (!z || (androidLiveWallpaper = AndroidLiveWallpaperService.this.f90app) == null) {
                            return;
                        }
                        ((AndroidWallpaperListener) androidLiveWallpaper.listener).previewStateChange(zIsPreview);
                    }
                });
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public WallpaperColors onComputeColors() {
            Color[] colorArr;
            Application application = Gdx.f82app;
            if (Build.VERSION.SDK_INT >= 27 && (application instanceof AndroidLiveWallpaper) && (colorArr = ((AndroidLiveWallpaper) application).wallpaperColors) != null) {
                return new WallpaperColors(android.graphics.Color.valueOf(colorArr[0].r, colorArr[0].g, colorArr[0].b, colorArr[0].a), android.graphics.Color.valueOf(colorArr[1].r, colorArr[1].g, colorArr[1].b, colorArr[1].a), android.graphics.Color.valueOf(colorArr[2].r, colorArr[2].g, colorArr[2].b, colorArr[2].a));
            }
            return super.onComputeColors();
        }
    }
}
