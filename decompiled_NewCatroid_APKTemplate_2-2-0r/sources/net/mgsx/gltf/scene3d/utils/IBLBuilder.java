package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.FrameBufferCubemap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;

/* JADX INFO: loaded from: classes12.dex */
public class IBLBuilder implements Disposable {
    private ShapeRenderer shapes;
    private final ShaderProgram sunShader;
    private ShapeRenderer sunShapes;
    public final Color nearGroundColor = new Color();
    public final Color farGroundColor = new Color();
    public final Color nearSkyColor = new Color();
    public final Color farSkyColor = new Color();
    public final Array<Light> lights = new Array<>();
    public boolean renderSun = true;
    public boolean renderGradient = true;

    public static IBLBuilder createOutdoor(DirectionalLight directionalLight) {
        IBLBuilder iBLBuilder = new IBLBuilder();
        iBLBuilder.nearGroundColor.set(0.5f, 0.45f, 0.4f, 1.0f);
        iBLBuilder.farGroundColor.set(0.3f, 0.25f, 0.2f, 1.0f);
        iBLBuilder.nearSkyColor.set(0.7f, 0.8f, 1.0f, 1.0f);
        iBLBuilder.farSkyColor.set(0.9f, 0.95f, 1.0f, 1.0f);
        Light light = new Light();
        light.direction.set(directionalLight.direction).nor();
        light.color.set(directionalLight.color);
        light.exponent = 30.0f;
        iBLBuilder.lights.add(light);
        return iBLBuilder;
    }

    public static IBLBuilder createIndoor(DirectionalLight directionalLight) {
        IBLBuilder iBLBuilder = new IBLBuilder();
        Color colorMul = new Color(1.0f, 0.9f, 0.8f, 1.0f).mul(0.3f);
        iBLBuilder.nearGroundColor.set(colorMul).mul(0.7f);
        iBLBuilder.farGroundColor.set(colorMul);
        iBLBuilder.farSkyColor.set(colorMul);
        iBLBuilder.nearSkyColor.set(colorMul).mul(2.0f);
        Light light = new Light();
        light.direction.set(directionalLight.direction).nor();
        light.color.set(1.0f, 0.5f, 0.0f, 1.0f).mul(0.3f);
        light.exponent = 3.0f;
        iBLBuilder.lights.add(light);
        return iBLBuilder;
    }

    public static IBLBuilder createCustom(DirectionalLight directionalLight) {
        IBLBuilder iBLBuilder = new IBLBuilder();
        Light light = new Light();
        light.direction.set(directionalLight.direction).nor();
        light.color.set(directionalLight.color);
        light.exponent = 100.0f;
        iBLBuilder.lights.add(light);
        return iBLBuilder;
    }

