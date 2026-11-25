package org.eclipse.jdt.internal.compiler.util;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public final class HashtableOfObject implements Cloneable {
    public int elementSize;
    public char[][] keyTable;
    int threshold;
    public Object[] valueTable;

    public HashtableOfObject() {
        this(13);
    }

    public HashtableOfObject(int i) {
        this.elementSize = 0;
        this.threshold = i;
        int i2 = (int) (i * 1.75f);
        i2 = i == i2 ? i2 + 1 : i2;
        this.keyTable = new char[i2][];
        this.valueTable = new Object[i2];
    }

    public void clear() {
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                this.keyTable[length] = null;
                this.valueTable[length] = null;
            } else {
                this.elementSize = 0;
                return;
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        HashtableOfObject hashtableOfObject = (HashtableOfObject) super.clone();
        hashtableOfObject.elementSize = this.elementSize;
        hashtableOfObject.threshold = this.threshold;
        int length = this.keyTable.length;
        char[][] cArr = new char[length][];
        hashtableOfObject.keyTable = cArr;
        System.arraycopy(this.keyTable, 0, cArr, 0, length);
        int length2 = this.valueTable.length;
        Object[] objArr = new Object[length2];
        hashtableOfObject.valueTable = objArr;
        System.arraycopy(this.valueTable, 0, objArr, 0, length2);
        return hashtableOfObject;
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

    public Object get(char[] cArr) {
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

    public Object put(char[] cArr, Object obj) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[][] cArr2 = this.keyTable;
            char[] cArr3 = cArr2[iHashCode];
            if (cArr3 != null) {
                if (cArr3.length == length2 && CharOperation.equals(cArr3, cArr)) {
                    this.valueTable[iHashCode] = obj;
                    return obj;
                }
                iHashCode++;
                if (iHashCode == length) {
                    iHashCode = 0;
                }
            } else {
                cArr2[iHashCode] = cArr;
                this.valueTable[iHashCode] = obj;
                int i = this.elementSize + 1;
                this.elementSize = i;
                if (i > this.threshold) {
                    rehash();
                }
                return obj;
            }
        }
    }

    public void putUnsafely(char[] cArr, Object obj) {
        char[][] cArr2;
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        while (true) {
            cArr2 = this.keyTable;
            if (cArr2[iHashCode] == null) {
                break;
            }
            iHashCode++;
            if (iHashCode == length) {
                iHashCode = 0;
            }
        }
        cArr2[iHashCode] = cArr;
        this.valueTable[iHashCode] = obj;
        int i = this.elementSize + 1;
        this.elementSize = i;
        if (i > this.threshold) {
            rehash();
        }
    }

    public Object removeKey(char[] cArr) {
        int length = this.keyTable.length;
        int iHashCode = CharOperation.hashCode(cArr) % length;
        int length2 = cArr.length;
        while (true) {
            char[] cArr2 = this.keyTable[iHashCode];
            if (cArr2 == null) {
                return null;
            }
            if (cArr2.length == length2 && CharOperation.equals(cArr2, cArr)) {
                Object[] objArr = this.valueTable;
                Object obj = objArr[iHashCode];
                this.elementSize--;
                this.keyTable[iHashCode] = null;
                objArr[iHashCode] = null;
                rehash();
                return obj;
            }
            iHashCode++;
            if (iHashCode == length) {
                iHashCode = 0;
            }
        }
    }

    private void rehash() {
        HashtableOfObject hashtableOfObject = new HashtableOfObject(this.elementSize * 2);
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                char[] cArr = this.keyTable[length];
                if (cArr != null) {
                    hashtableOfObject.putUnsafely(cArr, this.valueTable[length]);
                }
            } else {
                this.keyTable = hashtableOfObject.keyTable;
                this.valueTable = hashtableOfObject.valueTable;
                this.threshold = hashtableOfObject.threshold;
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
            Object obj = this.valueTable[i];
            if (obj != null) {
                str = String.valueOf(str) + new String(this.keyTable[i]) + " -> " + obj.toString() + "\n";
            }
        }
        return str;
    }
}
