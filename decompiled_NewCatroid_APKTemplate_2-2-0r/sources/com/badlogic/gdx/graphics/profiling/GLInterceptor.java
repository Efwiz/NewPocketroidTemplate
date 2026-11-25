package com.badlogic.gdx.graphics.profiling;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.math.FloatCounter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/profiling/GLInterceptor.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class GLInterceptor implements GL20 {
    protected int calls;
    protected int drawCalls;
    protected GLProfiler glProfiler;
    protected int shaderSwitches;
    protected int textureBindings;
    protected final FloatCounter vertexCount = new FloatCounter(0);

    protected GLInterceptor(GLProfiler gLProfiler) {
        this.glProfiler = gLProfiler;
    }

    public static String resolveErrorNumber(int i) {
        switch (i) {
            case GL20.GL_INVALID_ENUM /* 1280 */:
                return "GL_INVALID_ENUM";
            case 1281:
                return "GL_INVALID_VALUE";
            case GL20.GL_INVALID_OPERATION /* 1282 */:
                return "GL_INVALID_OPERATION";
            case GL32.GL_STACK_OVERFLOW /* 1283 */:
            case GL32.GL_STACK_UNDERFLOW /* 1284 */:
            default:
                return "number " + i;
            case GL20.GL_OUT_OF_MEMORY /* 1285 */:
                return "GL_OUT_OF_MEMORY";
            case GL20.GL_INVALID_FRAMEBUFFER_OPERATION /* 1286 */:
                return "GL_INVALID_FRAMEBUFFER_OPERATION";
        }
    }

    public int getCalls() {
        return this.calls;
    }

    public int getTextureBindings() {
        return this.textureBindings;
    }

    public int getDrawCalls() {
        return this.drawCalls;
    }

    public int getShaderSwitches() {
        return this.shaderSwitches;
    }

    public FloatCounter getVertexCount() {
        return this.vertexCount;
    }

    public void reset() {
        this.calls = 0;
        this.textureBindings = 0;
        this.drawCalls = 0;
        this.shaderSwitches = 0;
        this.vertexCount.reset();
    }
}
