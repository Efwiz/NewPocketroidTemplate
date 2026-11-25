package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/ExposureEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExposureEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private float exposure;

    public ExposureEffect() {
        super(VfxGLUtils.compileShader(Gdx.files.internal("shaders/screenspace.vert"), Gdx.files.internal("shaders/exposure.frag")));
        this.exposure = 1.0f;
    }

    public void setExposure(float exposure) {
        this.exposure = exposure;
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        buffers.getSrcTexture().bind(0);
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformf("u_exposure", this.exposure);
        this.program.end();
        renderShader(context, buffers.getDstBuffer());
    }
}
