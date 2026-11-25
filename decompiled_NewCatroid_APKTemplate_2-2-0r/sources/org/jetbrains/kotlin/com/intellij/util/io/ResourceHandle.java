package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ResourceHandle<T> implements Closeable {
    public abstract T get();
}
