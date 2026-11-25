package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.KTXTextureData;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/CubemapLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CubemapLoader extends AsynchronousAssetLoader<Cubemap, CubemapParameter> {
    CubemapLoaderInfo info;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/CubemapLoader$CubemapLoaderInfo.class
      classes8.dex
     */
    public static class CubemapLoaderInfo {
        Cubemap cubemap;
        CubemapData data;
        String filename;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/CubemapLoader$CubemapParameter.class
      classes8.dex
     */
    public static class CubemapParameter extends AssetLoaderParameters<Cubemap> {
        public Pixmap.Format format = null;
        public Cubemap cubemap = null;
        public CubemapData cubemapData = null;
        public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureWrap wrapU = Texture.TextureWrap.ClampToEdge;
        public Texture.TextureWrap wrapV = Texture.TextureWrap.ClampToEdge;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, CubemapParameter cubemapParameter) {
        return null;
    }

    public CubemapLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.info = new CubemapLoaderInfo();
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, CubemapParameter cubemapParameter) {
        this.info.filename = str;
        if (cubemapParameter == null || cubemapParameter.cubemapData == null) {
            this.info.cubemap = null;
            if (cubemapParameter != null) {
                Pixmap.Format format = cubemapParameter.format;
                this.info.cubemap = cubemapParameter.cubemap;
            }
            if (str.contains(".ktx") || str.contains(".zktx")) {
                this.info.data = new KTXTextureData(fileHandle, false);
            }
        } else {
            this.info.data = cubemapParameter.cubemapData;
            this.info.cubemap = cubemapParameter.cubemap;
        }
        if (this.info.data.isPrepared()) {
            return;
        }
        this.info.data.prepare();
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public Cubemap loadSync(AssetManager assetManager, String str, FileHandle fileHandle, CubemapParameter cubemapParameter) {
        CubemapLoaderInfo cubemapLoaderInfo = this.info;
        if (cubemapLoaderInfo == null) {
            return null;
        }
        Cubemap cubemap = cubemapLoaderInfo.cubemap;
        if (cubemap != null) {
            cubemap.load(this.info.data);
        } else {
            cubemap = new Cubemap(this.info.data);
        }
        if (cubemapParameter != null) {
            cubemap.setFilter(cubemapParameter.minFilter, cubemapParameter.magFilter);
            cubemap.setWrap(cubemapParameter.wrapU, cubemapParameter.wrapV);
        }
        return cubemap;
    }
}
