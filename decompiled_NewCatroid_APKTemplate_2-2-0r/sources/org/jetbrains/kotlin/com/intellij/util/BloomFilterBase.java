package org.jetbrains.kotlin.com.intellij.util;

import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes3.dex */
public class BloomFilterBase {
    private final int myBitsCount;
    private final long[] myElementsSet;
    private final int myHashFunctionCount;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "buffer";
        } else if (i != 3) {
            objArr[0] = "input";
        } else {
            objArr[0] = "output";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/BloomFilterBase";
        if (i == 2 || i == 3) {
            objArr[2] = "save";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected BloomFilterBase(int i, double d) {
        int iCeil = (int) Math.ceil((-Math.log(d)) / (Math.log(2.0d) * Math.log(2.0d)));
        this.myHashFunctionCount = (int) Math.ceil(((double) iCeil) * Math.log(2.0d));
        int i2 = i * iCeil;
        i2 = (i2 & 1) == 0 ? i2 + 1 : i2;
        while (!isPrime(i2)) {
            i2 += 2;
        }
        this.myBitsCount = i2;
        this.myElementsSet = new long[(i2 >> 6) + 1];
    }

    private static boolean isPrime(int i) {
        if ((i & 1) == 0 || i % 3 == 0) {
            return false;
        }
        int iSqrt = (int) Math.sqrt(i);
        for (int i2 = 6; i2 <= iSqrt; i2 += 6) {
            if (i % (i2 - 1) == 0 || i % (i2 + 1) == 0) {
                return false;
            }
        }
        return true;
    }

    protected final void addIt(int i, int i2) {
        int i3 = 0;
        while (true) {
            int i4 = this.myHashFunctionCount;
            if (i3 >= i4) {
                return;
            }
            int iAbs = Math.abs(((i3 * i) + ((i4 - i3) * i2)) % this.myBitsCount);
            long[] jArr = this.myElementsSet;
            int i5 = iAbs >> 6;
            jArr[i5] = jArr[i5] | (1 << iAbs);
            i3++;
        }
    }

    protected final boolean maybeContains(int i, int i2) {
        int i3 = 0;
        while (true) {
            int i4 = this.myHashFunctionCount;
            if (i3 >= i4) {
                return true;
            }
            int iAbs = Math.abs(((i3 * i) + ((i4 - i3) * i2)) % this.myBitsCount);
            if (((1 << iAbs) & this.myElementsSet[iAbs >> 6]) == 0) {
                return false;
            }
            i3++;
        }
    }

    protected BloomFilterBase(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            $$$reportNull$$$0(1);
        }
        this.myHashFunctionCount = byteBuffer.getInt();
        int i = byteBuffer.getInt();
        this.myBitsCount = i;
        long[] jArr = new long[(i >> 6) + 1];
        this.myElementsSet = jArr;
        byteBuffer.asLongBuffer().get(jArr);
    }

    public int sizeInBytes() {
        return (this.myElementsSet.length * 8) + 8;
    }

    public void save(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            $$$reportNull$$$0(2);
        }
        byteBuffer.putInt(this.myHashFunctionCount);
        byteBuffer.putInt(this.myBitsCount);
        byteBuffer.asLongBuffer().put(this.myElementsSet);
        byteBuffer.position(byteBuffer.position() + (this.myElementsSet.length * 8));
    }
}
