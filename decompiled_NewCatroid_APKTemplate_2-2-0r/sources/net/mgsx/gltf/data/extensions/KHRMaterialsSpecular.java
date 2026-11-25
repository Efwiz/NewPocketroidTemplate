package net.mgsx.gltf.data.extensions;

import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class KHRMaterialsSpecular {
    public static final String EXT = "KHR_materials_specular";
    public float specularFactor = 1.0f;
    public GLTFTextureInfo specularTexture = null;
    public float[] specularColorFactor = {1.0f, 1.0f, 1.0f};
    public GLTFTextureInfo specularColorTexture = null;
}
