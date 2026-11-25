package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.file.Path;

/* JADX INFO: loaded from: classes3.dex */
public class CorruptedException extends IOException {
    public CorruptedException(Path path) {
        this("Storage corrupted " + path);
    }

    protected CorruptedException(String str) {
        super(str);
    }
}
