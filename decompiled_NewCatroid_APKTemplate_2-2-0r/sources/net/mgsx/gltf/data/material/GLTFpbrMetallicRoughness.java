package net.mgsx.gltf.data.material;

import net.mgsx.gltf.data.GLTFObject;
import net.mgsx.gltf.data.texture.GLTFTextureInfo;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFpbrMetallicRoughness extends GLTFObject {
    public float[] baseColorFactor;
    public GLTFTextureInfo baseColorTexture;
    public GLTFTextureInfo metallicRoughnessTexture;
    public float metallicFactor = 1.0f;
    public float roughnessFactor = 1.0f;
}
