package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import org.eclipse.jgit.diff.RawText;

/* JADX INFO: loaded from: classes14.dex */
public class AutoLFInputStream extends InputStream {
    private final boolean abortIfBinary;
    private final byte[] buf;
    private int cnt;
    private boolean detectBinary;
    private final boolean forCheckout;
    private final InputStream in;
    private boolean isBinary;
    private boolean passAsIs;
    private int ptr;
    private final byte[] single;

    public enum StreamFlag {
        DETECT_BINARY,
        ABORT_IF_BINARY,
        FOR_CHECKOUT;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static StreamFlag[] valuesCustom() {
            StreamFlag[] streamFlagArrValuesCustom = values();
            int length = streamFlagArrValuesCustom.length;
            StreamFlag[] streamFlagArr = new StreamFlag[length];
            System.arraycopy(streamFlagArrValuesCustom, 0, streamFlagArr, 0, length);
            return streamFlagArr;
        }
    }

    public static class IsBinaryException extends IOException {
        private static final long serialVersionUID = 1;

        IsBinaryException() {
        }
    }

    public static AutoLFInputStream create(InputStream inputStream, StreamFlag... streamFlagArr) {
        if (streamFlagArr == null) {
            return new AutoLFInputStream(inputStream, (Set<StreamFlag>) null);
        }
        EnumSet enumSetNoneOf = EnumSet.noneOf(StreamFlag.class);
        enumSetNoneOf.addAll(Arrays.asList(streamFlagArr));
        return new AutoLFInputStream(inputStream, enumSetNoneOf);
    }

    public AutoLFInputStream(InputStream inputStream, Set<StreamFlag> set) {
        this.single = new byte[1];
        this.buf = new byte[8192];
        this.in = inputStream;
        this.detectBinary = set != null && set.contains(StreamFlag.DETECT_BINARY);
        this.abortIfBinary = set != null && set.contains(StreamFlag.ABORT_IF_BINARY);
        this.forCheckout = set != null && set.contains(StreamFlag.FOR_CHECKOUT);
    }

    @Deprecated
    public AutoLFInputStream(InputStream inputStream, boolean z) {
        this(inputStream, z, false);
    }

    @Deprecated
    public AutoLFInputStream(InputStream inputStream, boolean z, boolean z2) {
        this.single = new byte[1];
        this.buf = new byte[8192];
        this.in = inputStream;
        this.detectBinary = z;
        this.abortIfBinary = z2;
        this.forCheckout = false;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.single, 0, 1) == 1) {
            return this.single[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i2 == 0) {
            return 0;
        }
        if (this.cnt == -1) {
            return -1;
        }
        int i4 = i2 + i;
        int i5 = i;
        while (i5 < i4 && (this.ptr != this.cnt || fillBuffer())) {
            byte[] bArr2 = this.buf;
            int i6 = this.ptr;
            int i7 = i6 + 1;
            this.ptr = i7;
            byte b = bArr2[i6];
            if (this.passAsIs || b != 13) {
                bArr[i5] = b;
                i5++;
            } else {
                if (i7 == this.cnt && !fillBuffer()) {
                    bArr[i5] = 13;
                    i5++;
                    break;
                }
                byte[] bArr3 = this.buf;
                int i8 = this.ptr;
                if (bArr3[i8] == 10) {
                    i3 = i5 + 1;
                    bArr[i5] = 10;
                    this.ptr = i8 + 1;
                } else {
                    i3 = i5 + 1;
                    bArr[i5] = 13;
                }
                i5 = i3;
            }
        }
        if (i5 == i) {
            return -1;
        }
        return i5 - i;
    }

    public boolean isBinary() {
        return this.isBinary;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    private boolean fillBuffer() throws IOException {
        int i;
        this.cnt = 0;
        while (true) {
            int i2 = this.cnt;
            byte[] bArr = this.buf;
            if (i2 >= bArr.length || (i = this.in.read(bArr, i2, bArr.length - i2)) < 0) {
                break;
            }
            this.cnt += i;
        }
        int i3 = this.cnt;
        if (i3 < 1) {
            this.cnt = -1;
            return false;
        }
        if (this.detectBinary) {
            boolean zIsBinary = RawText.isBinary(this.buf, i3);
            this.isBinary = zIsBinary;
            this.passAsIs = zIsBinary;
            this.detectBinary = false;
            if (zIsBinary && this.abortIfBinary) {
                throw new IsBinaryException();
            }
            if (!zIsBinary && this.forCheckout) {
                this.passAsIs = RawText.isCrLfText(this.buf, this.cnt);
            }
        }
        this.ptr = 0;
        return true;
    }
}
