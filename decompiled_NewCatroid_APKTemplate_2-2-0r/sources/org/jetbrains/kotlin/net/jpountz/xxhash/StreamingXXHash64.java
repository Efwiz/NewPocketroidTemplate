package org.jetbrains.kotlin.net.jpountz.xxhash;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Closeable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class StreamingXXHash64 implements Closeable {
    final long seed;

    interface Factory {
        StreamingXXHash64 newStreamingHash(long j);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public abstract long getValue();

    public abstract void update(byte[] bArr, int i, int i2);

    public String toString() {
        return getClass().getSimpleName() + "(seed=" + this.seed + Constant.AFTER_QUTO;
    }
}
