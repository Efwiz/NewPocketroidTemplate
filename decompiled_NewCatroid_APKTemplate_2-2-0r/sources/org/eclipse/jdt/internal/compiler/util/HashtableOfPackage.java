package org.eclipse.jdt.internal.compiler.util;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;

/* JADX INFO: loaded from: classes14.dex */
public final class HashtableOfPackage {
    public int elementSize;
    public char[][] keyTable;
    int threshold;
    public PackageBinding[] valueTable;

    public HashtableOfPackage() {
        this(3);
    }

    public HashtableOfPackage(int i) {
        this.elementSize = 0;
        this.threshold = i;
        int i2 = (int) (i * 1.75f);
        i2 = i == i2 ? i2 + 1 : i2;
        this.keyTable = new char[i2][];
        this.valueTable = new PackageBinding[i2];
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

    public PackageBinding get(char[] cArr) {
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

    public PackageBinding put(char[] cArr, PackageBinding packageBinding) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[][] cArr2 = this.keyTable;
            char[] cArr3 = cArr2[iHashCode];
            if (cArr3 != null) {
                if (cArr3.length == length2 && CharOperation.equals(cArr3, cArr)) {
                    this.valueTable[iHashCode] = packageBinding;
                    return packageBinding;
                }
                iHashCode++;
                if (iHashCode == length) {
                    iHashCode = 0;
                }
            } else {
                cArr2[iHashCode] = cArr;
                this.valueTable[iHashCode] = packageBinding;
                int i = this.elementSize + 1;
                this.elementSize = i;
                if (i > this.threshold) {
                    rehash();
                }
                return packageBinding;
            }
        }
    }

    private void rehash() {
        HashtableOfPackage hashtableOfPackage = new HashtableOfPackage(this.elementSize * 2);
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                char[] cArr = this.keyTable[length];
                if (cArr != null) {
                    hashtableOfPackage.put(cArr, this.valueTable[length]);
                }
            } else {
                this.keyTable = hashtableOfPackage.keyTable;
                this.valueTable = hashtableOfPackage.valueTable;
                this.threshold = hashtableOfPackage.threshold;
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
            PackageBinding packageBinding = this.valueTable[i];
            if (packageBinding != null) {
                str = String.valueOf(str) + packageBinding.toString() + "\n";
            }
        }
        return str;
    }
}
