package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/TextureProvider.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TextureProvider {
    Texture load(String str);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/TextureProvider$FileTextureProvider.class
      classes8.dex
     */
    public static class FileTextureProvider implements TextureProvider {
        private Texture.TextureFilter magFilter;
        private Texture.TextureFilter minFilter;
        private Texture.TextureWrap uWrap;
        private boolean useMipMaps;
        private Texture.TextureWrap vWrap;

        public FileTextureProvider() {
            Texture.TextureFilter textureFilter = Texture.TextureFilter.Linear;
            this.magFilter = textureFilter;
            this.minFilter = textureFilter;
            Texture.TextureWrap textureWrap = Texture.TextureWrap.Repeat;
            this.vWrap = textureWrap;
            this.uWrap = textureWrap;
            this.useMipMaps = false;
        }

        public FileTextureProvider(Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, Texture.TextureWrap textureWrap, Texture.TextureWrap textureWrap2, boolean z) {
            this.minFilter = textureFilter;
            this.magFilter = textureFilter2;
            this.uWrap = textureWrap;
            this.vWrap = textureWrap2;
            this.useMipMaps = z;
        }

        @Override // com.badlogic.gdx.graphics.g3d.utils.TextureProvider
        public Texture load(String str) {
            Texture texture = new Texture(Gdx.files.internal(str), this.useMipMaps);
            texture.setFilter(this.minFilter, this.magFilter);
            texture.setWrap(this.uWrap, this.vWrap);
            return texture;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/TextureProvider$AssetTextureProvider.class
      classes8.dex
     */
    public static class AssetTextureProvider implements TextureProvider {
        public final AssetManager assetManager;

        public AssetTextureProvider(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        @Override // com.badlogic.gdx.graphics.g3d.utils.TextureProvider
        public Texture load(String str) {
            return (Texture) this.assetManager.get(str, Texture.class);
        }
    }
}
