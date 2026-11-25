package net.mgsx.gltf.loaders.gltf;

import com.badlogic.gdx.files.FileHandle;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFLoader extends GLTFLoaderBase {
    public SceneAsset load(FileHandle fileHandle) {
        return load(fileHandle, false);
    }

    public SceneAsset load(FileHandle fileHandle, boolean z) {
        SeparatedDataFileResolver separatedDataFileResolver = new SeparatedDataFileResolver();
        separatedDataFileResolver.load(fileHandle);
        return load(separatedDataFileResolver, z);
    }
}
