package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class GaussianBlurEffect extends AbstractVfxEffect implements ChainVfxEffect {
    private float amount;
    private Convolve2DEffect convolve;
    private float invHeight;
    private float invWidth;
    private int passes;
    private BlurType type;

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
    }

    private enum Tap {
        Tap3x3(1),
        Tap5x5(2);

        public final int radius;

        Tap(int i) {
            this.radius = i;
        }
    }

    public enum BlurType {
        Gaussian3x3(Tap.Tap3x3),
        Gaussian3x3b(Tap.Tap3x3),
        Gaussian5x5(Tap.Tap5x5),
        Gaussian5x5b(Tap.Tap5x5);

        public final Tap tap;

        BlurType(Tap tap) {
            this.tap = tap;
        }
    }

    public GaussianBlurEffect() {
        this(BlurType.Gaussian5x5);
    }

    public GaussianBlurEffect(BlurType blurType) {
        this.amount = 1.0f;
        this.passes = 1;
        setType(blurType);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.convolve.dispose();
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        this.invWidth = 1.0f / i;
        this.invHeight = 1.0f / i2;
        this.convolve.resize(i, i2);
        computeBlurWeightings();
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        this.convolve.rebind();
        computeBlurWeightings();
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        for (int i = 0; i < this.passes; i++) {
            this.convolve.render(vfxRenderContext, vfxPingPongWrapper);
            if (i < this.passes - 1) {
                vfxPingPongWrapper.swap();
            }
        }
    }

    public BlurType getType() {
        return this.type;
    }

    public void setType(BlurType blurType) {
        if (blurType == null) {
            throw new IllegalArgumentException("Blur type cannot be null.");
        }
        if (this.type != blurType) {
            this.type = blurType;
            Convolve2DEffect convolve2DEffect = this.convolve;
            if (convolve2DEffect != null) {
                convolve2DEffect.dispose();
            }
            this.convolve = new Convolve2DEffect(this.type.tap.radius);
            computeBlurWeightings();
        }
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float f) {
        this.amount = f;
        computeBlurWeightings();
    }

    public int getPasses() {
        return this.passes;
    }

    public void setPasses(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Passes should be greater than 0.");
        }
        this.passes = i;
    }

    private void computeBlurWeightings() {
        float[] weights = this.convolve.getWeights();
        float[] offsetsHor = this.convolve.getOffsetsHor();
        float[] offsetsVert = this.convolve.getOffsetsVert();
        float f = this.invWidth;
        float f2 = this.invHeight;
        int i = AnonymousClass1.$SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType[this.type.ordinal()];
        if (i == 1 || i == 2) {
            computeKernel(this.type.tap.radius, this.amount, weights);
            computeOffsets(this.type.tap.radius, this.invWidth, this.invHeight, offsetsHor, offsetsVert);
        } else {
            int i2 = 0;
            if (i == 3) {
                weights[0] = 0.352941f;
                weights[1] = 0.294118f;
                weights[2] = 0.352941f;
                offsetsHor[0] = -1.33333f;
                offsetsHor[1] = 0.0f;
                offsetsHor[2] = 0.0f;
                offsetsHor[3] = 0.0f;
                offsetsHor[4] = 1.33333f;
                offsetsHor[5] = 0.0f;
                offsetsVert[0] = 0.0f;
                offsetsVert[1] = -1.33333f;
                offsetsVert[2] = 0.0f;
                offsetsVert[3] = 0.0f;
                offsetsVert[4] = 0.0f;
                offsetsVert[5] = 1.33333f;
                while (i2 < this.convolve.getLength() * 2) {
                    offsetsHor[i2] = offsetsHor[i2] * f;
                    offsetsVert[i2] = offsetsVert[i2] * f2;
                    i2++;
                }
            } else {
                if (i != 4) {
                    return;
                }
                weights[0] = 0.0702703f;
                weights[1] = 0.316216f;
                weights[2] = 0.227027f;
                weights[3] = 0.316216f;
                weights[4] = 0.0702703f;
                offsetsHor[0] = -3.23077f;
                offsetsHor[1] = 0.0f;
                offsetsHor[2] = -1.38462f;
                offsetsHor[3] = 0.0f;
                offsetsHor[4] = 0.0f;
                offsetsHor[5] = 0.0f;
                offsetsHor[6] = 1.38462f;
                offsetsHor[7] = 0.0f;
                offsetsHor[8] = 3.23077f;
                offsetsHor[9] = 0.0f;
                offsetsVert[0] = 0.0f;
                offsetsVert[1] = -3.23077f;
                offsetsVert[2] = 0.0f;
                offsetsVert[3] = -1.38462f;
                offsetsVert[4] = 0.0f;
                offsetsVert[5] = 0.0f;
                offsetsVert[6] = 0.0f;
                offsetsVert[7] = 1.38462f;
                offsetsVert[8] = 0.0f;
                offsetsVert[9] = 3.23077f;
                while (i2 < this.convolve.getLength() * 2) {
                    offsetsHor[i2] = offsetsHor[i2] * f;
                    offsetsVert[i2] = offsetsVert[i2] * f2;
                    i2++;
                }
            }
        }
        this.convolve.rebind();
    }

    /* JADX INFO: renamed from: com.crashinvaders.vfx.effects.GaussianBlurEffect$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType;

        static {
            int[] iArr = new int[BlurType.values().length];
            $SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType = iArr;
            try {
                iArr[BlurType.Gaussian3x3.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType[BlurType.Gaussian5x5.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType[BlurType.Gaussian3x3b.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$crashinvaders$vfx$effects$GaussianBlurEffect$BlurType[BlurType.Gaussian5x5b.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void computeKernel(int i, float f, float[] fArr) {
        float f2 = 2.0f * f * f;
        float fSqrt = (float) Math.sqrt(((double) f2) * 3.141592653589793d);
        float f3 = 0.0f;
        for (int i2 = -i; i2 <= i; i2++) {
            float fExp = ((float) Math.exp((-(i2 * i2)) / f2)) / fSqrt;
            fArr[i2 + i] = fExp;
            f3 += fExp;
        }
        int i3 = (i * 2) + 1;
        for (int i4 = 0; i4 < i3; i4++) {
            fArr[i4] = fArr[i4] / f3;
        }
    }

    private void computeOffsets(int i, float f, float f2, float[] fArr, float[] fArr2) {
        int i2 = -i;
        int i3 = 0;
        while (i2 <= i) {
            float f3 = i2;
            fArr[i3] = f3 * f;
            int i4 = i3 + 1;
            fArr[i4] = 0.0f;
            fArr2[i3] = 0.0f;
            fArr2[i4] = f3 * f2;
            i2++;
            i3 += 2;
        }
    }

    public static final class Convolve1DEffect extends ShaderVfxEffect implements ChainVfxEffect {
        private static final String U_SAMPLE_OFFSETS = "u_sampleOffsets";
        private static final String U_SAMPLE_WEIGHTS = "u_sampleWeights";
        private static final String U_TEXTURE = "u_texture0";
        public int length;
        public float[] offsets;
        public float[] weights;

        public Convolve1DEffect(int i) {
            this(i, new float[i], new float[i * 2]);
        }

        public Convolve1DEffect(int i, float[] fArr) {
            this(i, fArr, new float[i * 2]);
        }

        public Convolve1DEffect(int i, float[] fArr, float[] fArr2) {
            super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/convolve-1d.frag"), "#define LENGTH " + i));
            setWeights(i, fArr, fArr2);
            rebind();
        }

        @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
        public void rebind() {
            super.rebind();
            this.program.begin();
            this.program.setUniformi(U_TEXTURE, 0);
            this.program.setUniform2fv(U_SAMPLE_OFFSETS, this.offsets, 0, this.length * 2);
            this.program.setUniform1fv(U_SAMPLE_WEIGHTS, this.weights, 0, this.length);
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

        public void setWeights(int i, float[] fArr, float[] fArr2) {
            this.weights = fArr;
            this.length = i;
            this.offsets = fArr2;
        }
    }

    public static final class Convolve2DEffect extends CompositeVfxEffect implements ChainVfxEffect {
        private Convolve1DEffect hor;
        private final int length;
        private final float[] offsetsHor;
        private final float[] offsetsVert;
        private final int radius;
        private Convolve1DEffect vert;
        private final float[] weights;

        public Convolve2DEffect(int i) {
            this.radius = i;
            int i2 = (i * 2) + 1;
            this.length = i2;
            this.hor = (Convolve1DEffect) register(new Convolve1DEffect(i2));
            this.vert = (Convolve1DEffect) register(new Convolve1DEffect(i2, this.hor.weights));
            this.weights = this.hor.weights;
            this.offsetsHor = this.hor.offsets;
            this.offsetsVert = this.vert.offsets;
        }

        @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
        public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
            this.hor.render(vfxRenderContext, vfxPingPongWrapper);
            vfxPingPongWrapper.swap();
            this.vert.render(vfxRenderContext, vfxPingPongWrapper);
        }

        public int getRadius() {
            return this.radius;
        }

        public int getLength() {
            return this.length;
        }

        public float[] getWeights() {
            return this.weights;
        }

        public float[] getOffsetsHor() {
            return this.offsetsHor;
        }

        public float[] getOffsetsVert() {
            return this.offsetsVert;
        }
    }
}
