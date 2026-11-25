package net.mgsx.gltf.data.material;

import net.mgsx.gltf.data.GLTFEntity;
import net.mgsx.gltf.data.texture.GLTFNormalTextureInfo;
import net.mgsx.gltf.data.texture.GLTFOcclusionTextureInfo;
import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFMaterial extends GLTFEntity {
    public Float alphaCutoff;
    public String alphaMode;
    public Boolean doubleSided;
    public float[] emissiveFactor;
    public GLTFTextureInfo emissiveTexture;
    public GLTFNormalTextureInfo normalTexture;
    public GLTFOcclusionTextureInfo occlusionTexture;
    public GLTFpbrMetallicRoughness pbrMetallicRoughness;
}
