package antlr.collections.impl;

import antlr.CharFormatter;

/* JADX INFO: loaded from: classes.dex */
public class BitSet implements Cloneable {
    protected static final int BITS = 64;
    protected static final int LOG_BITS = 6;
    protected static final int MOD_MASK = 63;
    protected static final int NIBBLE = 4;
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

    public BitSet(int i) {
        this.bits = new long[((i - 1) >> 6) + 1];
    }

    public void add(int i) {
        int iWordNumber = wordNumber(i);
        if (iWordNumber >= this.bits.length) {
            growToInclude(i);
        }
        long[] jArr = this.bits;
        jArr[iWordNumber] = jArr[iWordNumber] | bitMask(i);
    }

    public BitSet and(BitSet bitSet) {
        BitSet bitSet2 = (BitSet) clone();
        bitSet2.andInPlace(bitSet);
        return bitSet2;
    }

    public void andInPlace(BitSet bitSet) {
        int iMin = Math.min(this.bits.length, bitSet.bits.length);
        for (int i = iMin - 1; i >= 0; i--) {
            long[] jArr = this.bits;
            jArr[i] = jArr[i] & bitSet.bits[i];
        }
        while (true) {
            long[] jArr2 = this.bits;
            if (iMin >= jArr2.length) {
                return;
            }
            jArr2[iMin] = 0;
            iMin++;
        }
    }

    public void clear() {
        for (int length = this.bits.length - 1; length >= 0; length--) {
            this.bits[length] = 0;
        }
    }

