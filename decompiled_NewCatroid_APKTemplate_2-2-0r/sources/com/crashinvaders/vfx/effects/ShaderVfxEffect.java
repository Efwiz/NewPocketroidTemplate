package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;

/* JADX INFO: loaded from: classes8.dex */
public abstract class ShaderVfxEffect extends AbstractVfxEffect {
    public static final int TEXTURE_HANDLE0 = 0;
    public static final int TEXTURE_HANDLE1 = 1;
    public static final int TEXTURE_HANDLE2 = 2;
    public static final int TEXTURE_HANDLE3 = 3;
    public static final int TEXTURE_HANDLE4 = 4;
    public static final int TEXTURE_HANDLE5 = 5;
    public static final int TEXTURE_HANDLE6 = 6;
    public static final int TEXTURE_HANDLE7 = 7;
    protected final ShaderProgram program;

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
    }

    public ShaderVfxEffect(ShaderProgram shaderProgram) {
        this.program = shaderProgram;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.program.dispose();
    }

    public ShaderProgram getProgram() {
        return this.program;
    }

    protected void renderShader(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer) {
        boolean zIsDrawing = vfxFrameBuffer.isDrawing();
        if (!zIsDrawing) {
            vfxFrameBuffer.begin();
        }
        this.program.begin();
        vfxRenderContext.getViewportMesh().render(this.program);
        this.program.end();
        if (zIsDrawing) {
            return;
        }
        vfxFrameBuffer.end();
    }

    protected void setUniform(String str, float f) {
        this.program.begin();
        this.program.setUniformf(str, f);
        this.program.end();
    }

    protected void setUniform(String str, int i) {
        this.program.begin();
        this.program.setUniformi(str, i);
        this.program.end();
    }

    protected void setUniform(String str, Vector2 vector2) {
        this.program.begin();
        this.program.setUniformf(str, vector2);
        this.program.end();
    }

    protected void setUniform(String str, Vector3 vector3) {
        this.program.begin();
        this.program.setUniformf(str, vector3);
        this.program.end();
    }

    protected void setUniform(String str, Color color) {
        this.program.begin();
        this.program.setUniformf(str, color);
        this.program.end();
    }

    protected void setUniform(String str, Matrix3 matrix3) {
        this.program.begin();
        this.program.setUniformMatrix(str, matrix3);
        this.program.end();
    }

    protected void setUniform(String str, Matrix4 matrix4) {
        this.program.begin();
        this.program.setUniformMatrix(str, matrix4);
        this.program.end();
    }

    protected void setUniform(String str, int i, float[] fArr, int i2, int i3) {
        this.program.begin();
        if (i == 1) {
            this.program.setUniform1fv(str, fArr, i2, i3);
        } else if (i == 2) {
            this.program.setUniform2fv(str, fArr, i2, i3);
        } else if (i == 3) {
            this.program.setUniform3fv(str, fArr, i2, i3);
        } else if (i == 4) {
            this.program.setUniform4fv(str, fArr, i2, i3);
        } else {
            throw new IllegalArgumentException("elementSize has illegal value: " + i + ". Possible values are 1..4");
        }
        this.program.end();
    }
}
