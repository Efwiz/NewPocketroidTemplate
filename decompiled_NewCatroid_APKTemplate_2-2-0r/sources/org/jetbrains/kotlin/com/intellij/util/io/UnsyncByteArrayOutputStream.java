package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.ByteArraySequence;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
public class UnsyncByteArrayOutputStream extends OutputStream {
    private final ByteArrayAllocator myAllocator;
    protected byte[] myBuffer;
    protected int myCount;
    private boolean myIsShared;

    @FunctionalInterface
    public interface ByteArrayAllocator {
        byte[] allocate(int i);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "allocator";
        } else if (i != 2) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/UnsyncByteArrayOutputStream";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "write";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public UnsyncByteArrayOutputStream() {
        this(32);
    }

    public UnsyncByteArrayOutputStream(int i) {
        this(ArrayUtil.newByteArray(i));
    }

    public UnsyncByteArrayOutputStream(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myAllocator = new ByteArrayAllocator() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnsyncByteArrayOutputStream$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnsyncByteArrayOutputStream.ByteArrayAllocator
            public final byte[] allocate(int i) {
                return ArrayUtil.newByteArray(i);
            }
        };
        this.myBuffer = bArr;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        int i2 = this.myCount + 1;
        if (i2 > this.myBuffer.length || this.myIsShared) {
            grow(i2);
            this.myIsShared = false;
        }
        this.myBuffer[this.myCount] = (byte) i;
        this.myCount = i2;
    }

    private void grow(int i) {
        byte[] bArr = this.myBuffer;
        byte[] bArrAllocate = this.myAllocator.allocate(i > bArr.length ? Math.max(bArr.length << 1, i) : bArr.length);
        byte[] bArr2 = this.myBuffer;
        System.arraycopy(bArr2, 0, bArrAllocate, 0, bArr2.length);
        this.myBuffer = bArrAllocate;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr == null) {
            $$$reportNull$$$0(2);
        }
        if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return;
        }
        int i4 = this.myCount + i2;
        if (i4 > this.myBuffer.length || this.myIsShared) {
            grow(i4);
            this.myIsShared = false;
        }
        System.arraycopy(bArr, i, this.myBuffer, this.myCount, i2);
        this.myCount = i4;
    }

    public void reset() {
        this.myCount = 0;
    }

    public byte[] toByteArray() {
        byte[] bArr = this.myBuffer;
        int length = bArr.length;
        int i = this.myCount;
        if (length == i) {
            this.myIsShared = true;
            return bArr;
        }
        return Arrays.copyOf(bArr, i);
    }

    public int size() {
        return this.myCount;
    }

    public String toString() {
        return new String(this.myBuffer, 0, this.myCount, StandardCharsets.UTF_8);
    }

    public ByteArraySequence toByteArraySequence() {
        return this.myCount == 0 ? ByteArraySequence.EMPTY : new ByteArraySequence(this.myBuffer, 0, this.myCount);
    }

    public InputStream toInputStream() {
        return new UnsyncByteArrayInputStream(this.myBuffer, 0, this.myCount);
    }
}
