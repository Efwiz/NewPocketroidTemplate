package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;

/* JADX INFO: loaded from: classes3.dex */
public class PersistentEnumerator<Data> implements Forceable, DataEnumeratorEx<Data>, Closeable {
    protected final PersistentEnumeratorBase<Data> myEnumerator;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9) {
            objArr[0] = "dataDescriptor";
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentEnumerator";
        if (i == 8 || i == 9) {
            objArr[2] = "createDefaultEnumerator";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static <Data> PersistentEnumeratorBase<Data> createDefaultEnumerator(Path path, KeyDescriptor<Data> keyDescriptor, int i, StorageLockContext storageLockContext, int i2) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(8);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        return new PersistentBTreeEnumerator(path, keyDescriptor, i, storageLockContext, i2, false);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        PersistentEnumeratorBase<Data> persistentEnumeratorBase = this.myEnumerator;
        if (persistentEnumeratorBase != null) {
            persistentEnumeratorBase.close();
        }
    }

    public void markCorrupted() {
        this.myEnumerator.markCorrupted();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() {
        this.myEnumerator.m6720xd71ec63f();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataEnumerator
    public Data valueOf(int i) throws IOException {
        return this.myEnumerator.valueOf(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataEnumerator
    public int enumerate(Data data) throws IOException {
        return this.myEnumerator.enumerate(data);
    }
}
