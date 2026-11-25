package com.badlogic.gdx.graphics.profiling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.GL31;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.math.FloatCounter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/profiling/GLProfiler.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class GLProfiler {
    private boolean enabled = false;
    private GLInterceptor glInterceptor;
    private Graphics graphics;
    private GLErrorListener listener;

    public GLProfiler(Graphics graphics) {
        this.graphics = graphics;
        GL32 gl32 = graphics.getGL32();
        GL31 gl31 = graphics.getGL31();
        GL30 gl30 = graphics.getGL30();
        if (gl32 != null) {
            this.glInterceptor = new GL32Interceptor(this, gl32);
        } else if (gl31 != null) {
            this.glInterceptor = new GL31Interceptor(this, gl31);
        } else if (gl30 != null) {
            this.glInterceptor = new GL30Interceptor(this, gl30);
        } else {
            this.glInterceptor = new GL20Interceptor(this, graphics.getGL20());
        }
        this.listener = GLErrorListener.LOGGING_LISTENER;
    }

    public void enable() {
        if (this.enabled) {
            return;
        }
        Object obj = this.glInterceptor;
        if (obj instanceof GL32) {
            this.graphics.setGL32((GL32) obj);
        }
        Object obj2 = this.glInterceptor;
        if (obj2 instanceof GL31) {
            this.graphics.setGL31((GL31) obj2);
        }
        Object obj3 = this.glInterceptor;
        if (obj3 instanceof GL30) {
            this.graphics.setGL30((GL30) obj3);
        }
        this.graphics.setGL20(this.glInterceptor);
        Gdx.gl32 = this.graphics.getGL32();
        Gdx.gl31 = this.graphics.getGL31();
        Gdx.gl30 = this.graphics.getGL30();
        Gdx.gl20 = this.graphics.getGL20();
        Gdx.gl = this.graphics.getGL20();
        this.enabled = true;
    }

    public void disable() {
        if (this.enabled) {
            GLInterceptor gLInterceptor = this.glInterceptor;
            if (gLInterceptor instanceof GL32Interceptor) {
                this.graphics.setGL32(((GL32Interceptor) gLInterceptor).gl32);
            }
            GLInterceptor gLInterceptor2 = this.glInterceptor;
            if (gLInterceptor2 instanceof GL31Interceptor) {
                this.graphics.setGL31(((GL31Interceptor) gLInterceptor2).gl31);
            }
            GLInterceptor gLInterceptor3 = this.glInterceptor;
            if (gLInterceptor3 instanceof GL30Interceptor) {
                this.graphics.setGL30(((GL30Interceptor) gLInterceptor3).gl30);
            }
            if (this.glInterceptor instanceof GL20Interceptor) {
                Graphics graphics = this.graphics;
                graphics.setGL20(((GL20Interceptor) graphics.getGL20()).gl20);
            }
            Gdx.gl32 = this.graphics.getGL32();
            Gdx.gl31 = this.graphics.getGL31();
            Gdx.gl30 = this.graphics.getGL30();
            Gdx.gl20 = this.graphics.getGL20();
            Gdx.gl = this.graphics.getGL20();
            this.enabled = false;
        }
    }

    public void setListener(GLErrorListener gLErrorListener) {
        this.listener = gLErrorListener;
    }

    public GLErrorListener getListener() {
        return this.listener;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getCalls() {
        return this.glInterceptor.getCalls();
    }

    public int getTextureBindings() {
        return this.glInterceptor.getTextureBindings();
    }

    public int getDrawCalls() {
        return this.glInterceptor.getDrawCalls();
    }

    public int getShaderSwitches() {
        return this.glInterceptor.getShaderSwitches();
    }

    public FloatCounter getVertexCount() {
        return this.glInterceptor.getVertexCount();
    }

    public void reset() {
        this.glInterceptor.reset();
    }
}
