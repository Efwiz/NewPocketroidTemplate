package net.mgsx.gltf.loaders.glb;

import com.badlogic.gdx.files.FileHandle;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/* JADX INFO: loaded from: classes12.dex */
public class GLBLoader extends GLTFLoaderBase {
    public SceneAsset load(FileHandle fileHandle) {
        return load(fileHandle, false);
    }

    public SceneAsset load(FileHandle fileHandle, boolean z) {
        BinaryDataFileResolver binaryDataFileResolver = new BinaryDataFileResolver();
        binaryDataFileResolver.load(fileHandle);
        return load(binaryDataFileResolver, z);
    }

    public SceneAsset load(byte[] bArr) {
        return load(bArr, false);
    }

    public SceneAsset load(byte[] bArr, boolean z) {
        BinaryDataFileResolver binaryDataFileResolver = new BinaryDataFileResolver();
        binaryDataFileResolver.load(bArr);
        return load(binaryDataFileResolver, z);
    }
}
