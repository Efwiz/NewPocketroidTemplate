package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/SsrRayTracingEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SsrRayTracingEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/raytracing_ssr.frag").readString();
    private static final String VSH = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}";
    private Camera camera;
    private Texture depthTexture;
    private float edgeFade;
    private Texture materialTexture;
    private float maxDistance;
    private float reflectivity;
    private int steps;
    private float stride;
    private float thickness;
    public Matrix4 tmpMat;

    public SsrRayTracingEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.steps = 20;
        this.reflectivity = 1.0f;
        this.edgeFade = 0.15f;
        this.thickness = 0.5f;
        this.maxDistance = 50.0f;
        this.stride = 0.5f;
        this.tmpMat = new Matrix4();
        if (this.program.isCompiled()) {
            return;
        }
        Gdx.f82app.error("SsrRayTracingEffect", "Shader error:\n" + this.program.getLog());
    }

    public void setParams(int steps, float reflectivity, float thickness, float maxDistance, float stride, float edgeFade) {
        this.steps = steps;
        this.reflectivity = reflectivity;
        this.thickness = thickness;
        this.maxDistance = maxDistance;
        this.stride = stride;
        this.edgeFade = edgeFade;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDepthTexture(Texture depthTexture) {
        this.depthTexture = depthTexture;
    }

    public void setMaterialTexture(Texture materialTexture) {
        this.materialTexture = materialTexture;
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_materialTexture", 2);
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
        Texture texture2 = this.materialTexture;
        if (texture2 != null) {
            texture2.bind(2);
        }
        srcBuffer.getTexture().bind(0);
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_materialTexture", 2);
        if (this.camera != null) {
            this.program.setUniformMatrix("u_projectionMatrix", this.camera.projection);
            this.program.setUniformMatrix("u_invProjectionMatrix", this.tmpMat.set(this.camera.projection).inv());
            this.program.setUniformMatrix("u_viewMatrix", this.camera.view);
            this.program.setUniformf("u_farPlane", this.camera.far);
        }
        this.program.setUniformf("u_reflectivityMulti", this.reflectivity);
        this.program.setUniformf("u_edgeFade", this.edgeFade);
        this.program.setUniformf("u_thickness", this.thickness);
        this.program.setUniformf("u_maxDistance", this.maxDistance);
        this.program.setUniformf("u_stride", this.stride);
        this.program.setUniformi("u_maxSteps", this.steps);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
