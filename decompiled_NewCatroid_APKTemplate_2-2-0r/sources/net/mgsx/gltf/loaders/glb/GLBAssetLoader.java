package net.mgsx.gltf.loaders.glb;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.loaders.shared.SceneAssetLoaderParameters;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/* JADX INFO: loaded from: classes12.dex */
public class GLBAssetLoader extends AsynchronousAssetLoader<SceneAsset, SceneAssetLoaderParameters> {
    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
        return null;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
    }

    public GLBAssetLoader() {
        this(new InternalFileHandleResolver());
    }

    public GLBAssetLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public SceneAsset loadSync(AssetManager assetManager, String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
        return new GLBLoader().load(fileHandle, sceneAssetLoaderParameters != null && sceneAssetLoaderParameters.withData);
    }
}
