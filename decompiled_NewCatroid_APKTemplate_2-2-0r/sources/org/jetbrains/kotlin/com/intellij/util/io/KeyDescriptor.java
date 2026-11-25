package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataOutput;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy;

/* JADX INFO: loaded from: classes3.dex */
public interface KeyDescriptor<T> extends EqualityPolicy<T>, DataExternalizer<T> {
    @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    int getHashCode(T t);

    void save(DataOutput dataOutput, T t) throws IOException;
}
