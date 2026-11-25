package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;

/* JADX INFO: loaded from: classes3.dex */
public interface AbstractStringEnumerator extends Forceable, DataEnumerator<String>, Closeable {
    void markCorrupted();
}
