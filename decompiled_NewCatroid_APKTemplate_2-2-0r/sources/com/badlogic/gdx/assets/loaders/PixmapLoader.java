package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/PixmapLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PixmapLoader extends AsynchronousAssetLoader<Pixmap, PixmapParameter> {
    Pixmap pixmap;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/PixmapLoader$PixmapParameter.class
      classes8.dex
     */
    public static class PixmapParameter extends AssetLoaderParameters<Pixmap> {
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, PixmapParameter pixmapParameter) {
        return null;
    }

    public PixmapLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, PixmapParameter pixmapParameter) {
        this.pixmap = null;
        this.pixmap = new Pixmap(fileHandle);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public Pixmap loadSync(AssetManager assetManager, String str, FileHandle fileHandle, PixmapParameter pixmapParameter) {
        Pixmap pixmap = this.pixmap;
        this.pixmap = null;
        return pixmap;
    }
}
