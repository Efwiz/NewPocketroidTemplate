package com.badlogic.gdx.backends.android;

import com.badlogic.gdx.Files;

/* JADX INFO: loaded from: classes8.dex */
public interface AndroidFiles extends Files {
    ZipResourceFile getExpansionFile();

    boolean setAPKExpansion(int i, int i2);
}
