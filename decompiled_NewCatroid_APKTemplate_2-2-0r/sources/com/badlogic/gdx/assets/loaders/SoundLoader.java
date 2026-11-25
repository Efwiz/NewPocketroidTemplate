package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/SoundLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SoundLoader extends AsynchronousAssetLoader<Sound, SoundParameter> {
    private Sound sound;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/SoundLoader$SoundParameter.class
      classes8.dex
     */
    public static class SoundParameter extends AssetLoaderParameters<Sound> {
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, SoundParameter soundParameter) {
        return null;
    }

    public SoundLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    protected Sound getLoadedSound() {
        return this.sound;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, SoundParameter soundParameter) {
        this.sound = Gdx.audio.newSound(fileHandle);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public Sound loadSync(AssetManager assetManager, String str, FileHandle fileHandle, SoundParameter soundParameter) {
        Sound sound = this.sound;
        this.sound = null;
        return sound;
    }
}
