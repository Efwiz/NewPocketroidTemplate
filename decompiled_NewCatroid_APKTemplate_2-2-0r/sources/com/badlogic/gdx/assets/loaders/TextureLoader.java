package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/TextureLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextureLoader extends AsynchronousAssetLoader<Texture, TextureParameter> {
    TextureLoaderInfo info;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/TextureLoader$TextureLoaderInfo.class
      classes8.dex
     */
    public static class TextureLoaderInfo {
        TextureData data;
        String filename;
        Texture texture;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/TextureLoader$TextureParameter.class
      classes8.dex
     */
    public static class TextureParameter extends AssetLoaderParameters<Texture> {
        public Pixmap.Format format = null;
        public boolean genMipMaps = false;
        public Texture texture = null;
        public TextureData textureData = null;
        public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureWrap wrapU = Texture.TextureWrap.ClampToEdge;
        public Texture.TextureWrap wrapV = Texture.TextureWrap.ClampToEdge;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, TextureParameter textureParameter) {
        return null;
    }

    public TextureLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.info = new TextureLoaderInfo();
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, TextureParameter textureParameter) {
        boolean z;
        this.info.filename = str;
        if (textureParameter == null || textureParameter.textureData == null) {
            Pixmap.Format format = null;
            this.info.texture = null;
            if (textureParameter != null) {
                format = textureParameter.format;
                z = textureParameter.genMipMaps;
                this.info.texture = textureParameter.texture;
            } else {
                z = false;
            }
            this.info.data = TextureData.Factory.loadFromFile(fileHandle, format, z);
        } else {
            this.info.data = textureParameter.textureData;
            this.info.texture = textureParameter.texture;
        }
        if (this.info.data.isPrepared()) {
            return;
        }
        this.info.data.prepare();
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public Texture loadSync(AssetManager assetManager, String str, FileHandle fileHandle, TextureParameter textureParameter) {
        TextureLoaderInfo textureLoaderInfo = this.info;
        if (textureLoaderInfo == null) {
            return null;
        }
        Texture texture = textureLoaderInfo.texture;
        if (texture != null) {
            texture.load(this.info.data);
        } else {
            texture = new Texture(this.info.data);
        }
        if (textureParameter != null) {
            texture.setFilter(textureParameter.minFilter, textureParameter.magFilter);
            texture.setWrap(textureParameter.wrapU, textureParameter.wrapV);
        }
        return texture;
    }
}
