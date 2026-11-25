package com.badlogic.gdx.assets;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/AssetLoaderParameters.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AssetLoaderParameters<T> {
    public LoadedCallback loadedCallback;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/AssetLoaderParameters$LoadedCallback.class
      classes8.dex
     */
    public interface LoadedCallback {
        void finishedLoading(AssetManager assetManager, String str, Class cls);
    }
}
