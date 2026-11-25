package org.jetbrains.kotlin.com.intellij.util.io.keyStorage;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public class InlinedKeyStorage<Data> implements AppendableObjectStorage<Data> {
    private final InlineKeyDescriptor<Data> myDescriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = "processor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/keyStorage/InlinedKeyStorage";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "processAll";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public boolean checkBytesAreTheSame(int i, Data data) {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public void clear() throws IOException {
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    public void force() {
    }

    public InlinedKeyStorage(InlineKeyDescriptor<Data> inlineKeyDescriptor) {
        if (inlineKeyDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        this.myDescriptor = inlineKeyDescriptor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public Data read(int i) throws IOException {
        return this.myDescriptor.fromInt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public boolean processAll(AppendableObjectStorage.StorageObjectProcessor<? super Data> storageObjectProcessor) throws IOException {
        if (storageObjectProcessor == null) {
            $$$reportNull$$$0(1);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public int append(Data data) throws IOException {
        return this.myDescriptor.toInt(data);
    }
}
