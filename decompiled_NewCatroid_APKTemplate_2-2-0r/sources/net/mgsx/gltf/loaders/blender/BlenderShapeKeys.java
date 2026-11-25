package net.mgsx.gltf.loaders.blender;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import net.mgsx.gltf.data.geometry.GLTFMesh;

/* JADX INFO: loaded from: classes12.dex */
public class BlenderShapeKeys {
    public static Array<String> parse(GLTFMesh gLTFMesh) {
        JsonValue jsonValue;
        if (gLTFMesh.extras == null || (jsonValue = gLTFMesh.extras.value.get("targetNames")) == null || !jsonValue.isArray()) {
            return null;
        }
        return new Array<>(jsonValue.asStringArray());
    }
}
