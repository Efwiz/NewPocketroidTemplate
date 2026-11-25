package net.mgsx.gltf.loaders.shared.material;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.data.material.GLTFMaterial;
import net.mgsx.gltf.loaders.shared.texture.TextureResolver;

/* JADX INFO: loaded from: classes12.dex */
public abstract class MaterialLoaderBase implements MaterialLoader {
    private Material defaultMaterial;
    private Array<Material> materials = new Array<>();
    protected TextureResolver textureResolver;

    protected abstract Material loadMaterial(GLTFMaterial gLTFMaterial);

    public MaterialLoaderBase(TextureResolver textureResolver, Material material) {
        this.textureResolver = textureResolver;
        this.defaultMaterial = material;
    }

    @Override // net.mgsx.gltf.loaders.shared.material.MaterialLoader
    public Material getDefaultMaterial() {
        return this.defaultMaterial;
    }

    @Override // net.mgsx.gltf.loaders.shared.material.MaterialLoader
    public Material get(int i) {
        return this.materials.get(i);
    }

    @Override // net.mgsx.gltf.loaders.shared.material.MaterialLoader
    public void loadMaterials(Array<GLTFMaterial> array) {
        if (array != null) {
            for (int i = 0; i < array.size; i++) {
                this.materials.add(loadMaterial(array.get(i)));
            }
        }
    }
}
