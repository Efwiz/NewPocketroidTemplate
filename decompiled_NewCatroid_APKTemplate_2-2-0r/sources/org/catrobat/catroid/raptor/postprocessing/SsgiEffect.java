package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/SsgiEffect.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SsgiEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String FSH = Gdx.files.internal("shaders/ssgi.frag").readString();
    private static final String VSH = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}";
    private final Color baseAlbedo;
    private float bias;
    private Camera camera;
    private Texture depthTexture;
    private float flipDepth;
    private float intensity;
    private final float[] kernel;
    private Texture noiseTexture;
    private float radius;
    private float ssaoStrength;
    public Matrix4 tmpMat;

    public SsgiEffect() {
        super(new ShaderProgram(VSH, FSH));
        this.kernel = new float[36];
        this.radius = 1.0f;
        this.intensity = 1.0f;
        this.bias = 0.05f;
        this.ssaoStrength = 1.0f;
        this.baseAlbedo = new Color(0.1f, 0.1f, 0.1f, 1.0f);
        this.flipDepth = 1.0f;
        this.tmpMat = new Matrix4();
        if (!this.program.isCompiled()) {
            Gdx.f82app.error("SsgiEffect", "Shader compilation error:\n" + this.program.getLog());
        }
        createNoiseTexture();
        generateKernel();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDepthTexture(Texture depthTexture) {
        this.depthTexture = depthTexture;
    }

    public void setParams(float radius, float intensity, float bias, float ssaoStrength, Color baseAlbedo, boolean flipDepth) {
        this.radius = radius;
        this.intensity = intensity;
        this.bias = bias;
        this.ssaoStrength = ssaoStrength;
        this.baseAlbedo.set(baseAlbedo);
        this.flipDepth = flipDepth ? 1.0f : 0.0f;
    }

    public void setParams(float radius, float intensity, float bias) {
        this.radius = radius;
        this.intensity = intensity;
        this.bias = bias;
    }

    private void generateKernel() {
        for (int i = 0; i < 12; i++) {
            Vector3 vector3Nor = new Vector3((MathUtils.random() * 2.0f) - 1.0f, (MathUtils.random() * 2.0f) - 1.0f, MathUtils.random()).nor();
            float f = i / 12.0f;
            vector3Nor.scl(MathUtils.lerp(0.1f, 1.0f, f * f));
            int i2 = i * 3;
            this.kernel[i2] = vector3Nor.x;
            this.kernel[i2 + 1] = vector3Nor.y;
            this.kernel[i2 + 2] = vector3Nor.z;
        }
    }

    private void createNoiseTexture() {
        Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGB888);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                pixmap.setColor(MathUtils.random(), MathUtils.random(), 0.0f, 1.0f);
                pixmap.drawPixel(i, i2);
            }
        }
        Texture texture = new Texture(pixmap);
        this.noiseTexture = texture;
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.noiseTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        pixmap.dispose();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_noiseTexture", 2);
        this.program.end();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        VfxFrameBuffer srcBuffer = buffers.getSrcBuffer();
        VfxFrameBuffer dstBuffer = buffers.getDstBuffer();
        srcBuffer.getTexture().bind(0);
        Texture texture = this.depthTexture;
        if (texture != null) {
            texture.bind(1);
        }
        Texture texture2 = this.noiseTexture;
        if (texture2 != null) {
            texture2.bind(2);
        }
        this.program.begin();
        this.program.setUniformi("u_texture0", 0);
        this.program.setUniformi("u_depthTexture", 1);
        this.program.setUniformi("u_noiseTexture", 2);
        if (this.camera != null) {
            this.program.setUniformMatrix("u_projectionMatrix", this.camera.projection);
            this.program.setUniformMatrix("u_invProjectionMatrix", this.tmpMat.set(this.camera.projection).inv());
            this.program.setUniformMatrix("u_viewMatrix", this.camera.view);
            this.program.setUniformf("u_farPlane", this.camera.far);
        }
        if (srcBuffer.getFbo() != null) {
            this.program.setUniformf("u_noiseScale", srcBuffer.getFbo().getWidth() / 4.0f, srcBuffer.getFbo().getHeight() / 4.0f);
        } else {
            this.program.setUniformf("u_noiseScale", Gdx.graphics.getWidth() / 4.0f, Gdx.graphics.getHeight() / 4.0f);
        }
        this.program.setUniform3fv("u_kernel", this.kernel, 0, 36);
        this.program.setUniformf("u_radius", this.radius);
        this.program.setUniformf("u_intensity", this.intensity);
        this.program.setUniformf("u_bias", this.bias);
        this.program.setUniformf("u_ssaoStrength", this.ssaoStrength);
        this.program.setUniformf("u_baseAlbedo", this.baseAlbedo.r, this.baseAlbedo.g, this.baseAlbedo.b);
        this.program.setUniformf("u_flipDepth", this.flipDepth);
        this.program.end();
        renderShader(context, dstBuffer);
    }
}
