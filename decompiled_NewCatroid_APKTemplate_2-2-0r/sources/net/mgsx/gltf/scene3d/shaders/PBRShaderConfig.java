package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;

/* JADX INFO: loaded from: classes12.dex */
public class PBRShaderConfig extends DefaultShader.Config {
    public static final float DEFAULT_GAMMA = 2.2f;
    public SRGB manualSRGB = SRGB.ACCURATE;
    public SRGB transmissionSRGB = SRGB.ACCURATE;
    public SRGB mirrorSRGB = SRGB.ACCURATE;
    public boolean manualGammaCorrection = true;
    public float gamma = 2.2f;
    public String glslVersion = null;
    public int numVertexColors = 1;
    public String prefix = null;

    public enum SRGB {
        NONE,
        FAST,
        ACCURATE
    }
}
