package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/SkinLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SkinLoader extends AsynchronousAssetLoader<Skin, SkinParameter> {
    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, SkinParameter skinParameter) {
    }

    public SkinLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, SkinParameter skinParameter) {
        Array<AssetDescriptor> array = new Array<>();
        if (skinParameter == null || skinParameter.textureAtlasPath == null) {
            array.add(new AssetDescriptor(fileHandle.pathWithoutExtension() + ".atlas", TextureAtlas.class));
        } else if (skinParameter.textureAtlasPath != null) {
            array.add(new AssetDescriptor(skinParameter.textureAtlasPath, TextureAtlas.class));
        }
        return array;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public Skin loadSync(AssetManager assetManager, String str, FileHandle fileHandle, SkinParameter skinParameter) {
        String str2 = fileHandle.pathWithoutExtension() + ".atlas";
        ObjectMap<String, Object> objectMap = null;
        if (skinParameter != null) {
            if (skinParameter.textureAtlasPath != null) {
                str2 = skinParameter.textureAtlasPath;
            }
            if (skinParameter.resources != null) {
                objectMap = skinParameter.resources;
            }
        }
        Skin skinNewSkin = newSkin((TextureAtlas) assetManager.get(str2, TextureAtlas.class));
        if (objectMap != null) {
            ObjectMap.Entries<String, Object> it = objectMap.entries().iterator();
            while (it.hasNext()) {
                ObjectMap.Entry next = it.next();
                skinNewSkin.add((String) next.key, next.value);
            }
        }
        skinNewSkin.load(fileHandle);
        return skinNewSkin;
    }

    protected Skin newSkin(TextureAtlas textureAtlas) {
        return new Skin(textureAtlas);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/SkinLoader$SkinParameter.class
      classes8.dex
     */
    public static class SkinParameter extends AssetLoaderParameters<Skin> {
        public final ObjectMap<String, Object> resources;
        public final String textureAtlasPath;

        public SkinParameter() {
            this(null, null);
        }

        public SkinParameter(ObjectMap<String, Object> objectMap) {
            this(null, objectMap);
        }

        public SkinParameter(String str) {
            this(str, null);
        }

        public SkinParameter(String str, ObjectMap<String, Object> objectMap) {
            this.textureAtlasPath = str;
            this.resources = objectMap;
        }
    }
}
