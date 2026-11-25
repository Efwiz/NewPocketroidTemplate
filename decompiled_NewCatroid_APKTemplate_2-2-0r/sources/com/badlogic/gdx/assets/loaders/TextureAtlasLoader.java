package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/TextureAtlasLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasParameter> {
    TextureAtlas.TextureAtlasData data;

    public TextureAtlasLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
    public TextureAtlas load(AssetManager assetManager, String str, FileHandle fileHandle, TextureAtlasParameter textureAtlasParameter) {
        Array.ArrayIterator<TextureAtlas.TextureAtlasData.Page> it = this.data.getPages().iterator();
        while (it.hasNext()) {
            TextureAtlas.TextureAtlasData.Page next = it.next();
            next.texture = (Texture) assetManager.get(next.textureFile.path().replaceAll("\\\\", "/"), Texture.class);
        }
        TextureAtlas textureAtlas = new TextureAtlas(this.data);
        this.data = null;
        return textureAtlas;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, TextureAtlasParameter textureAtlasParameter) {
        FileHandle fileHandleParent = fileHandle.parent();
        if (textureAtlasParameter != null) {
            this.data = new TextureAtlas.TextureAtlasData(fileHandle, fileHandleParent, textureAtlasParameter.flip);
        } else {
            this.data = new TextureAtlas.TextureAtlasData(fileHandle, fileHandleParent, false);
        }
        Array<AssetDescriptor> array = new Array<>();
        Array.ArrayIterator<TextureAtlas.TextureAtlasData.Page> it = this.data.getPages().iterator();
        while (it.hasNext()) {
            TextureAtlas.TextureAtlasData.Page next = it.next();
            TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
            textureParameter.format = next.format;
            textureParameter.genMipMaps = next.useMipMaps;
            textureParameter.minFilter = next.minFilter;
            textureParameter.magFilter = next.magFilter;
            array.add(new AssetDescriptor(next.textureFile, Texture.class, textureParameter));
        }
        return array;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/TextureAtlasLoader$TextureAtlasParameter.class
      classes8.dex
     */
    public static class TextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {
        public boolean flip;

        public TextureAtlasParameter() {
            this.flip = false;
        }

        public TextureAtlasParameter(boolean z) {
            this.flip = z;
        }
    }
}
