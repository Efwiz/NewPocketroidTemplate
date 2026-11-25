package org.antlr.runtime;

import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class BitSet implements Cloneable {
    protected static final int BITS = 64;
    protected static final int LOG_BITS = 6;
    protected static final int MOD_MASK = 63;
    protected long[] bits;

    private static final long bitMask(int i) {
        return 1 << (i & 63);
    }

    private final int numWordsToHold(int i) {
        return (i >> 6) + 1;
    }

    private static final int wordNumber(int i) {
        return i >> 6;
    }

    public BitSet() {
        this(64);
    }

    public BitSet(long[] jArr) {
        this.bits = jArr;
    }

    public BitSet(List<Integer> list) {
        this();
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i).intValue());
        }
    }

    public BitSet(int i) {
        this.bits = new long[((i - 1) >> 6) + 1];
    }

    public static BitSet of(int i) {
        BitSet bitSet = new BitSet(i + 1);
        bitSet.add(i);
        return bitSet;
    }

    public static BitSet of(int i, int i2) {
        BitSet bitSet = new BitSet(Math.max(i, i2) + 1);
        bitSet.add(i);
        bitSet.add(i2);
        return bitSet;
    }

    public static BitSet of(int i, int i2, int i3) {
        BitSet bitSet = new BitSet();
        bitSet.add(i);
        bitSet.add(i2);
        bitSet.add(i3);
        return bitSet;
    }

    public static BitSet of(int i, int i2, int i3, int i4) {
        BitSet bitSet = new BitSet();
        bitSet.add(i);
        bitSet.add(i2);
        bitSet.add(i3);
        bitSet.add(i4);
        return bitSet;
    }

    public BitSet or(BitSet bitSet) {
        if (bitSet == null) {
            return this;
        }
        BitSet bitSet2 = (BitSet) clone();
        bitSet2.orInPlace(bitSet);
        return bitSet2;
    }

    public void add(int i) {
        int iWordNumber = wordNumber(i);
        if (iWordNumber >= this.bits.length) {
            growToInclude(i);
        }
        long[] jArr = this.bits;
        jArr[iWordNumber] = jArr[iWordNumber] | bitMask(i);
    }

    public void growToInclude(int i) {
        long[] jArr = new long[Math.max(this.bits.length << 1, numWordsToHold(i))];
        long[] jArr2 = this.bits;
        System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
        this.bits = jArr;
    }

    public void orInPlace(BitSet bitSet) {
        if (bitSet == null) {
            return;
        }
        long[] jArr = bitSet.bits;
        if (jArr.length > this.bits.length) {
            setSize(jArr.length);
        }
        for (int iMin = Math.min(this.bits.length, bitSet.bits.length) - 1; iMin >= 0; iMin--) {
            long[] jArr2 = this.bits;
            jArr2[iMin] = jArr2[iMin] | bitSet.bits[iMin];
        }
    }

    private void setSize(int i) {
        long[] jArr = new long[i];
        System.arraycopy(this.bits, 0, jArr, 0, Math.min(i, this.bits.length));
        this.bits = jArr;
    }

    public Object clone() {
        try {
            BitSet bitSet = (BitSet) super.clone();
            long[] jArr = new long[this.bits.length];
            bitSet.bits = jArr;
            long[] jArr2 = this.bits;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            return bitSet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    public int size() {
        int i = 0;
        for (int length = this.bits.length - 1; length >= 0; length--) {
            long j = this.bits[length];
            if (j != 0) {
                for (int i2 = 63; i2 >= 0; i2--) {
                    if (((1 << i2) & j) != 0) {
                        i++;
                    }
                }
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BitSet)) {
            return false;
        }
        BitSet bitSet = (BitSet) obj;
        int iMin = Math.min(this.bits.length, bitSet.bits.length);
        for (int i = 0; i < iMin; i++) {
            if (this.bits[i] != bitSet.bits[i]) {
                return false;
            }
        }
        if (this.bits.length > iMin) {
            int i2 = iMin + 1;
            while (true) {
                long[] jArr = this.bits;
                if (i2 >= jArr.length) {
                    break;
                }
                if (jArr[i2] != 0) {
                    return false;
                }
                i2++;
            }
        } else if (bitSet.bits.length > iMin) {
            int i3 = iMin + 1;
            while (true) {
                long[] jArr2 = bitSet.bits;
                if (i3 >= jArr2.length) {
                    break;
                }
                if (jArr2[i3] != 0) {
                    return false;
                }
                i3++;
            }
        }
        return true;
    }

    public boolean member(int i) {
        if (i < 0) {
            return false;
        }
        int iWordNumber = wordNumber(i);
        long[] jArr = this.bits;
        return iWordNumber < jArr.length && (jArr[iWordNumber] & bitMask(i)) != 0;
    }

    public void remove(int i) {
        int iWordNumber = wordNumber(i);
        long[] jArr = this.bits;
        if (iWordNumber < jArr.length) {
            jArr[iWordNumber] = jArr[iWordNumber] & (~bitMask(i));
        }
    }

    public boolean isNil() {
        for (int length = this.bits.length - 1; length >= 0; length--) {
            if (this.bits[length] != 0) {
                return false;
            }
        }
        return true;
    }

    public int numBits() {
        return this.bits.length << 6;
    }

    public int lengthInLongWords() {
        return this.bits.length;
    }

    public int[] toArray() {
        int[] iArr = new int[size()];
        int i = 0;
        for (int i2 = 0; i2 < (this.bits.length << 6); i2++) {
            if (member(i2)) {
                iArr[i] = i2;
                i++;
            }
        }
        return iArr;
    }

    public long[] toPackedArray() {
        return this.bits;
    }

    public String toString() {
        return toString(null);
    }

    public String toString(String[] strArr) {
        StringBuilder sb = new StringBuilder("{");
        boolean z = false;
        for (int i = 0; i < (this.bits.length << 6); i++) {
            if (member(i)) {
                if (i > 0 && z) {
                    sb.append(",");
                }
                if (strArr != null) {
                    sb.append(strArr[i]);
                } else {
                    sb.append(i);
                }
                z = true;
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
