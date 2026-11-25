package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class UnsignedShortArrayList implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private char[] myData;
    private int mySize;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/UnsignedShortArrayList";
        } else {
            objArr[0] = "a";
        }
        if (i != 1) {
            objArr[1] = "toArray";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/UnsignedShortArrayList";
        }
        if (i == 1) {
            objArr[2] = "toArray";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    private static void assertShort(int i) {
    }

    public UnsignedShortArrayList(int i) {
        this.myData = new char[i];
    }

    public UnsignedShortArrayList() {
        this(10);
    }

    public void trimToSize() {
        int i = this.mySize;
        char[] cArr = this.myData;
        if (i < cArr.length) {
            this.myData = ArrayUtil.realloc(cArr, i);
        }
    }

    public void ensureCapacity(int i) {
        char[] cArr = this.myData;
        int length = cArr.length;
        if (i > length) {
            int i2 = ((length * 3) / 2) + 1;
            if (i2 >= i) {
                i = i2;
            }
            char[] cArr2 = new char[i];
            this.myData = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, this.mySize);
        }
    }

    public int size() {
        return this.mySize;
    }

    public Object clone() {
        try {
            UnsignedShortArrayList unsignedShortArrayList = (UnsignedShortArrayList) super.clone();
            unsignedShortArrayList.myData = (char[]) this.myData.clone();
            return unsignedShortArrayList;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    public int[] toArray() {
        int[] array = toArray(0, this.mySize);
        if (array == null) {
            $$$reportNull$$$0(0);
        }
        return array;
    }

    public int[] toArray(int i, int i2) {
        int[] iArr = new int[i2];
        for (int i3 = i; i3 < i2; i3++) {
            iArr[i3 - i] = this.myData[i3];
        }
        return iArr;
    }

    public int getQuick(int i) {
        return this.myData[i];
    }

    public void setQuick(int i, int i2) {
        assertShort(i2);
        this.myData[i] = (char) i2;
    }

    public void add(int i) {
        ensureCapacity(this.mySize + 1);
        int i2 = this.mySize;
        this.mySize = i2 + 1;
        setQuick(i2, i);
    }

    public String toString() {
        return Arrays.toString(toArray());
    }
}
