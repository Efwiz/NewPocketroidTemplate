package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/CustomScreenShaderEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CustomScreenShaderEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private float time;

    public CustomScreenShaderEffect(String vertexCode, String fragmentCode) {
        super(new ShaderProgram(vertexCode, fragmentCode));
        this.time = 0.0f;
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("CustomScreenShader", "Shader compile error:\n" + this.program.getLog());
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void update(float delta) {
        super.update(delta);
        this.time += delta;
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        VfxFrameBuffer srcBuffer = buffers.getSrcBuffer();
        VfxFrameBuffer dstBuffer = buffers.getDstBuffer();
        this.program.begin();
        srcBuffer.getTexture().bind(0);
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformf("u_time", this.time);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
