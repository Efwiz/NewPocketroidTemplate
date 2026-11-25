package org.jetbrains.kotlin.com.intellij.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public class SoftReference<T> extends java.lang.ref.SoftReference<T> implements Supplier<T> {
    public SoftReference(T t) {
        super(t);
    }

    public SoftReference(T t, ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }

    public static <T> T dereference(Reference<T> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    public static <T> T deref(Supplier<T> supplier) {
        if (supplier == null) {
            return null;
        }
        return supplier.get();
    }
}
