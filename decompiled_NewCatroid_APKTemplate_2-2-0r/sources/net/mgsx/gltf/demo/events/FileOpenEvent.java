package net.mgsx.gltf.demo.events;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/* JADX INFO: loaded from: classes12.dex */
public class FileOpenEvent extends ChangeListener.ChangeEvent {
    public FileHandle file;

    public FileOpenEvent(FileHandle fileHandle) {
        this.file = fileHandle;
    }
}
