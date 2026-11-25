package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import org.catrobat.catroid.raptor.PostProcessingData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/EyeAdaptationManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EyeAdaptationManager implements Disposable {
    private float currentExposure = 1.0f;
    private final FrameBuffer[] downsampleFBOs;
    private final ShaderProgram downsampleShader;
    private final ShaderProgram luminanceShader;
    private final Mesh quad;

    public EyeAdaptationManager() {
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec4 a_position; attribute vec2 a_texCoord0; varying vec2 v_texCoords; void main() { gl_Position = a_position; v_texCoords = a_texCoord0; }", "varying vec2 v_texCoords; uniform sampler2D u_texture0; void main() { vec3 c = texture2D(u_texture0, v_texCoords).rgb; float lum = dot(c, vec3(0.2126, 0.7152, 0.0722)); gl_FragColor = vec4(lum, lum, lum, 1.0); }");
        this.luminanceShader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            Gdx.f82app.error("EyeAdaptation", "Luminance shader failed: " + shaderProgram.getLog());
        }
        ShaderProgram shaderProgram2 = new ShaderProgram("attribute vec4 a_position; attribute vec2 a_texCoord0; varying vec2 v_texCoords; void main() { gl_Position = a_position; v_texCoords = a_texCoord0; }", "varying vec2 v_texCoords; uniform sampler2D u_texture0; uniform vec2 u_pixelSize; void main() { vec3 a = texture2D(u_texture0, v_texCoords + u_pixelSize * vec2(-0.5, 0.5)).rgb; vec3 b = texture2D(u_texture0, v_texCoords + u_pixelSize * vec2(0.5, 0.5)).rgb; vec3 c = texture2D(u_texture0, v_texCoords + u_pixelSize * vec2(-0.5, -0.5)).rgb; vec3 d = texture2D(u_texture0, v_texCoords + u_pixelSize * vec2(0.5, -0.5)).rgb; gl_FragColor = vec4((a+b+c+d)*0.25, 1.0); }");
        this.downsampleShader = shaderProgram2;
        if (!shaderProgram2.isCompiled()) {
            Gdx.f82app.error("EyeAdaptation", "Downsample shader failed: " + shaderProgram2.getLog());
        }
        this.downsampleFBOs = new FrameBuffer[9];
        int i = 0;
        while (true) {
            FrameBuffer[] frameBufferArr = this.downsampleFBOs;
            if (i < frameBufferArr.length) {
                int length = 1 << ((frameBufferArr.length - 1) - i);
                frameBufferArr[i] = new FrameBuffer(Pixmap.Format.RGBA8888, length, length, false);
                i++;
            } else {
                Mesh mesh = new Mesh(true, 4, 0, new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
                this.quad = mesh;
                mesh.setVertices(new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f});
                return;
            }
        }
    }

    public void update(float delta, Texture hdrSourceTexture, PostProcessingData.EyeAdaptation settings) {
        this.currentExposure = MathUtils.lerp(this.currentExposure, MathUtils.clamp(settings.targetLuminance / (calculateAvgLuminance(hdrSourceTexture) + 0.001f), settings.minExposure, settings.maxExposure), delta * settings.speed);
    }

    private float calculateAvgLuminance(Texture source) {
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        FrameBuffer frameBuffer = this.downsampleFBOs[0];
        frameBuffer.begin();
        this.luminanceShader.bind();
        source.bind(0);
        this.luminanceShader.setUniformi("u_texture0", 0);
        Gdx.gl.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
        this.quad.render(this.luminanceShader, 6, 0, 4);
        frameBuffer.end();
        int i = 1;
        while (true) {
            FrameBuffer[] frameBufferArr = this.downsampleFBOs;
            if (i < frameBufferArr.length) {
                FrameBuffer frameBuffer2 = frameBufferArr[i];
                FrameBuffer frameBuffer3 = frameBufferArr[i - 1];
                frameBuffer2.begin();
                this.downsampleShader.bind();
                frameBuffer3.getColorBufferTexture().bind(0);
                this.downsampleShader.setUniformi("u_texture0", 0);
                this.downsampleShader.setUniformf("u_pixelSize", 1.0f / frameBuffer3.getWidth(), 1.0f / frameBuffer3.getHeight());
                Gdx.gl.glViewport(0, 0, frameBuffer2.getWidth(), frameBuffer2.getHeight());
                this.quad.render(this.downsampleShader, 6, 0, 4);
                frameBuffer2.end();
                i++;
            } else {
                FrameBuffer frameBuffer4 = frameBufferArr[frameBufferArr.length - 1];
                frameBuffer4.begin();
                byte[] frameBufferPixels = ScreenUtils.getFrameBufferPixels(0, 0, 1, 1, true);
                frameBuffer4.end();
                Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                return (frameBufferPixels[0] & 255) / 255.0f;
            }
        }
    }

    public float getCurrentExposure() {
        return this.currentExposure;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.luminanceShader.dispose();
        this.downsampleShader.dispose();
        for (FrameBuffer frameBuffer : this.downsampleFBOs) {
            frameBuffer.dispose();
        }
        this.quad.dispose();
    }
}
