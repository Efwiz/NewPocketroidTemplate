package net.mgsx.gltf.loaders.shared.material;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.data.material.GLTFMaterial;

/* JADX INFO: loaded from: classes12.dex */
public interface MaterialLoader {
    Material get(int i);

    Material getDefaultMaterial();

    void loadMaterials(Array<GLTFMaterial> array);
}
