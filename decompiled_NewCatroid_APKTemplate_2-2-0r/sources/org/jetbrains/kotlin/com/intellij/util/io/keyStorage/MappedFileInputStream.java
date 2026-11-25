package org.jetbrains.kotlin.com.intellij.util.io.keyStorage;

import java.io.IOException;
import java.io.InputStream;
import org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
final class MappedFileInputStream extends InputStream {
    private int cur;
    private final long limit;
    private final ResizeableMappedFile raf;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "raf";
        } else {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/keyStorage/MappedFileInputStream";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "read";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    MappedFileInputStream(ResizeableMappedFile resizeableMappedFile, long j, long j2) {
        if (resizeableMappedFile == null) {
            $$$reportNull$$$0(0);
        }
        this.raf = resizeableMappedFile;
        this.cur = (int) j;
        this.limit = j2;
    }

    @Override // java.io.InputStream
    public int available() {
        return (int) (this.limit - ((long) this.cur));
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.cur;
        if (i >= this.limit) {
            return -1;
        }
        ResizeableMappedFile resizeableMappedFile = this.raf;
        this.cur = i + 1;
        return resizeableMappedFile.get(i);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        if (i2 > available()) {
            i2 = available();
        }
        if (available() > 0) {
            this.raf.get(this.cur, bArr, i, i2);
            this.cur += i2;
        }
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j) {
        long jMin = Math.min(j, available());
        this.cur = (int) (((long) this.cur) + jMin);
        return jMin;
    }
}
