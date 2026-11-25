package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInputStream;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public interface PersistentMapBase<Key, Value> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "appender", "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapBase", "appendData"));
    }

    void close() throws IOException;

    void closeAndDelete() throws IOException;

    boolean containsKey(Key key) throws IOException;

    void force() throws IOException;

    Value get(Key key) throws IOException;

    DataExternalizer<Value> getValuesExternalizer();

    boolean isClosed();

    boolean isDirty();

    void markDirty() throws IOException;

    boolean processExistingKeys(Processor<? super Key> processor) throws IOException;

    boolean processKeys(Processor<? super Key> processor) throws IOException;

    void put(Key key, Value value) throws IOException;

    void remove(Key key) throws IOException;

    default void appendData(Key key, AppendablePersistentMap.ValueDataAppender valueDataAppender) throws IOException {
        if (valueDataAppender == null) {
            $$$reportNull$$$0(0);
        }
        BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = new BufferExposingByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(bufferExposingByteArrayOutputStream);
        DataExternalizer<Value> valuesExternalizer = getValuesExternalizer();
        Value value = get(key);
        if (value != null) {
            valuesExternalizer.save(dataOutputStream, value);
        }
        valueDataAppender.append(dataOutputStream);
        dataOutputStream.close();
        DataInputStream dataInputStream = new DataInputStream(bufferExposingByteArrayOutputStream.toInputStream());
        Value value2 = valuesExternalizer.read(dataInputStream);
        dataInputStream.close();
        put(key, value2);
    }
}
