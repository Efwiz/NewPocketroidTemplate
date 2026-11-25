package com.badlogic.gdx.backends.android;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.utils.GdxRuntimeException;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes8.dex */
public final class AndroidGraphicsLiveWallpaper extends AndroidGraphics {
    public AndroidGraphicsLiveWallpaper(AndroidLiveWallpaper androidLiveWallpaper, AndroidApplicationConfiguration androidApplicationConfiguration, ResolutionStrategy resolutionStrategy) {
        super(androidLiveWallpaper, androidApplicationConfiguration, resolutionStrategy, false);
    }

    SurfaceHolder getSurfaceHolder() {
        SurfaceHolder surfaceHolder;
        synchronized (((AndroidLiveWallpaper) this.f88app).service.sync) {
            surfaceHolder = ((AndroidLiveWallpaper) this.f88app).service.getSurfaceHolder();
        }
        return surfaceHolder;
    }

    @Override // com.badlogic.gdx.backends.android.AndroidGraphics
    protected GLSurfaceView20 createGLSurfaceView(AndroidApplicationBase androidApplicationBase, ResolutionStrategy resolutionStrategy) {
        if (!checkGL20()) {
            throw new GdxRuntimeException("libGDX requires OpenGL ES 2.0");
        }
        GLSurfaceView.EGLConfigChooser eglConfigChooser = getEglConfigChooser();
        GLSurfaceView20 gLSurfaceView20 = new GLSurfaceView20(androidApplicationBase.getContext(), resolutionStrategy) { // from class: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.1
            @Override // android.view.SurfaceView
            public SurfaceHolder getHolder() {
                return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
            }
        };
        if (eglConfigChooser != null) {
            gLSurfaceView20.setEGLConfigChooser(eglConfigChooser);
        } else {
            gLSurfaceView20.setEGLConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil);
        }
        gLSurfaceView20.setRenderer(this);
        return gLSurfaceView20;
    }

    public void onDestroyGLSurfaceView() {
        if (this.view != null) {
            try {
                this.view.onDetachedFromWindow();
                if (AndroidLiveWallpaperService.DEBUG) {
                    Log.d("WallpaperService", " > AndroidLiveWallpaper - onDestroy() stopped GLThread managed by GLSurfaceView");
                }
            } catch (Throwable th) {
                Log.e("WallpaperService", "failed to destroy GLSurfaceView's thread! GLSurfaceView.onDetachedFromWindow impl changed since API lvl 16!");
                th.printStackTrace();
            }
        }
    }

    @Override // com.badlogic.gdx.backends.android.AndroidGraphics
    void resume() {
        synchronized (this.synch) {
            this.running = true;
            this.resume = true;
            while (this.resume) {
                try {
                    requestRendering();
                    this.synch.wait();
                } catch (InterruptedException unused) {
                    Gdx.f82app.log("AndroidGraphics", "waiting for resume synchronization failed!");
                }
            }
        }
    }

    @Override // com.badlogic.gdx.backends.android.AndroidGraphics, android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        long jNanoTime = System.nanoTime();
        if (!this.resume) {
            this.deltaTime = (jNanoTime - this.lastFrameTime) / 1.0E9f;
        } else {
            this.deltaTime = 0.0f;
        }
        this.lastFrameTime = jNanoTime;
        synchronized (this.synch) {
            z = this.running;
            z2 = this.pause;
            z3 = this.destroy;
            z4 = this.resume;
            if (this.resume) {
                this.resume = false;
                this.synch.notifyAll();
            }
            if (this.pause) {
                this.pause = false;
                this.synch.notifyAll();
            }
            if (this.destroy) {
                this.destroy = false;
                this.synch.notifyAll();
            }
        }
        if (z4) {
            this.f88app.getApplicationListener().resume();
            Gdx.f82app.log("AndroidGraphics", "resumed");
        }
        if (z) {
            synchronized (this.f88app.getRunnables()) {
                this.f88app.getExecutedRunnables().clear();
                this.f88app.getExecutedRunnables().addAll(this.f88app.getRunnables());
                this.f88app.getRunnables().clear();
                for (int i = 0; i < this.f88app.getExecutedRunnables().size; i++) {
                    try {
                        this.f88app.getExecutedRunnables().get(i).run();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
            this.f88app.getInput().processEvents();
            this.frameId++;
            this.f88app.getApplicationListener().render();
        }
        if (z2) {
            this.f88app.getApplicationListener().pause();
            Gdx.f82app.log("AndroidGraphics", "paused");
        }
        if (z3) {
            this.f88app.getApplicationListener().dispose();
            Gdx.f82app.log("AndroidGraphics", "destroyed");
        }
        if (jNanoTime - this.frameStart > 1000000000) {
            this.fps = this.frames;
            this.frames = 0;
            this.frameStart = jNanoTime;
        }
        this.frames++;
    }

    @Override // com.badlogic.gdx.backends.android.AndroidGraphics
    protected void logManagedCachesStatus() {
        if (AndroidLiveWallpaperService.DEBUG) {
            super.logManagedCachesStatus();
        }
    }
}
