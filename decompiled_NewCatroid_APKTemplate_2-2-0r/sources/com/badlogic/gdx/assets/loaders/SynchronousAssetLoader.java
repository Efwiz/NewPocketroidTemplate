package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/SynchronousAssetLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class SynchronousAssetLoader<T, P extends AssetLoaderParameters<T>> extends AssetLoader<T, P> {
    public abstract T load(AssetManager assetManager, String str, FileHandle fileHandle, P p);

    public SynchronousAssetLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }
}
