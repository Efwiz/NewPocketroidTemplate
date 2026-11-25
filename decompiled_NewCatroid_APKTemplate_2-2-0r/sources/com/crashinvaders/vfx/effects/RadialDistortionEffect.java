package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class RadialDistortionEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_DISTORTION = "distortion";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_ZOOM = "zoom";
    private float distortion;
    private float zoom;

    public RadialDistortionEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/radial-distortion.frag")));
        this.zoom = 1.0f;
        this.distortion = 0.3f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformf(U_DISTORTION, this.distortion);
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

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float f) {
        this.zoom = f;
        setUniform(U_ZOOM, f);
    }

    public float getDistortion() {
        return this.distortion;
    }

    public void setDistortion(float f) {
        this.distortion = f;
        setUniform(U_DISTORTION, f);
    }
}
