package net.mgsx.gltf.data.extensions;

import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class KHRMaterialsIridescence {
    public static final String EXT = "KHR_materials_iridescence";
    public GLTFTextureInfo iridescenceTexture;
    public GLTFTextureInfo iridescenceThicknessTexture;
    public float iridescenceFactor = 0.0f;
    public float iridescenceIor = 1.3f;
    public float iridescenceThicknessMinimum = 100.0f;
    public float iridescenceThicknessMaximum = 400.0f;
}
