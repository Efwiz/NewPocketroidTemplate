package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;

/* JADX INFO: loaded from: classes8.dex */
public class CrtEffect extends ShaderVfxEffect implements ChainVfxEffect {
    private static final String U_RESOLUTION = "u_resolution";
    private static final String U_TEXTURE0 = "u_texture0";
    private static final Vector2 tmpVec = new Vector2();
    private SizeSource sizeSource;
    private final Vector2 viewportSize;

    public enum LineStyle {
        CROSSLINE_HARD,
        VERTICAL_HARD,
        HORIZONTAL_HARD,
        VERTICAL_SMOOTH,
        HORIZONTAL_SMOOTH
    }

    public enum SizeSource {
        VIEWPORT,
        SCREEN
    }

    public CrtEffect() {
        this(LineStyle.HORIZONTAL_HARD, 1.3f, 0.5f);
    }

    public CrtEffect(LineStyle lineStyle, float f, float f2) {
        super(VfxGLUtils.compileShader(Gdx.files.classpath("gdxvfx/shaders/screenspace.vert"), Gdx.files.classpath("gdxvfx/shaders/crt.frag"), "#define SL_BRIGHTNESS_MIN " + f + "\n#define SL_BRIGHTNESS_MAX " + f2 + "\n#define LINE_TYPE " + lineStyle.ordinal()));
        this.viewportSize = new Vector2();
        this.sizeSource = SizeSource.VIEWPORT;
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        super.resize(i, i2);
        this.viewportSize.set(i, i2);
        rebind();
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        super.rebind();
        this.program.begin();
        this.program.setUniformi(U_TEXTURE0, 0);
        int i = AnonymousClass1.$SwitchMap$com$crashinvaders$vfx$effects$CrtEffect$SizeSource[this.sizeSource.ordinal()];
        if (i == 1) {
            this.program.setUniformf(U_RESOLUTION, this.viewportSize);
        } else if (i == 2) {
            this.program.setUniformf(U_RESOLUTION, tmpVec.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }
        this.program.end();
    }

    /* JADX INFO: renamed from: com.crashinvaders.vfx.effects.CrtEffect$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$crashinvaders$vfx$effects$CrtEffect$SizeSource;

        static {
            int[] iArr = new int[SizeSource.values().length];
            $SwitchMap$com$crashinvaders$vfx$effects$CrtEffect$SizeSource = iArr;
            try {
                iArr[SizeSource.VIEWPORT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$crashinvaders$vfx$effects$CrtEffect$SizeSource[SizeSource.SCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.crashinvaders.vfx.effects.ChainVfxEffect
    public void render(VfxRenderContext vfxRenderContext, VfxPingPongWrapper vfxPingPongWrapper) {
        render(vfxRenderContext, vfxPingPongWrapper.getSrcBuffer(), vfxPingPongWrapper.getDstBuffer());
    }

    public void render(VfxRenderContext vfxRenderContext, VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        vfxFrameBuffer.getTexture().bind(0);
        renderShader(vfxRenderContext, vfxFrameBuffer2);
    }

    public SizeSource getSizeSource() {
        return this.sizeSource;
    }

    public void setSizeSource(SizeSource sizeSource) {
        if (sizeSource == null) {
            throw new IllegalArgumentException("Size source cannot be null.");
        }
        if (this.sizeSource == sizeSource) {
            return;
        }
        this.sizeSource = sizeSource;
        rebind();
    }
}
