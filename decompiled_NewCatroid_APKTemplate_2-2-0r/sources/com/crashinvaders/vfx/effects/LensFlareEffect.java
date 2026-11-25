package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class LensFlareEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_COLOR = "u_color";
    private static final String U_INTENSITY = "u_intensity";
    private static final String U_LIGHT_POSITION = "u_lightPosition";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_VIEWPORT = "u_viewport";
    private final Vector3 color;
    private float intensity;
    private final Vector2 lightPosition;
    private final Vector2 viewport;

    public LensFlareEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/lens-flare.frag")));
        this.lightPosition = new Vector2(0.5f, 0.5f);
        this.viewport = new Vector2();
        this.color = new Vector3(1.0f, 0.8f, 0.2f);
        this.intensity = 5.0f;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        super.resize(i, i2);
        this.viewport.set(i, i2);
        setUniform(U_VIEWPORT, this.viewport);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        this.program.setUniformf(U_LIGHT_POSITION, this.lightPosition);
        this.program.setUniformf(U_INTENSITY, this.intensity);
        this.program.setUniformf(U_COLOR, this.color);
        this.program.setUniformf(U_VIEWPORT, this.viewport);
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

    public Vector2 getLightPosition() {
        return this.lightPosition;
    }

    public void setLightPosition(Vector2 vector2) {
        setLightPosition(vector2.x, vector2.y);
    }

    public void setLightPosition(float f, float f2) {
        this.lightPosition.set(f, f2);
        setUniform(U_LIGHT_POSITION, this.lightPosition);
    }

    public float getIntensity() {
        return this.intensity;
    }

    public void setIntensity(float f) {
        this.intensity = f;
        setUniform(U_INTENSITY, f);
    }

    public Vector3 getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        setColor(color.r, color.g, color.b);
    }

    public void setColor(float f, float f2, float f3) {
        this.color.set(f, f2, f3);
        setUniform(U_COLOR, this.color);
    }
}
