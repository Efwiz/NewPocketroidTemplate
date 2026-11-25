package com.badlogic.gdx.maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/ImageResolver.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface ImageResolver {
    TextureRegion getImage(String str);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/ImageResolver$DirectImageResolver.class
      classes8.dex
     */
    public static class DirectImageResolver implements ImageResolver {
        private final ObjectMap<String, Texture> images;

        public DirectImageResolver(ObjectMap<String, Texture> objectMap) {
            this.images = objectMap;
        }

        @Override // com.badlogic.gdx.maps.ImageResolver
        public TextureRegion getImage(String str) {
            return new TextureRegion(this.images.get(str));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/ImageResolver$AssetManagerImageResolver.class
      classes8.dex
     */
    public static class AssetManagerImageResolver implements ImageResolver {
        private final AssetManager assetManager;

        public AssetManagerImageResolver(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        @Override // com.badlogic.gdx.maps.ImageResolver
        public TextureRegion getImage(String str) {
            return new TextureRegion((Texture) this.assetManager.get(str, Texture.class));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/ImageResolver$TextureAtlasImageResolver.class
      classes8.dex
     */
    public static class TextureAtlasImageResolver implements ImageResolver {
        private final TextureAtlas atlas;

        public TextureAtlasImageResolver(TextureAtlas textureAtlas) {
            this.atlas = textureAtlas;
        }

        @Override // com.badlogic.gdx.maps.ImageResolver
        public TextureRegion getImage(String str) {
            return this.atlas.findRegion(str);
        }
    }
}
