package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class RadialBlurEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static String U_BLUR_DIV = "u_blurDiv";
    private static String U_OFFSET_X = "u_offsetX";
    private static String U_OFFSET_Y = "u_offsetY";
    private static String U_TEXTURE = "u_texture0";
    private static String U_ZOOM = "u_zoom";
    private float originX;
    private float originY;
    private final int passes;
    private float strength;
    private float zoom;

    public RadialBlurEffect(int i) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/radial-blur.vert"), Gdx.files.classpath("gdxvfx/shaders/radial-blur.frag"), "#define PASSES " + i));
        this.strength = 0.2f;
        this.originX = 0.5f;
        this.originY = 0.5f;
        this.zoom = 1.0f;
        this.passes = i;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE, 0);
        this.program.setUniformf(U_BLUR_DIV, this.strength / this.passes);
        this.program.setUniformf(U_OFFSET_X, this.originX);
        this.program.setUniformf(U_OFFSET_Y, this.originY);
        this.program.setUniformf(U_ZOOM, this.zoom);
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxPingPongWrapper.getDstBuffer());
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        vfxFrameBuffer.getTexture().bind(0);
        renderShader(vfxRenderContext, vfxFrameBuffer2);
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public void setOrigin(int i) {
        float f;
        float f2 = 1.0f;
        if ((i & 8) != 0) {
            f = 0.0f;
        } else {
            f = (i & 16) != 0 ? 1.0f : 0.5f;
        }
        if ((i & 4) != 0) {
            f2 = 0.0f;
        } else if ((i & 2) == 0) {
            f2 = 0.5f;
        }
        setOrigin(f, f2);
    }

    public void setOrigin(float f, float f2) {
        this.originX = f;
        this.originY = f2;
        this.program.begin();
        this.program.setUniformf(U_OFFSET_X, this.originX);
        this.program.setUniformf(U_OFFSET_Y, this.originY);
        this.program.end();
    }

    public float getStrength() {
        return this.strength;
    }

    public void setStrength(float f) {
        this.strength = f;
        setUniform(U_BLUR_DIV, f / this.passes);
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float f) {
        this.zoom = f;
        setUniform(U_ZOOM, f);
    }
}
