package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataOutput;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public interface AppendablePersistentMap<K, V> extends PersistentMap<K, V> {

    public interface ValueDataAppender {
        void append(DataOutput dataOutput) throws IOException;
    }
}
