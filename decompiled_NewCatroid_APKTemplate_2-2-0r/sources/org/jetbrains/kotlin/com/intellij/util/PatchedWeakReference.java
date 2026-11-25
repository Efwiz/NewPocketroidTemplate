package org.jetbrains.kotlin.com.intellij.util;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public final class PatchedWeakReference<T> extends WeakReference<T> implements Supplier<T> {
    public PatchedWeakReference(T t) {
        super(t);
    }
}
