package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import java.nio.ShortBuffer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/SpriteBatch.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SpriteBatch implements Batch {

    @Deprecated
    public static Mesh.VertexDataType defaultVertexDataType = Mesh.VertexDataType.VertexBufferObject;

    @Deprecated
    public static Mesh.VertexDataType overrideVertexType = null;
    private int blendDstFunc;
    private int blendDstFuncAlpha;
    private int blendSrcFunc;
    private int blendSrcFuncAlpha;
    private boolean blendingDisabled;
    private final Color color;
    float colorPacked;
    private final Matrix4 combinedMatrix;
    private Mesh.VertexDataType currentDataType;
    private ShaderProgram customShader;
    boolean drawing;
    int idx;
    float invTexHeight;
    float invTexWidth;
    Texture lastTexture;
    public int maxSpritesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    final float[] vertices;

    public SpriteBatch() {
        this(1000, null);
    }

    public SpriteBatch(int i) {
        this(i, null);
    }

    public SpriteBatch(int i, ShaderProgram shaderProgram) {
        this.idx = 0;
        this.lastTexture = null;
        this.invTexWidth = 0.0f;
        this.invTexHeight = 0.0f;
        this.drawing = false;
        this.transformMatrix = new Matrix4();
        Matrix4 matrix4 = new Matrix4();
        this.projectionMatrix = matrix4;
        this.combinedMatrix = new Matrix4();
        this.blendingDisabled = false;
        this.blendSrcFunc = GL20.GL_SRC_ALPHA;
        this.blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
        this.blendSrcFuncAlpha = GL20.GL_SRC_ALPHA;
        this.blendDstFuncAlpha = GL20.GL_ONE_MINUS_SRC_ALPHA;
        this.customShader = null;
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.colorPacked = Color.WHITE_FLOAT_BITS;
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.maxSpritesInBatch = 0;
        if (i > 8191) {
            throw new IllegalArgumentException("Can't have more than 8191 sprites per batch: " + i);
        }
        Mesh.VertexDataType vertexDataType = Gdx.gl30 != null ? Mesh.VertexDataType.VertexBufferObjectWithVAO : defaultVertexDataType;
        Mesh.VertexDataType vertexDataType2 = overrideVertexType;
        vertexDataType = vertexDataType2 != null ? vertexDataType2 : vertexDataType;
        this.currentDataType = vertexDataType;
        int i2 = i * 6;
        this.mesh = new Mesh(this.currentDataType, false, i * 4, i2, new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
        matrix4.setToOrtho2D(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.vertices = new float[i * 20];
        short[] sArr = new short[i2];
        int i3 = 0;
        short s = 0;
        while (i3 < i2) {
            sArr[i3] = s;
            sArr[i3 + 1] = (short) (s + 1);
            short s2 = (short) (s + 2);
            sArr[i3 + 2] = s2;
            sArr[i3 + 3] = s2;
            sArr[i3 + 4] = (short) (s + 3);
            sArr[i3 + 5] = s;
            i3 += 6;
            s = (short) (s + 4);
        }
        this.mesh.setIndices(sArr);
        if (shaderProgram == null) {
            this.shader = createDefaultShader();
            this.ownsShader = true;
        } else {
            this.shader = shaderProgram;
        }
        if (vertexDataType != Mesh.VertexDataType.VertexArray) {
            this.mesh.getIndexData().bind();
            this.mesh.getIndexData().unbind();
        }
    }

    public static ShaderProgram createDefaultShader() {
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_color.a = v_color.a * (255.0/254.0);\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if (shaderProgram.isCompiled()) {
            return shaderProgram;
        }
        throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void begin() {
        if (this.drawing) {
            throw new IllegalStateException("SpriteBatch.end must be called before begin.");
        }
        this.renderCalls = 0;
        Gdx.gl.glDepthMask(false);
        ShaderProgram shaderProgram = this.customShader;
        if (shaderProgram != null) {
            shaderProgram.bind();
        } else {
            this.shader.bind();
        }
        setupMatrices();
        this.drawing = true;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void end() {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before end.");
        }
        if (this.idx > 0) {
            flush();
        }
        this.lastTexture = null;
        this.drawing = false;
        GL20 gl20 = Gdx.gl;
        gl20.glDepthMask(true);
        if (isBlendingEnabled()) {
            gl20.glDisable(GL20.GL_BLEND);
        }
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setColor(Color color) {
        this.color.set(color);
        this.colorPacked = color.toFloatBits();
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setColor(float f, float f2, float f3, float f4) {
        this.color.set(f, f2, f3, f4);
        this.colorPacked = this.color.toFloatBits();
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public Color getColor() {
        return this.color;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setPackedColor(float f) {
        Color.abgr8888ToColor(this.color, f);
        this.colorPacked = f;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public float getPackedColor() {
        return this.colorPacked;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int i, int i2, int i3, int i4, boolean z, boolean z2) {
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f17 = f + f3;
        float f18 = f2 + f4;
        float f19 = -f3;
        float f20 = -f4;
        float f21 = f5 - f3;
        float f22 = f6 - f4;
        if (f7 != 1.0f || f8 != 1.0f) {
            f19 *= f7;
            f20 *= f8;
            f21 *= f7;
            f22 *= f8;
        }
        if (f9 != 0.0f) {
            float fCosDeg = MathUtils.cosDeg(f9);
            float fSinDeg = MathUtils.sinDeg(f9);
            float f23 = fCosDeg * f19;
            f11 = f23 - (fSinDeg * f20);
            float f24 = f19 * fSinDeg;
            float f25 = (f20 * fCosDeg) + f24;
            float f26 = fSinDeg * f22;
            f10 = f23 - f26;
            float f27 = f22 * fCosDeg;
            f14 = f24 + f27;
            float f28 = (fCosDeg * f21) - f26;
            float f29 = f27 + (fSinDeg * f21);
            f13 = f29 - (f14 - f25);
            f16 = (f28 - f10) + f11;
            f21 = f28;
            f12 = f25;
            f15 = f29;
        } else {
            f10 = f19;
            f11 = f10;
            f12 = f20;
            f13 = f12;
            f14 = f22;
            f15 = f14;
            f16 = f21;
        }
        float f30 = f11 + f17;
        float f31 = f12 + f18;
        float f32 = f10 + f17;
        float f33 = f14 + f18;
        float f34 = f21 + f17;
        float f35 = f15 + f18;
        float f36 = f16 + f17;
        float f37 = f13 + f18;
        float f38 = this.invTexWidth;
        float f39 = i * f38;
        float f40 = this.invTexHeight;
        float f41 = (i2 + i4) * f40;
        float f42 = (i + i3) * f38;
        float f43 = i2 * f40;
        if (z) {
            f39 = f42;
            f42 = f39;
        }
        if (z2) {
            f41 = f43;
            f43 = f41;
        }
        float f44 = this.colorPacked;
        int i5 = this.idx;
        fArr[i5] = f30;
        fArr[i5 + 1] = f31;
        fArr[i5 + 2] = f44;
        fArr[i5 + 3] = f39;
        fArr[i5 + 4] = f41;
        fArr[i5 + 5] = f32;
        fArr[i5 + 6] = f33;
        fArr[i5 + 7] = f44;
        fArr[i5 + 8] = f39;
        fArr[i5 + 9] = f43;
        fArr[i5 + 10] = f34;
        fArr[i5 + 11] = f35;
        fArr[i5 + 12] = f44;
        fArr[i5 + 13] = f42;
        fArr[i5 + 14] = f43;
        fArr[i5 + 15] = f36;
        fArr[i5 + 16] = f37;
        fArr[i5 + 17] = f44;
        fArr[i5 + 18] = f42;
        fArr[i5 + 19] = f41;
        this.idx = i5 + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2, float f3, float f4, int i, int i2, int i3, int i4, boolean z, boolean z2) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f5 = this.invTexWidth;
        float f6 = i * f5;
        float f7 = this.invTexHeight;
        float f8 = (i4 + i2) * f7;
        float f9 = (i + i3) * f5;
        float f10 = i2 * f7;
        float f11 = f3 + f;
        float f12 = f4 + f2;
        if (z) {
            f9 = f6;
            f6 = f9;
        }
        if (z2) {
            f8 = f10;
            f10 = f8;
        }
        float f13 = this.colorPacked;
        int i5 = this.idx;
        fArr[i5] = f;
        fArr[i5 + 1] = f2;
        fArr[i5 + 2] = f13;
        fArr[i5 + 3] = f6;
        fArr[i5 + 4] = f8;
        fArr[i5 + 5] = f;
        fArr[i5 + 6] = f12;
        fArr[i5 + 7] = f13;
        fArr[i5 + 8] = f6;
        fArr[i5 + 9] = f10;
        fArr[i5 + 10] = f11;
        fArr[i5 + 11] = f12;
        fArr[i5 + 12] = f13;
        fArr[i5 + 13] = f9;
        fArr[i5 + 14] = f10;
        fArr[i5 + 15] = f11;
        fArr[i5 + 16] = f2;
        fArr[i5 + 17] = f13;
        fArr[i5 + 18] = f9;
        fArr[i5 + 19] = f8;
        this.idx = i5 + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2, int i, int i2, int i3, int i4) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f3 = this.invTexWidth;
        float f4 = i * f3;
        float f5 = this.invTexHeight;
        float f6 = (i2 + i4) * f5;
        float f7 = (i + i3) * f3;
        float f8 = i2 * f5;
        float f9 = i3 + f;
        float f10 = i4 + f2;
        float f11 = this.colorPacked;
        int i5 = this.idx;
        fArr[i5] = f;
        fArr[i5 + 1] = f2;
        fArr[i5 + 2] = f11;
        fArr[i5 + 3] = f4;
        fArr[i5 + 4] = f6;
        fArr[i5 + 5] = f;
        fArr[i5 + 6] = f10;
        fArr[i5 + 7] = f11;
        fArr[i5 + 8] = f4;
        fArr[i5 + 9] = f8;
        fArr[i5 + 10] = f9;
        fArr[i5 + 11] = f10;
        fArr[i5 + 12] = f11;
        fArr[i5 + 13] = f7;
        fArr[i5 + 14] = f8;
        fArr[i5 + 15] = f9;
        fArr[i5 + 16] = f2;
        fArr[i5 + 17] = f11;
        fArr[i5 + 18] = f7;
        fArr[i5 + 19] = f6;
        this.idx = i5 + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f9 = f3 + f;
        float f10 = f4 + f2;
        float f11 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f;
        fArr[i + 1] = f2;
        fArr[i + 2] = f11;
        fArr[i + 3] = f5;
        fArr[i + 4] = f6;
        fArr[i + 5] = f;
        fArr[i + 6] = f10;
        fArr[i + 7] = f11;
        fArr[i + 8] = f5;
        fArr[i + 9] = f8;
        fArr[i + 10] = f9;
        fArr[i + 11] = f10;
        fArr[i + 12] = f11;
        fArr[i + 13] = f7;
        fArr[i + 14] = f8;
        fArr[i + 15] = f9;
        fArr[i + 16] = f2;
        fArr[i + 17] = f11;
        fArr[i + 18] = f7;
        fArr[i + 19] = f6;
        this.idx = i + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2) {
        draw(texture, f, f2, texture.getWidth(), texture.getHeight());
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float f, float f2, float f3, float f4) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f5 = f3 + f;
        float f6 = f4 + f2;
        float f7 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f;
        fArr[i + 1] = f2;
        fArr[i + 2] = f7;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = 1.0f;
        fArr[i + 5] = f;
        fArr[i + 6] = f6;
        fArr[i + 7] = f7;
        fArr[i + 8] = 0.0f;
        fArr[i + 9] = 0.0f;
        fArr[i + 10] = f5;
        fArr[i + 11] = f6;
        fArr[i + 12] = f7;
        fArr[i + 13] = 1.0f;
        fArr[i + 14] = 0.0f;
        fArr[i + 15] = f5;
        fArr[i + 16] = f2;
        fArr[i + 17] = f7;
        fArr[i + 18] = 1.0f;
        fArr[i + 19] = 1.0f;
        this.idx = i + 20;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002c A[LOOP:0: B:12:0x0029->B:14:0x002c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:18:0x0040 A[SYNTHETIC] */
    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(Texture texture, float[] fArr, int i, int i2) {
        int i3;
        int iMin;
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        int length = this.vertices.length;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else {
            i3 = length - this.idx;
            if (i3 == 0) {
                flush();
            }
            iMin = Math.min(i3, i2);
            System.arraycopy(fArr, i, this.vertices, this.idx, iMin);
            this.idx += iMin;
            while (true) {
                i2 -= iMin;
                if (i2 > 0) {
                    return;
                }
                i += iMin;
                flush();
                iMin = Math.min(length, i2);
                System.arraycopy(fArr, i, this.vertices, 0, iMin);
                this.idx += iMin;
            }
        }
        i3 = length;
        iMin = Math.min(i3, i2);
        System.arraycopy(fArr, i, this.vertices, this.idx, iMin);
        this.idx += iMin;
        while (true) {
            i2 -= iMin;
            if (i2 > 0) {
                return;
            }
            i += iMin;
            flush();
            iMin = Math.min(length, i2);
            System.arraycopy(fArr, i, this.vertices, 0, iMin);
            this.idx += iMin;
        }
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(TextureRegion textureRegion, float f, float f2) {
        draw(textureRegion, f, f2, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(TextureRegion textureRegion, float f, float f2, float f3, float f4) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        Texture texture = textureRegion.texture;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f5 = f3 + f;
        float f6 = f4 + f2;
        float f7 = textureRegion.u;
        float f8 = textureRegion.v2;
        float f9 = textureRegion.u2;
        float f10 = textureRegion.v;
        float f11 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f;
        fArr[i + 1] = f2;
        fArr[i + 2] = f11;
        fArr[i + 3] = f7;
        fArr[i + 4] = f8;
        fArr[i + 5] = f;
        fArr[i + 6] = f6;
        fArr[i + 7] = f11;
        fArr[i + 8] = f7;
        fArr[i + 9] = f10;
        fArr[i + 10] = f5;
        fArr[i + 11] = f6;
        fArr[i + 12] = f11;
        fArr[i + 13] = f9;
        fArr[i + 14] = f10;
        fArr[i + 15] = f5;
        fArr[i + 16] = f2;
        fArr[i + 17] = f11;
        fArr[i + 18] = f9;
        fArr[i + 19] = f8;
        this.idx = i + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(TextureRegion textureRegion, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        Texture texture = textureRegion.texture;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f17 = f + f3;
        float f18 = f2 + f4;
        float f19 = -f3;
        float f20 = -f4;
        float f21 = f5 - f3;
        float f22 = f6 - f4;
        if (f7 != 1.0f || f8 != 1.0f) {
            f19 *= f7;
            f20 *= f8;
            f21 *= f7;
            f22 *= f8;
        }
        if (f9 != 0.0f) {
            float fCosDeg = MathUtils.cosDeg(f9);
            float fSinDeg = MathUtils.sinDeg(f9);
            float f23 = fCosDeg * f19;
            f11 = f23 - (fSinDeg * f20);
            float f24 = f19 * fSinDeg;
            float f25 = (f20 * fCosDeg) + f24;
            float f26 = fSinDeg * f22;
            f10 = f23 - f26;
            float f27 = f22 * fCosDeg;
            f14 = f24 + f27;
            float f28 = (fCosDeg * f21) - f26;
            float f29 = f27 + (fSinDeg * f21);
            f13 = f29 - (f14 - f25);
            f16 = (f28 - f10) + f11;
            f21 = f28;
            f12 = f25;
            f15 = f29;
        } else {
            f10 = f19;
            f11 = f10;
            f12 = f20;
            f13 = f12;
            f14 = f22;
            f15 = f14;
            f16 = f21;
        }
        float f30 = f11 + f17;
        float f31 = f12 + f18;
        float f32 = f10 + f17;
        float f33 = f14 + f18;
        float f34 = f21 + f17;
        float f35 = f15 + f18;
        float f36 = f16 + f17;
        float f37 = f13 + f18;
        float f38 = textureRegion.u;
        float f39 = textureRegion.v2;
        float f40 = textureRegion.u2;
        float f41 = textureRegion.v;
        float f42 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f30;
        fArr[i + 1] = f31;
        fArr[i + 2] = f42;
        fArr[i + 3] = f38;
        fArr[i + 4] = f39;
        fArr[i + 5] = f32;
        fArr[i + 6] = f33;
        fArr[i + 7] = f42;
        fArr[i + 8] = f38;
        fArr[i + 9] = f41;
        fArr[i + 10] = f34;
        fArr[i + 11] = f35;
        fArr[i + 12] = f42;
        fArr[i + 13] = f40;
        fArr[i + 14] = f41;
        fArr[i + 15] = f36;
        fArr[i + 16] = f37;
        fArr[i + 17] = f42;
        fArr[i + 18] = f40;
        fArr[i + 19] = f39;
        this.idx = i + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(TextureRegion textureRegion, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean z) {
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        float f19;
        float f20;
        float f21;
        float f22;
        float f23;
        float f24;
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        Texture texture = textureRegion.texture;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f25 = f + f3;
        float f26 = f2 + f4;
        float f27 = -f3;
        float f28 = -f4;
        float f29 = f5 - f3;
        float f30 = f6 - f4;
        if (f7 != 1.0f || f8 != 1.0f) {
            f27 *= f7;
            f28 *= f8;
            f29 *= f7;
            f30 *= f8;
        }
        if (f9 != 0.0f) {
            float fCosDeg = MathUtils.cosDeg(f9);
            float fSinDeg = MathUtils.sinDeg(f9);
            float f31 = fCosDeg * f27;
            f11 = f31 - (fSinDeg * f28);
            float f32 = f27 * fSinDeg;
            float f33 = (f28 * fCosDeg) + f32;
            float f34 = fSinDeg * f30;
            f10 = f31 - f34;
            float f35 = f30 * fCosDeg;
            f14 = f32 + f35;
            float f36 = (fCosDeg * f29) - f34;
            float f37 = f35 + (fSinDeg * f29);
            f13 = f37 - (f14 - f33);
            f16 = (f36 - f10) + f11;
            f29 = f36;
            f12 = f33;
            f15 = f37;
        } else {
            f10 = f27;
            f11 = f10;
            f12 = f28;
            f13 = f12;
            f14 = f30;
            f15 = f14;
            f16 = f29;
        }
        float f38 = f11 + f25;
        float f39 = f12 + f26;
        float f40 = f10 + f25;
        float f41 = f14 + f26;
        float f42 = f29 + f25;
        float f43 = f15 + f26;
        float f44 = f16 + f25;
        float f45 = f13 + f26;
        if (z) {
            float f46 = textureRegion.u2;
            f17 = textureRegion.v2;
            f18 = textureRegion.u;
            f19 = textureRegion.v2;
            f20 = textureRegion.u;
            f21 = f46;
            f22 = textureRegion.v;
            f23 = textureRegion.u2;
            f24 = textureRegion.v;
        } else {
            float f47 = textureRegion.u;
            f17 = textureRegion.v;
            f18 = textureRegion.u2;
            f19 = textureRegion.v;
            f20 = textureRegion.u2;
            f21 = f47;
            f22 = textureRegion.v2;
            f23 = textureRegion.u;
            f24 = textureRegion.v2;
        }
        float f48 = f24;
        float f49 = f19;
        float f50 = f20;
        float f51 = f18;
        float f52 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f38;
        fArr[i + 1] = f39;
        fArr[i + 2] = f52;
        fArr[i + 3] = f21;
        fArr[i + 4] = f17;
        fArr[i + 5] = f40;
        fArr[i + 6] = f41;
        fArr[i + 7] = f52;
        fArr[i + 8] = f51;
        fArr[i + 9] = f49;
        fArr[i + 10] = f42;
        fArr[i + 11] = f43;
        fArr[i + 12] = f52;
        fArr[i + 13] = f50;
        fArr[i + 14] = f22;
        fArr[i + 15] = f44;
        fArr[i + 16] = f45;
        fArr[i + 17] = f52;
        fArr[i + 18] = f23;
        fArr[i + 19] = f48;
        this.idx = i + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void draw(TextureRegion textureRegion, float f, float f2, Affine2 affine2) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }
        float[] fArr = this.vertices;
        Texture texture = textureRegion.texture;
        if (texture != this.lastTexture) {
            switchTexture(texture);
        } else if (this.idx == fArr.length) {
            flush();
        }
        float f3 = affine2.m02;
        float f4 = affine2.m12;
        float f5 = (affine2.m01 * f2) + affine2.m02;
        float f6 = (affine2.m11 * f2) + affine2.m12;
        float f7 = (affine2.m00 * f) + (affine2.m01 * f2) + affine2.m02;
        float f8 = (affine2.m10 * f) + (affine2.m11 * f2) + affine2.m12;
        float f9 = (affine2.m00 * f) + affine2.m02;
        float f10 = (affine2.m10 * f) + affine2.m12;
        float f11 = textureRegion.u;
        float f12 = textureRegion.v2;
        float f13 = textureRegion.u2;
        float f14 = textureRegion.v;
        float f15 = this.colorPacked;
        int i = this.idx;
        fArr[i] = f3;
        fArr[i + 1] = f4;
        fArr[i + 2] = f15;
        fArr[i + 3] = f11;
        fArr[i + 4] = f12;
        fArr[i + 5] = f5;
        fArr[i + 6] = f6;
        fArr[i + 7] = f15;
        fArr[i + 8] = f11;
        fArr[i + 9] = f14;
        fArr[i + 10] = f7;
        fArr[i + 11] = f8;
        fArr[i + 12] = f15;
        fArr[i + 13] = f13;
        fArr[i + 14] = f14;
        fArr[i + 15] = f9;
        fArr[i + 16] = f10;
        fArr[i + 17] = f15;
        fArr[i + 18] = f13;
        fArr[i + 19] = f12;
        this.idx = i + 20;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void flush() {
        int i = this.idx;
        if (i == 0) {
            return;
        }
        this.renderCalls++;
        this.totalRenderCalls++;
        int i2 = i / 20;
        if (i2 > this.maxSpritesInBatch) {
            this.maxSpritesInBatch = i2;
        }
        int i3 = i2 * 6;
        this.lastTexture.bind();
        Mesh mesh = this.mesh;
        mesh.setVertices(this.vertices, 0, this.idx);
        if (this.currentDataType == Mesh.VertexDataType.VertexArray) {
            ShortBuffer indicesBuffer = mesh.getIndicesBuffer(true);
            indicesBuffer.position(0);
            indicesBuffer.limit(i3);
        }
        if (this.blendingDisabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if (this.blendSrcFunc != -1) {
                Gdx.gl.glBlendFuncSeparate(this.blendSrcFunc, this.blendDstFunc, this.blendSrcFuncAlpha, this.blendDstFuncAlpha);
            }
        }
        ShaderProgram shaderProgram = this.customShader;
        if (shaderProgram == null) {
            shaderProgram = this.shader;
        }
        mesh.render(shaderProgram, 4, 0, i3);
        this.idx = 0;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void disableBlending() {
        if (this.blendingDisabled) {
            return;
        }
        flush();
        this.blendingDisabled = true;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void enableBlending() {
        if (this.blendingDisabled) {
            flush();
            this.blendingDisabled = false;
        }
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setBlendFunction(int i, int i2) {
        setBlendFunctionSeparate(i, i2, i, i2);
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setBlendFunctionSeparate(int i, int i2, int i3, int i4) {
        if (this.blendSrcFunc == i && this.blendDstFunc == i2 && this.blendSrcFuncAlpha == i3 && this.blendDstFuncAlpha == i4) {
            return;
        }
        flush();
        this.blendSrcFunc = i;
        this.blendDstFunc = i2;
        this.blendSrcFuncAlpha = i3;
        this.blendDstFuncAlpha = i4;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public int getBlendSrcFunc() {
        return this.blendSrcFunc;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public int getBlendDstFunc() {
        return this.blendDstFunc;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public int getBlendSrcFuncAlpha() {
        return this.blendSrcFuncAlpha;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public int getBlendDstFuncAlpha() {
        return this.blendDstFuncAlpha;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        ShaderProgram shaderProgram;
        this.mesh.dispose();
        if (!this.ownsShader || (shaderProgram = this.shader) == null) {
            return;
        }
        shaderProgram.dispose();
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setProjectionMatrix(Matrix4 matrix4) {
        if (this.drawing) {
            flush();
        }
        this.projectionMatrix.set(matrix4);
        if (this.drawing) {
            setupMatrices();
        }
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setTransformMatrix(Matrix4 matrix4) {
        if (this.drawing) {
            flush();
        }
        this.transformMatrix.set(matrix4);
        if (this.drawing) {
            setupMatrices();
        }
    }

    protected void setupMatrices() {
        this.combinedMatrix.set(this.projectionMatrix).mul(this.transformMatrix);
        ShaderProgram shaderProgram = this.customShader;
        if (shaderProgram != null) {
            shaderProgram.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.customShader.setUniformi("u_texture", 0);
        } else {
            this.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.shader.setUniformi("u_texture", 0);
        }
    }

    protected void switchTexture(Texture texture) {
        flush();
        this.lastTexture = texture;
        this.invTexWidth = 1.0f / texture.getWidth();
        this.invTexHeight = 1.0f / texture.getHeight();
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public void setShader(ShaderProgram shaderProgram) {
        if (shaderProgram == this.customShader) {
            return;
        }
        if (this.drawing) {
            flush();
        }
        this.customShader = shaderProgram;
        if (this.drawing) {
            if (shaderProgram != null) {
                shaderProgram.bind();
            } else {
                this.shader.bind();
            }
            setupMatrices();
        }
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public ShaderProgram getShader() {
        ShaderProgram shaderProgram = this.customShader;
        return shaderProgram == null ? this.shader : shaderProgram;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public boolean isBlendingEnabled() {
        return !this.blendingDisabled;
    }

    @Override // com.badlogic.gdx.graphics.g2d.Batch
    public boolean isDrawing() {
        return this.drawing;
    }
}
