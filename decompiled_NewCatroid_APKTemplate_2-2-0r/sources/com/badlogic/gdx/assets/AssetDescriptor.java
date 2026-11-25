package com.badlogic.gdx.assets;

import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/AssetDescriptor.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AssetDescriptor<T> {
    public FileHandle file;
    public final String fileName;
    public final AssetLoaderParameters params;
    public final Class<T> type;

    public AssetDescriptor(String str, Class<T> cls) {
        this(str, cls, (AssetLoaderParameters) null);
    }

    public AssetDescriptor(FileHandle fileHandle, Class<T> cls) {
        this(fileHandle, cls, (AssetLoaderParameters) null);
    }

    public AssetDescriptor(String str, Class<T> cls, AssetLoaderParameters<T> assetLoaderParameters) {
        this.fileName = str;
        this.type = cls;
        this.params = assetLoaderParameters;
    }

    public AssetDescriptor(FileHandle fileHandle, Class<T> cls, AssetLoaderParameters<T> assetLoaderParameters) {
        this.fileName = fileHandle.path();
        this.file = fileHandle;
        this.type = cls;
        this.params = assetLoaderParameters;
    }

    public String toString() {
        return this.fileName + ", " + this.type.getName();
    }
}
