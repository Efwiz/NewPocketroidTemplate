package net.mgsx.gltf.data.extensions;

import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class KHRMaterialsVolume {
    public static final String EXT = "KHR_materials_volume";
    public float thicknessFactor = 0.0f;
    public GLTFTextureInfo thicknessTexture = null;
    public Float attenuationDistance = null;
    public float[] attenuationColor = {1.0f, 1.0f, 1.0f};
}
