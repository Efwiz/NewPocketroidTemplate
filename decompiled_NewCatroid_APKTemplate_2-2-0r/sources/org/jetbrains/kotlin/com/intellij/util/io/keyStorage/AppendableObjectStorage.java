package org.jetbrains.kotlin.com.intellij.util.io.keyStorage;

import java.io.Closeable;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;

/* JADX INFO: loaded from: classes3.dex */
public interface AppendableObjectStorage<Data> extends Forceable, Closeable {

    @FunctionalInterface
    public interface StorageObjectProcessor<Data> {
        boolean process(int i, Data data);
    }

    int append(Data data) throws IOException;

    boolean checkBytesAreTheSame(int i, Data data) throws IOException;

    void clear() throws IOException;

    boolean processAll(StorageObjectProcessor<? super Data> storageObjectProcessor) throws IOException;

    Data read(int i) throws IOException;
}
