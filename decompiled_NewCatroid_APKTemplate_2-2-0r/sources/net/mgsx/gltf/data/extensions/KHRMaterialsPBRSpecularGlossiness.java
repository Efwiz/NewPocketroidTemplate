package net.mgsx.gltf.data.extensions;

import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class KHRMaterialsPBRSpecularGlossiness {
    public static final String EXT = "KHR_materials_pbrSpecularGlossiness";
    public float[] diffuseFactor;
    public GLTFTextureInfo diffuseTexture;
    public float glossinessFactor = 1.0f;
    public float[] specularFactor;
    public GLTFTextureInfo specularGlossinessTexture;
}
