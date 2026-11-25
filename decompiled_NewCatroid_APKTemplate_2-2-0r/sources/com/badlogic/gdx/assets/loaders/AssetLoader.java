package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/AssetLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AssetLoader<T, P extends AssetLoaderParameters<T>> {
    private FileHandleResolver resolver;

    public abstract Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, P p);

    public AssetLoader(FileHandleResolver fileHandleResolver) {
        this.resolver = fileHandleResolver;
    }

    public FileHandle resolve(String str) {
        return this.resolver.resolve(str);
    }
}