    public void clear(int i) {
        int iWordNumber = wordNumber(i);
        if (iWordNumber >= this.bits.length) {
            growToInclude(i);
        }
        long[] jArr = this.bits;
        jArr[iWordNumber] = jArr[iWordNumber] & (~bitMask(i));
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

    public int degree() {
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
        int i = iMin;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                long[] jArr = this.bits;
                if (jArr.length > iMin) {
                    int length = jArr.length;
                    while (true) {
                        int i3 = length - 1;
                        if (length <= iMin) {
                            return true;
                        }
                        if (this.bits[i3] != 0) {
                            return false;
                        }
                        length = i3;
                    }
                } else {
                    long[] jArr2 = bitSet.bits;
                    if (jArr2.length <= iMin) {
                        return true;
                    }
                    int length2 = jArr2.length;
                    while (true) {
                        int i4 = length2 - 1;
                        if (length2 <= iMin) {
                            return true;
                        }
                        if (bitSet.bits[i4] != 0) {
                            return false;
                        }
                        length2 = i4;
                    }
                }
            } else {
                if (this.bits[i2] != bitSet.bits[i2]) {
                    return false;
                }
                i = i2;
            }
        }
    }

    public static Vector getRanges(int[] iArr) {
        if (iArr.length == 0) {
            return null;
        }
        int i = 0;
        int i2 = iArr[0];
        int i3 = iArr[iArr.length - 1];
        if (iArr.length <= 2) {
            return null;
        }
        Vector vector = new Vector(5);
        while (i < iArr.length - 2) {
            int length = iArr.length - 1;
            int i4 = i + 1;
            for (int i5 = i4; i5 < iArr.length; i5++) {
                int i6 = i5 - 1;
                if (iArr[i5] != iArr[i6] + 1) {
                    length = i6;
                    break;
                }
            }
            if (length - i > 2) {
                vector.appendElement(new IntRange(iArr[i], iArr[length]));
            }
            i = i4;
        }
        return vector;
    }

    public void growToInclude(int i) {
        long[] jArr = new long[Math.max(this.bits.length << 1, numWordsToHold(i))];
        long[] jArr2 = this.bits;
        System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
        this.bits = jArr;
    }

    public boolean member(int i) {
        int iWordNumber = wordNumber(i);
        long[] jArr = this.bits;
        return iWordNumber < jArr.length && (jArr[iWordNumber] & bitMask(i)) != 0;
    }

    public boolean nil() {
        for (int length = this.bits.length - 1; length >= 0; length--) {
            if (this.bits[length] != 0) {
                return false;
            }
        }
        return true;
    }

    public BitSet not() {
        BitSet bitSet = (BitSet) clone();
        bitSet.notInPlace();
        return bitSet;
    }

    public void notInPlace() {
        for (int length = this.bits.length - 1; length >= 0; length--) {
            long[] jArr = this.bits;
            jArr[length] = ~jArr[length];
        }
    }

    public void notInPlace(int i) {
        notInPlace(0, i);
    }

    public void notInPlace(int i, int i2) {
        growToInclude(i2);
        while (i <= i2) {
            int iWordNumber = wordNumber(i);
            long[] jArr = this.bits;
            jArr[iWordNumber] = jArr[iWordNumber] ^ bitMask(i);
            i++;
        }
    }

    public static BitSet of(int i) {
        BitSet bitSet = new BitSet(i + 1);
        bitSet.add(i);
        return bitSet;
    }

    public BitSet or(BitSet bitSet) {
        BitSet bitSet2 = (BitSet) clone();
        bitSet2.orInPlace(bitSet);
        return bitSet2;
    }

    public void orInPlace(BitSet bitSet) {
        long[] jArr = bitSet.bits;
        if (jArr.length > this.bits.length) {
            setSize(jArr.length);
        }
        for (int iMin = Math.min(this.bits.length, bitSet.bits.length) - 1; iMin >= 0; iMin--) {
            long[] jArr2 = this.bits;
            jArr2[iMin] = jArr2[iMin] | bitSet.bits[iMin];
        }
    }

    public void remove(int i) {
        int iWordNumber = wordNumber(i);
        if (iWordNumber >= this.bits.length) {
            growToInclude(i);
        }
        long[] jArr = this.bits;
        jArr[iWordNumber] = jArr[iWordNumber] & (~bitMask(i));
    }

    private void setSize(int i) {
        long[] jArr = new long[i];
        System.arraycopy(this.bits, 0, jArr, 0, Math.min(i, this.bits.length));
        this.bits = jArr;
    }

    public int size() {
        return this.bits.length << 6;
    }

    public int lengthInLongWords() {
        return this.bits.length;
    }

    public boolean subset(BitSet bitSet) {
        if (bitSet == null || !(bitSet instanceof BitSet)) {
            return false;
        }
        return and(bitSet).equals(this);
    }

    public void subtractInPlace(BitSet bitSet) {
        if (bitSet == null) {
            return;
        }
        int i = 0;
        while (true) {
            long[] jArr = this.bits;
            if (i >= jArr.length) {
                return;
            }
            long[] jArr2 = bitSet.bits;
            if (i >= jArr2.length) {
                return;
            }
            jArr[i] = jArr[i] & (~jArr2[i]);
            i++;
        }
    }

    public int[] toArray() {
        int[] iArr = new int[degree()];
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
        return toString(",");
    }

    public String toString(String str) {
        String string = "";
        for (int i = 0; i < (this.bits.length << 6); i++) {
            if (member(i)) {
                if (string.length() > 0) {
                    string = new StringBuffer().append(string).append(str).toString();
                }
                string = new StringBuffer().append(string).append(i).toString();
            }
        }
        return string;
    }

    public String toString(String str, CharFormatter charFormatter) {
        String string = "";
        for (int i = 0; i < (this.bits.length << 6); i++) {
            if (member(i)) {
                if (string.length() > 0) {
                    string = new StringBuffer().append(string).append(str).toString();
                }
                string = new StringBuffer().append(string).append(charFormatter.literalChar(i)).toString();
            }
        }
        return string;
    }

    public String toString(String str, Vector vector) {
        if (vector == null) {
            return toString(str);
        }
        String string = "";
        for (int i = 0; i < (this.bits.length << 6); i++) {
            if (member(i)) {
                if (string.length() > 0) {
                    string = new StringBuffer().append(string).append(str).toString();
                }
                if (i >= vector.size()) {
                    string = new StringBuffer().append(string).append("<bad element ").append(i).append(">").toString();
                } else if (vector.elementAt(i) == null) {
                    string = new StringBuffer().append(string).append("<").append(i).append(">").toString();
                } else {
                    string = new StringBuffer().append(string).append((String) vector.elementAt(i)).toString();
                }
            }
        }
        return string;
    }

    public String toStringOfHalfWords() {
        String str = new String();
        for (int i = 0; i < this.bits.length; i++) {
            if (i != 0) {
                str = new StringBuffer().append(str).append(", ").toString();
            }
            String string = new StringBuffer().append(new StringBuffer().append(str).append(this.bits[i] & 4294967295L).append("UL").toString()).append(", ").toString();
            str = new StringBuffer().append(string).append((this.bits[i] >>> 32) & 4294967295L).append("UL").toString();
        }
        return str;
    }

    public String toStringOfWords() {
        String str = new String();
        for (int i = 0; i < this.bits.length; i++) {
            if (i != 0) {
                str = new StringBuffer().append(str).append(", ").toString();
            }
            str = new StringBuffer().append(str).append(this.bits[i]).append("L").toString();
        }
        return str;
    }

    public String toStringWithRanges(String str, CharFormatter charFormatter) {
        int[] array = toArray();
        String string = "";
        if (array.length == 0) {
            return "";
        }
        int i = 0;
        while (i < array.length) {
            int i2 = 0;
            for (int i3 = i + 1; i3 < array.length && array[i3] == array[i3 - 1] + 1; i3++) {
                i2 = i3;
            }
            if (string.length() > 0) {
                string = new StringBuffer().append(string).append(str).toString();
            }
            if (i2 - i >= 2) {
                string = new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(string).append(charFormatter.literalChar(array[i])).toString()).append("..").toString()).append(charFormatter.literalChar(array[i2])).toString();
                i = i2;
            } else {
                string = new StringBuffer().append(string).append(charFormatter.literalChar(array[i])).toString();
            }
            i++;
        }
        return string;
    }
}
