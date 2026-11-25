package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.InputStream;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
public class UnsyncByteArrayInputStream extends InputStream {
    protected byte[] myBuffer;
    private int myCount;
    private int myMarkedPosition;
    private int myPosition;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 3) {
            objArr[0] = "buf";
        } else {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/UnsyncByteArrayInputStream";
        if (i == 2) {
            objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
        } else if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "read";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UnsyncByteArrayInputStream(byte[] bArr) {
        this(bArr, 0, bArr.length);
        if (bArr == null) {
            $$$reportNull$$$0(0);
        }
    }

    public UnsyncByteArrayInputStream(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        init(bArr, i, i2);
    }

    public void init(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myBuffer = bArr;
        this.myPosition = i;
        this.myCount = i2;
    }

    @Override // java.io.InputStream
    public int read() {
        int i = this.myPosition;
        if (i >= this.myCount) {
            return -1;
        }
        byte[] bArr = this.myBuffer;
        this.myPosition = i + 1;
        return bArr[i] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(3);
        }
        if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = this.myPosition;
        int i4 = this.myCount;
        if (i3 >= i4) {
            return -1;
        }
        if (i3 + i2 > i4) {
            i2 = i4 - i3;
        }
        if (i2 <= 0) {
            return 0;
        }
        System.arraycopy(this.myBuffer, i3, bArr, i, i2);
        this.myPosition += i2;
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j) {
        int i = this.myPosition;
        long j2 = ((long) i) + j;
        int i2 = this.myCount;
        if (j2 > i2) {
            j = i2 - i;
        }
        if (j < 0) {
            return 0L;
        }
        this.myPosition = (int) (((long) i) + j);
        return j;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.myCount - this.myPosition;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.myMarkedPosition = this.myPosition;
    }

    @Override // java.io.InputStream
    public void reset() {
        this.myPosition = this.myMarkedPosition;
    }
}
