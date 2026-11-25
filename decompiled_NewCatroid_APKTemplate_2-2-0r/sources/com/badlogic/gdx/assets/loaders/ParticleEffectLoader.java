package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/ParticleEffectLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParticleEffectLoader extends SynchronousAssetLoader<ParticleEffect, ParticleEffectParameter> {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/ParticleEffectLoader$ParticleEffectParameter.class
      classes8.dex
     */
    public static class ParticleEffectParameter extends AssetLoaderParameters<ParticleEffect> {
        public String atlasFile;
        public String atlasPrefix;
        public FileHandle imagesDir;
    }

    public ParticleEffectLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
    public ParticleEffect load(AssetManager assetManager, String str, FileHandle fileHandle, ParticleEffectParameter particleEffectParameter) throws Throwable {
        ParticleEffect particleEffect = new ParticleEffect();
        if (particleEffectParameter != null && particleEffectParameter.atlasFile != null) {
            particleEffect.load(fileHandle, (TextureAtlas) assetManager.get(particleEffectParameter.atlasFile, TextureAtlas.class), particleEffectParameter.atlasPrefix);
        } else if (particleEffectParameter != null && particleEffectParameter.imagesDir != null) {
            particleEffect.load(fileHandle, particleEffectParameter.imagesDir);
        } else {
            particleEffect.load(fileHandle, fileHandle.parent());
        }
        return particleEffect;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, ParticleEffectParameter particleEffectParameter) {
        if (particleEffectParameter == null || particleEffectParameter.atlasFile == null) {
            return null;
        }
        Array<AssetDescriptor> array = new Array<>();
        array.add(new AssetDescriptor(particleEffectParameter.atlasFile, TextureAtlas.class));
        return array;
    }
}
