package org.eclipse.jdt.internal.compiler.util;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public final class HashtableOfType {
    public int elementSize;
    public char[][] keyTable;
    int threshold;
    public ReferenceBinding[] valueTable;

    public HashtableOfType() {
        this(3);
    }

    public HashtableOfType(int i) {
        this.elementSize = 0;
        this.threshold = i;
        int i2 = (int) (i * 1.75f);
        i2 = i == i2 ? i2 + 1 : i2;
        this.keyTable = new char[i2][];
        this.valueTable = new ReferenceBinding[i2];
    }

    public boolean containsKey(char[] cArr) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[] cArr2 = this.keyTable[iHashCode];
            if (cArr2 == null) {
                return false;
            }
            if (cArr2.length == length2 && CharOperation.equals(cArr2, cArr)) {
                return true;
            }
            iHashCode++;
            if (iHashCode == length) {
                iHashCode = 0;
            }
        }
    }

    public ReferenceBinding get(char[] cArr) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[] cArr2 = this.keyTable[iHashCode];
            if (cArr2 == null) {
                return null;
            }
            if (cArr2.length == length2 && CharOperation.equals(cArr2, cArr)) {
                return this.valueTable[iHashCode];
            }
            iHashCode++;
            if (iHashCode == length) {
                iHashCode = 0;
            }
        }
    }

    public ReferenceBinding getput(char[] cArr, ReferenceBinding referenceBinding) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[][] cArr2 = this.keyTable;
            char[] cArr3 = cArr2[iHashCode];
            if (cArr3 != null) {
                if (cArr3.length == length2 && CharOperation.equals(cArr3, cArr)) {
                    ReferenceBinding[] referenceBindingArr = this.valueTable;
                    ReferenceBinding referenceBinding2 = referenceBindingArr[iHashCode];
                    referenceBindingArr[iHashCode] = referenceBinding;
                    return referenceBinding2;
                }
                iHashCode++;
                if (iHashCode == length) {
                    iHashCode = 0;
                }
            } else {
                cArr2[iHashCode] = cArr;
                this.valueTable[iHashCode] = referenceBinding;
                int i = this.elementSize + 1;
                this.elementSize = i;
                if (i <= this.threshold) {
                    return null;
                }
                rehash();
                return null;
            }
        }
    }

    public ReferenceBinding put(char[] cArr, ReferenceBinding referenceBinding) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[][] cArr2 = this.keyTable;
            char[] cArr3 = cArr2[iHashCode];
            if (cArr3 != null) {
                if (cArr3.length == length2 && CharOperation.equals(cArr3, cArr)) {
                    this.valueTable[iHashCode] = referenceBinding;
                    return referenceBinding;
                }
                iHashCode++;
                if (iHashCode == length) {
                    iHashCode = 0;
                }
            } else {
                cArr2[iHashCode] = cArr;
                this.valueTable[iHashCode] = referenceBinding;
                int i = this.elementSize + 1;
                this.elementSize = i;
                if (i > this.threshold) {
                    rehash();
                }
                return referenceBinding;
            }
        }
    }

    private void rehash() {
        int i = this.elementSize;
        HashtableOfType hashtableOfType = new HashtableOfType(i >= 100 ? i * 2 : 100);
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                char[] cArr = this.keyTable[length];
                if (cArr != null) {
                    hashtableOfType.put(cArr, this.valueTable[length]);
                }
            } else {
                this.keyTable = hashtableOfType.keyTable;
                this.valueTable = hashtableOfType.valueTable;
                this.threshold = hashtableOfType.threshold;
                return;
            }
        }
    }

    public int size() {
        return this.elementSize;
    }

    public String toString() {
        int length = this.valueTable.length;
        String str = "";
        for (int i = 0; i < length; i++) {
            ReferenceBinding referenceBinding = this.valueTable[i];
            if (referenceBinding != null) {
                str = String.valueOf(str) + referenceBinding.toString() + "\n";
            }
        }
        return str;
    }
}
