package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class WaterDistortionEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_AMOUNT = "u_amount";
    private static final String U_SPEED = "u_speed";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final String U_TIME = "u_time";
    private float amount;
    private float speed;
    private float time;

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
    }

    public WaterDistortionEffect(float f, float f2) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/water-distortion.frag")));
        this.time = 0.0f;
        this.amount = f;
        this.speed = f2;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
        super.update(f);
        setTime(this.time + f);
    }

    public float getTime() {
        return this.time;
    }

    public void setTime(float f) {
        this.time = f;
        setUniform(U_TIME, f);
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float f) {
        this.amount = f;
        setUniform(U_AMOUNT, f);
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float f) {
        this.speed = f;
        setUniform(U_SPEED, f);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        setUniform(U_TIME, this.time);
        setUniform(U_AMOUNT, this.amount);
        setUniform(U_SPEED, this.speed);
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
}
