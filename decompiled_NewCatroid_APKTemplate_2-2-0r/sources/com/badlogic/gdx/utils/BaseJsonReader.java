package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import java.io.InputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/BaseJsonReader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface BaseJsonReader {
    JsonValue parse(FileHandle fileHandle);

    JsonValue parse(InputStream inputStream);
}
