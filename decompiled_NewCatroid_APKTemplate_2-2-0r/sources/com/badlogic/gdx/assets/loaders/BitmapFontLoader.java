package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/BitmapFontLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class BitmapFontLoader extends AsynchronousAssetLoader<BitmapFont, BitmapFontParameter> {
    BitmapFont.BitmapFontData data;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/BitmapFontLoader$BitmapFontParameter.class
      classes8.dex
     */
    public static class BitmapFontParameter extends AssetLoaderParameters<BitmapFont> {
        public boolean flip = false;
        public boolean genMipMaps = false;
        public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
        public BitmapFont.BitmapFontData bitmapFontData = null;
        public String atlasName = null;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, BitmapFontParameter bitmapFontParameter) {
    }

    public BitmapFontLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, BitmapFontParameter bitmapFontParameter) {
        Array<AssetDescriptor> array = new Array<>();
        if (bitmapFontParameter != null && bitmapFontParameter.bitmapFontData != null) {
            this.data = bitmapFontParameter.bitmapFontData;
            return array;
        }
        this.data = new BitmapFont.BitmapFontData(fileHandle, bitmapFontParameter != null && bitmapFontParameter.flip);
        if (bitmapFontParameter != null && bitmapFontParameter.atlasName != null) {
            array.add(new AssetDescriptor(bitmapFontParameter.atlasName, TextureAtlas.class));
        } else {
            for (int i = 0; i < this.data.getImagePaths().length; i++) {
                FileHandle fileHandleResolve = resolve(this.data.getImagePath(i));
                TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
                if (bitmapFontParameter != null) {
                    textureParameter.genMipMaps = bitmapFontParameter.genMipMaps;
                    textureParameter.minFilter = bitmapFontParameter.minFilter;
                    textureParameter.magFilter = bitmapFontParameter.magFilter;
                }
                array.add(new AssetDescriptor(fileHandleResolve, Texture.class, textureParameter));
            }
        }
        return array;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public BitmapFont loadSync(AssetManager assetManager, String str, FileHandle fileHandle, BitmapFontParameter bitmapFontParameter) {
        if (bitmapFontParameter != null && bitmapFontParameter.atlasName != null) {
            TextureAtlas textureAtlas = (TextureAtlas) assetManager.get(bitmapFontParameter.atlasName, TextureAtlas.class);
            String string = fileHandle.sibling(this.data.imagePaths[0]).nameWithoutExtension().toString();
            TextureAtlas.AtlasRegion atlasRegionFindRegion = textureAtlas.findRegion(string);
            if (atlasRegionFindRegion == null) {
                throw new GdxRuntimeException("Could not find font region " + string + " in atlas " + bitmapFontParameter.atlasName);
            }
            return new BitmapFont(fileHandle, atlasRegionFindRegion);
        }
        int length = this.data.getImagePaths().length;
        Array array = new Array(length);
        for (int i = 0; i < length; i++) {
            array.add(new TextureRegion((Texture) assetManager.get(this.data.getImagePath(i), Texture.class)));
        }
        return new BitmapFont(this.data, (Array<TextureRegion>) array, true);
    }
}
