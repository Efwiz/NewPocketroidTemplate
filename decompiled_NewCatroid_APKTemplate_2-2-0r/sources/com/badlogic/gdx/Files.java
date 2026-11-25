package com.badlogic.gdx;

import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Files.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Files {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Files$FileType.class
      classes8.dex
     */
    public enum FileType {
        Classpath,
        Internal,
        External,
        Absolute,
        Local
    }

    FileHandle absolute(String str);

    FileHandle classpath(String str);

    FileHandle external(String str);

    String getExternalStoragePath();

    FileHandle getFileHandle(String str, FileType fileType);

    String getLocalStoragePath();

    FileHandle internal(String str);

    boolean isExternalStorageAvailable();

    boolean isLocalStorageAvailable();

    FileHandle local(String str);
}
