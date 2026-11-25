package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/VolumetricFogEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VolumetricFogEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/volumetric_fog.frag").readString();
    private static final String VSH = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}";
    private Camera camera;
    private float density;
    private Texture depthTexture;
    private final Color lightColor;
    private final Vector3 lightDir;
    private final Matrix4 lightProjView;
    private float maxDistance;
    private float scattering;
    private Texture shadowTexture;
    private int steps;

    public VolumetricFogEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.lightProjView = new Matrix4();
        this.lightDir = new Vector3(1.0f, -1.0f, 1.0f).nor();
        this.lightColor = new Color(Color.WHITE);
        this.density = 0.5f;
        this.scattering = 0.6f;
        this.steps = 30;
        this.maxDistance = 150.0f;
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("VolumetricFog", "Shader error:\n" + this.program.getLog());
    }

    public void setParams(float density, float scattering, int steps, float maxDistance) {
        this.density = density;
        this.scattering = scattering;
        this.steps = steps;
        this.maxDistance = maxDistance;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDepthTexture(Texture depthTexture) {
        this.depthTexture = depthTexture;
    }

    public void setShadowMap(Texture shadowTexture, Matrix4 lightCombined) {
        this.shadowTexture = shadowTexture;
        this.lightProjView.set(lightCombined);
    }

    public void setLightParams(Vector3 dir, Color color) {
        this.lightDir.set(dir).nor();
        this.lightColor.set(color);
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_shadowTexture", 2);
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        VfxFrameBuffer srcBuffer = buffers.getSrcBuffer();
        VfxFrameBuffer dstBuffer = buffers.getDstBuffer();
        this.program.begin();
        Texture texture = this.depthTexture;
        if (texture != null) {
            texture.bind(1);
        }
        Texture texture2 = this.shadowTexture;
        if (texture2 != null) {
            texture2.bind(2);
        }
        srcBuffer.getTexture().bind(0);
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_shadowTexture", 2);
        if (this.camera != null) {
            this.program.setUniformMatrix("u_invProjViewMatrix", this.camera.invProjectionView);
            this.program.setUniformf("u_cameraPos", this.camera.position);
            this.program.setUniformf("u_farPlane", this.camera.far);
        }
        this.program.setUniformMatrix("u_lightProjView", this.lightProjView);
        this.program.setUniformf("u_lightDir", this.lightDir);
        this.program.setUniformf("u_lightColor", this.lightColor.r, this.lightColor.g, this.lightColor.b);
        this.program.setUniformf("u_density", this.density);
        this.program.setUniformf("u_scattering", this.scattering);
        this.program.setUniformf("u_maxDistance", this.maxDistance);
        this.program.setUniformi("u_steps", this.steps);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
