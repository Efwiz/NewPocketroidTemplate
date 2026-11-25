package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.files.FileHandle;

/* JADX INFO: loaded from: classes12.dex */
public abstract class FileSelector {
    public FileHandle lastFile;

    public abstract void open(Runnable runnable);

    public abstract void save(Runnable runnable);

    public abstract void selectFolder(Runnable runnable);
}
