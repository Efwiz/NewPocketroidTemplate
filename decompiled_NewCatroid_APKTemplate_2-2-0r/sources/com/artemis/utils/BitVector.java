package com.artemis.utils;

import com.artemis.ComponentManager;
import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public class BitVector {
    long[] words;

    public BitVector() {
        this.words = new long[]{0};
    }

    public BitVector(int i) {
        this.words = new long[]{0};
        checkCapacity(i >>> 6);
    }

    public BitVector(BitVector bitVector) {
        this.words = new long[]{0};
        long[] jArr = bitVector.words;
        this.words = Arrays.copyOf(jArr, jArr.length);
    }

    public boolean get(int i) {
        int i2 = i >>> 6;
        long[] jArr = this.words;
        return i2 < jArr.length && (jArr[i2] & (1 << i)) != 0;
    }

    public void set(int i) {
        int i2 = i >>> 6;
        checkCapacity(i2);
        long[] jArr = this.words;
        jArr[i2] = jArr[i2] | (1 << i);
    }

    public void set(int i, boolean z) {
        if (z) {
            set(i);
        } else {
            clear(i);
        }
    }

    public boolean unsafeGet(int i) {
        return (this.words[i >>> 6] & (1 << i)) != 0;
    }

    public void unsafeSet(int i) {
        long[] jArr = this.words;
        int i2 = i >>> 6;
        jArr[i2] = jArr[i2] | (1 << i);
    }

    public void unsafeSet(int i, boolean z) {
        if (z) {
            unsafeSet(i);
        } else {
            unsafeClear(i);
        }
    }

    public void flip(int i) {
        int i2 = i >>> 6;
        checkCapacity(i2);
        long[] jArr = this.words;
        jArr[i2] = jArr[i2] ^ (1 << i);
    }

    public void ensureCapacity(int i) {
        checkCapacity(i >>> 6);
    }

    private void checkCapacity(int i) {
        long[] jArr = this.words;
        if (i >= jArr.length) {
            long[] jArr2 = new long[i + 1];
            System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
            this.words = jArr2;
        }
    }

    public void clear(int i) {
        int i2 = i >>> 6;
        long[] jArr = this.words;
        if (i2 >= jArr.length) {
            return;
        }
        jArr[i2] = jArr[i2] & (~(1 << i));
    }

    public void unsafeClear(int i) {
        long[] jArr = this.words;
        int i2 = i >>> 6;
        jArr[i2] = jArr[i2] & (~(1 << i));
    }

    public void clear() {
        Arrays.fill(this.words, 0L);
    }

    public int length() {
        long[] jArr = this.words;
        for (int length = jArr.length - 1; length >= 0; length--) {
            long j = jArr[length];
            if (j != 0) {
                return ((length << 6) + 64) - Long.numberOfLeadingZeros(j);
            }
        }
        return 0;
    }

    public boolean isEmpty() {
        for (long j : this.words) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    public int nextSetBit(int i) {
        long j;
        int i2 = i >>> 6;
        long[] jArr = this.words;
        if (i2 >= jArr.length) {
            return -1;
        }
        long j2 = jArr[i2] >>> i;
        if (j2 != 0) {
            return i + Long.numberOfTrailingZeros(j2);
        }
        do {
            i2++;
            long[] jArr2 = this.words;
            if (i2 >= jArr2.length) {
                return -1;
            }
            j = jArr2[i2];
        } while (j == 0);
        return (i2 * 64) + Long.numberOfTrailingZeros(j);
    }

    public int nextClearBit(int i) {
        long j;
        int i2 = i >>> 6;
        long[] jArr = this.words;
        if (i2 >= jArr.length) {
            return Math.min(i, jArr.length << 6);
        }
        long j2 = ~(jArr[i2] >>> i);
        if (j2 != 0) {
            return i + Long.numberOfTrailingZeros(j2);
        }
        do {
            i2++;
            long[] jArr2 = this.words;
            if (i2 < jArr2.length) {
                j = ~jArr2[i2];
            } else {
                return Math.min(i, jArr2.length << 6);
            }
        } while (j == 0);
        return (i2 * 64) + Long.numberOfTrailingZeros(j);
    }

    public void and(BitVector bitVector) {
        int iMin = Math.min(this.words.length, bitVector.words.length);
        for (int i = 0; iMin > i; i++) {
            long[] jArr = this.words;
            jArr[i] = jArr[i] & bitVector.words[i];
        }
        long[] jArr2 = this.words;
        if (jArr2.length > iMin) {
            int length = jArr2.length;
            while (length > iMin) {
                this.words[iMin] = 0;
                iMin++;
            }
        }
    }

    public void andNot(BitVector bitVector) {
        int iMin = Math.min(this.words.length, bitVector.words.length);
        for (int i = 0; iMin > i; i++) {
            long[] jArr = this.words;
            jArr[i] = jArr[i] & (~bitVector.words[i]);
        }
    }

    public void or(BitVector bitVector) {
        int iMin = Math.min(this.words.length, bitVector.words.length);
        for (int i = 0; iMin > i; i++) {
            long[] jArr = this.words;
            jArr[i] = jArr[i] | bitVector.words[i];
        }
        long[] jArr2 = bitVector.words;
        if (iMin < jArr2.length) {
            checkCapacity(jArr2.length);
            int length = bitVector.words.length;
            while (length > iMin) {
                this.words[iMin] = bitVector.words[iMin];
                iMin++;
            }
        }
    }

    public void xor(BitVector bitVector) {
        int iMin = Math.min(this.words.length, bitVector.words.length);
        for (int i = 0; iMin > i; i++) {
            long[] jArr = this.words;
            jArr[i] = jArr[i] ^ bitVector.words[i];
        }
        long[] jArr2 = bitVector.words;
        if (iMin < jArr2.length) {
            checkCapacity(jArr2.length);
            int length = bitVector.words.length;
            while (length > iMin) {
                this.words[iMin] = bitVector.words[iMin];
                iMin++;
            }
        }
    }

    public boolean intersects(BitVector bitVector) {
        long[] jArr = this.words;
        long[] jArr2 = bitVector.words;
        int iMin = Math.min(jArr.length, jArr2.length);
        for (int i = 0; iMin > i; i++) {
            if ((jArr[i] & jArr2[i]) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(BitVector bitVector) {
        long[] jArr = this.words;
        long[] jArr2 = bitVector.words;
        int length = jArr2.length;
        int length2 = jArr.length;
        for (int i = length2; i < length; i++) {
            if (jArr2[i] != 0) {
                return false;
            }
        }
        int iMin = Math.min(length2, length);
        for (int i2 = 0; iMin > i2; i2++) {
            long j = jArr[i2];
            long j2 = jArr2[i2];
            if ((j & j2) != j2) {
                return false;
            }
        }
        return true;
    }

    public int cardinality() {
        int i = 0;
        int iBitCount = 0;
        while (true) {
            long[] jArr = this.words;
            if (i >= jArr.length) {
                return iBitCount;
            }
            iBitCount += Long.bitCount(jArr[i]);
            i++;
        }
    }

    public IntBag toIntBag(IntBag intBag) {
        int i = 0;
        if (isEmpty()) {
            intBag.setSize(0);
            return intBag;
        }
        int iPrepareBag = prepareBag(intBag, 1);
        int[] data = intBag.getData();
        int i2 = 0;
        while (iPrepareBag > i) {
            long j = this.words[i2];
            int i3 = i2 << 6;
            while (j != 0) {
                long j2 = (-j) & j;
                data[i] = Long.bitCount(j2 - 1) + i3;
                j ^= j2;
                i++;
            }
            i2++;
        }
        return intBag;
    }

    public IntBag toIntBagIdCid(ComponentManager componentManager, IntBag intBag) {
        int i = 0;
        if (isEmpty()) {
            intBag.setSize(0);
            return intBag;
        }
        int iPrepareBag = prepareBag(intBag, 2);
        int[] data = intBag.getData();
        int i2 = 0;
        while (iPrepareBag > i) {
            long j = this.words[i2];
            int i3 = i2 << 6;
            while (j != 0) {
                long j2 = (-j) & j;
                int iBitCount = Long.bitCount(j2 - 1) + i3;
                data[i] = iBitCount;
                data[i + 1] = componentManager.getIdentity(iBitCount);
                i += 2;
                j ^= j2;
            }
            i2++;
        }
        return intBag;
    }

    private int prepareBag(IntBag intBag, int i) {
        int iCardinality = i * cardinality();
        intBag.ensureCapacity(iCardinality);
        intBag.setSize(iCardinality);
        return iCardinality;
    }

    public int hashCode() {
        int length = length() >>> 6;
        int i = 0;
        for (int i2 = 0; length >= i2; i2++) {
            long j = this.words[i2];
            i = (i * 127) + ((int) (j ^ (j >>> 32)));
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BitVector bitVector = (BitVector) obj;
        long[] jArr = bitVector.words;
        int iMin = Math.min(this.words.length, jArr.length);
        for (int i = 0; iMin > i; i++) {
            if (this.words[i] != jArr[i]) {
                return false;
            }
        }
        return this.words.length == jArr.length || length() == bitVector.length();
    }

    public String toString() {
        int iCardinality = cardinality();
        int iMin = Math.min(128, iCardinality);
        StringBuilder sb = new StringBuilder("BitVector[");
        sb.append(iCardinality);
        if (iCardinality > 0) {
            sb.append(": {");
            int i = 0;
            int iNextSetBit = nextSetBit(0);
            while (iMin > i && iNextSetBit != -1) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(iNextSetBit);
                i++;
                iNextSetBit = nextSetBit(iNextSetBit + 1);
            }
            if (iCardinality > iMin) {
                sb.append(" ...");
            }
            sb.append("}");
        }
        sb.append("]");
        return sb.toString();
    }
}