    private IBLBuilder() {
        ShapeRenderer shapeRenderer = new ShapeRenderer(20);
        this.shapes = shapeRenderer;
        shapeRenderer.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, 1.0f, 1.0f);
        ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.classpath("net/mgsx/gltf/shaders/ibl-sun.vs.glsl"), Gdx.files.classpath("net/mgsx/gltf/shaders/ibl-sun.fs.glsl"));
        this.sunShader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            throw new GdxRuntimeException(shaderProgram.getLog());
        }
        ShapeRenderer shapeRenderer2 = new ShapeRenderer(20, shaderProgram);
        this.sunShapes = shapeRenderer2;
        shapeRenderer2.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, 1.0f, 1.0f);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.sunShader.dispose();
        this.sunShapes.dispose();
        this.shapes.dispose();
    }

    public Cubemap buildEnvMap(int i) {
        FrameBufferCubemap frameBufferCubemap = new FrameBufferCubemap(Pixmap.Format.RGBA8888, i, i, false) { // from class: net.mgsx.gltf.scene3d.utils.IBLBuilder.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.graphics.glutils.FrameBufferCubemap, com.badlogic.gdx.graphics.glutils.GLFrameBuffer
            public void disposeColorTexture(Cubemap cubemap) {
            }
        };
        frameBufferCubemap.begin();
        while (frameBufferCubemap.nextSide()) {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            Gdx.gl.glClear(16384);
            Cubemap.CubemapSide side = frameBufferCubemap.getSide();
            renderGradient(side, 0.0f);
            renderLights(side, false);
        }
        frameBufferCubemap.end();
        Cubemap colorBufferTexture = frameBufferCubemap.getColorBufferTexture();
        frameBufferCubemap.dispose();
        return colorBufferTexture;
    }

    public Cubemap buildIrradianceMap(int i) {
        FrameBufferCubemap frameBufferCubemap = new FrameBufferCubemap(Pixmap.Format.RGBA8888, i, i, false) { // from class: net.mgsx.gltf.scene3d.utils.IBLBuilder.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.graphics.glutils.FrameBufferCubemap, com.badlogic.gdx.graphics.glutils.GLFrameBuffer
            public void disposeColorTexture(Cubemap cubemap) {
            }
        };
        frameBufferCubemap.begin();
        while (frameBufferCubemap.nextSide()) {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            Gdx.gl.glClear(16384);
            Cubemap.CubemapSide side = frameBufferCubemap.getSide();
            renderGradient(side, 0.5f);
            renderLights(side, true);
        }
        frameBufferCubemap.end();
        Cubemap colorBufferTexture = frameBufferCubemap.getColorBufferTexture();
        frameBufferCubemap.dispose();
        return colorBufferTexture;
    }

    public Cubemap buildRadianceMap(int i) {
        Pixmap[] pixmapArr = new Pixmap[i * 6];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = 1 << ((i - i3) - 1);
            FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, i4, i4, false);
            frameBuffer.begin();
            for (int i5 = 0; i5 < 6; i5++) {
                Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                Gdx.gl.glClear(16384);
                Cubemap.CubemapSide cubemapSide = Cubemap.CubemapSide.values()[i5];
                renderGradient(cubemapSide, i3 / i);
                renderLights(cubemapSide, false);
                pixmapArr[i2] = ScreenUtils.getFrameBufferPixmap(0, 0, i4, i4);
                i2++;
            }
            frameBuffer.end();
            frameBuffer.dispose();
        }
        Cubemap cubemap = new Cubemap(new FacedMultiCubemapData(pixmapArr, i));
        cubemap.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Linear);
        return cubemap;
    }

    private void renderGradient(Cubemap.CubemapSide cubemapSide, float f) {
        if (this.renderGradient) {
            Color colorLerp = this.farSkyColor.cpy().lerp(this.nearSkyColor, 0.5f).cpy().lerp(this.farGroundColor.cpy().lerp(this.nearGroundColor, 0.5f), 0.5f);
            Color colorLerp2 = this.farGroundColor.cpy().lerp(this.farSkyColor, 0.5f);
            double d = 1.0f - f;
            float fPow = 1.0f - ((float) Math.pow(d, 4.0d));
            float fPow2 = 1.0f - ((float) Math.pow(d, 1.0d));
            Color colorLerp3 = this.nearGroundColor.cpy().lerp(colorLerp, fPow2);
            Color colorLerp4 = this.nearSkyColor.cpy().lerp(colorLerp, fPow2);
            Color colorLerp5 = this.farGroundColor.cpy().lerp(colorLerp2, fPow).lerp(colorLerp, fPow2);
            Color colorLerp6 = this.farSkyColor.cpy().lerp(colorLerp2, fPow).lerp(colorLerp, fPow2);
            this.shapes.begin(ShapeRenderer.ShapeType.Filled);
            if (cubemapSide == Cubemap.CubemapSide.PositiveY) {
                this.shapes.rect(0.0f, 0.0f, 1.0f, 1.0f, colorLerp4, colorLerp4, colorLerp4, colorLerp4);
            } else if (cubemapSide == Cubemap.CubemapSide.NegativeY) {
                this.shapes.rect(0.0f, 0.0f, 1.0f, 1.0f, colorLerp3, colorLerp3, colorLerp3, colorLerp3);
            } else {
                this.shapes.rect(0.0f, 0.0f, 1.0f, 0.5f, colorLerp4, colorLerp4, colorLerp6, colorLerp6);
                this.shapes.rect(0.0f, 0.5f, 1.0f, 0.5f, colorLerp5, colorLerp5, colorLerp3, colorLerp3);
            }
            this.shapes.end();
        }
    }

    public static class Light {
        public final Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public final Vector3 direction = new Vector3(0.0f, -1.0f, 0.0f);
        public float exponent = 30.0f;
        private static final Vector3 localSunDir = new Vector3();
        private static final Vector3 localDir = new Vector3();
        private static final Vector3 localUp = new Vector3();
        private static final Matrix4 matrix = new Matrix4();

        /* JADX INFO: Access modifiers changed from: private */
        public void render(Cubemap.CubemapSide cubemapSide, ShapeRenderer shapeRenderer, ShaderProgram shaderProgram, float f) {
            render(cubemapSide, shapeRenderer, shaderProgram, f, this.exponent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void render(Cubemap.CubemapSide cubemapSide, ShapeRenderer shapeRenderer, ShaderProgram shaderProgram, float f, float f2) {
            shaderProgram.bind();
            shaderProgram.setUniformf("u_exponent", f2);
            shaderProgram.setUniformf("u_ambient", this.color.r, this.color.g, this.color.b, 0.0f);
            shaderProgram.setUniformf("u_diffuse", this.color.r, this.color.g, this.color.b, f);
            Vector3 vector3 = localDir;
            vector3.set(cubemapSide.direction);
            Vector3 vector4 = localUp;
            vector4.set(cubemapSide.up);
            if (cubemapSide == Cubemap.CubemapSide.NegativeX || cubemapSide == Cubemap.CubemapSide.PositiveX) {
                vector3.x = -vector3.x;
            }
            Matrix4 matrix4 = matrix;
            matrix4.setToLookAt(vector3, vector4).tra();
            Vector3 vector5 = localSunDir;
            vector5.set(this.direction).scl(-1.0f, -1.0f, 1.0f).mul(matrix4);
            shaderProgram.setUniformf("u_direction", vector5);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0.0f, 0.0f, 1.0f, 1.0f);
            shapeRenderer.end();
        }
    }

    private void renderLights(Cubemap.CubemapSide cubemapSide, boolean z) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
        Array.ArrayIterator<Light> it = this.lights.iterator();
        while (it.hasNext()) {
            Light next = it.next();
            if (z) {
                next.render(cubemapSide, this.sunShapes, this.sunShader, 0.5f, 1.0f);
            } else {
                next.render(cubemapSide, this.sunShapes, this.sunShader, 1.0f);
            }
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
