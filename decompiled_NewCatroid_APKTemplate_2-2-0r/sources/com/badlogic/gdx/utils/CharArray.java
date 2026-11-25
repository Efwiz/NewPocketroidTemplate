package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;
import okhttp3.HttpUrl;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/CharArray.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CharArray {
    public char[] items;
    public boolean ordered;
    public int size;

    public CharArray() {
        this(true, 16);
    }

    public CharArray(int i) {
        this(true, i);
    }

    public CharArray(boolean z, int i) {
        this.ordered = z;
        this.items = new char[i];
    }

    public CharArray(CharArray charArray) {
        this.ordered = charArray.ordered;
        int i = charArray.size;
        this.size = i;
        char[] cArr = new char[i];
        this.items = cArr;
        System.arraycopy(charArray.items, 0, cArr, 0, i);
    }

    public CharArray(char[] cArr) {
        this(true, cArr, 0, cArr.length);
    }

    public CharArray(boolean z, char[] cArr, int i, int i2) {
        this(z, i2);
        this.size = i2;
        System.arraycopy(cArr, i, this.items, 0, i2);
    }

    public void add(char c) {
        char[] cArrResize = this.items;
        int i = this.size;
        if (i == cArrResize.length) {
            cArrResize = resize(Math.max(8, (int) (i * 1.75f)));
        }
        int i2 = this.size;
        this.size = i2 + 1;
        cArrResize[i2] = c;
    }

    public void add(char c, char c2) {
        char[] cArrResize = this.items;
        int i = this.size;
        if (i + 1 >= cArrResize.length) {
            cArrResize = resize(Math.max(8, (int) (i * 1.75f)));
        }
        int i2 = this.size;
        cArrResize[i2] = c;
        cArrResize[i2 + 1] = c2;
        this.size = i2 + 2;
    }

    public void add(char c, char c2, char c3) {
        char[] cArrResize = this.items;
        int i = this.size;
        if (i + 2 >= cArrResize.length) {
            cArrResize = resize(Math.max(8, (int) (i * 1.75f)));
        }
        int i2 = this.size;
        cArrResize[i2] = c;
        cArrResize[i2 + 1] = c2;
        cArrResize[i2 + 2] = c3;
        this.size = i2 + 3;
    }

    public void add(char c, char c2, char c3, char c4) {
        char[] cArrResize = this.items;
        int i = this.size;
        if (i + 3 >= cArrResize.length) {
            cArrResize = resize(Math.max(8, (int) (i * 1.8f)));
        }
        int i2 = this.size;
        cArrResize[i2] = c;
        cArrResize[i2 + 1] = c2;
        cArrResize[i2 + 2] = c3;
        cArrResize[i2 + 3] = c4;
        this.size = i2 + 4;
    }

    public void addAll(CharArray charArray) {
        addAll(charArray.items, 0, charArray.size);
    }

    public void addAll(CharArray charArray, int i, int i2) {
        if (i + i2 > charArray.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + i + " + " + i2 + " <= " + charArray.size);
        }
        addAll(charArray.items, i, i2);
    }

    public void addAll(char... cArr) {
        addAll(cArr, 0, cArr.length);
    }

    public void addAll(char[] cArr, int i, int i2) {
        char[] cArrResize = this.items;
        int i3 = this.size + i2;
        if (i3 > cArrResize.length) {
            cArrResize = resize(Math.max(Math.max(8, i3), (int) (this.size * 1.75f)));
        }
        System.arraycopy(cArr, i, cArrResize, this.size, i2);
        this.size += i2;
    }

    public char get(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.size);
        }
        return this.items[i];
    }

    public void set(int i, char c) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.size);
        }
        this.items[i] = c;
    }

    public void incr(int i, char c) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.size);
        }
        char[] cArr = this.items;
        cArr[i] = (char) (cArr[i] + c);
    }

    public void incr(char c) {
        char[] cArr = this.items;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) (cArr[i2] + c);
        }
    }

    public void mul(int i, char c) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.size);
        }
        char[] cArr = this.items;
        cArr[i] = (char) (cArr[i] * c);
    }

    public void mul(char c) {
        char[] cArr = this.items;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) (cArr[i2] * c);
        }
    }

    public void insert(int i, char c) {
        int i2 = this.size;
        if (i > i2) {
            throw new IndexOutOfBoundsException("index can't be > size: " + i + " > " + this.size);
        }
        char[] cArrResize = this.items;
        if (i2 == cArrResize.length) {
            cArrResize = resize(Math.max(8, (int) (i2 * 1.75f)));
        }
        if (this.ordered) {
            System.arraycopy(cArrResize, i, cArrResize, i + 1, this.size - i);
        } else {
            cArrResize[this.size] = cArrResize[i];
        }
        this.size++;
        cArrResize[i] = c;
    }

    public void insertRange(int i, int i2) {
        int i3 = this.size;
        if (i > i3) {
            throw new IndexOutOfBoundsException("index can't be > size: " + i + " > " + this.size);
        }
        int i4 = i3 + i2;
        if (i4 > this.items.length) {
            this.items = resize(Math.max(Math.max(8, i4), (int) (this.size * 1.75f)));
        }
        char[] cArr = this.items;
        System.arraycopy(cArr, i, cArr, i2 + i, this.size - i);
        this.size = i4;
    }

    public void swap(int i, int i2) {
        int i3 = this.size;
        if (i >= i3) {
            throw new IndexOutOfBoundsException("first can't be >= size: " + i + " >= " + this.size);
        }
        if (i2 >= i3) {
            throw new IndexOutOfBoundsException("second can't be >= size: " + i2 + " >= " + this.size);
        }
        char[] cArr = this.items;
        char c = cArr[i];
        cArr[i] = cArr[i2];
        cArr[i2] = c;
    }

    public boolean contains(char c) {
        int i = this.size - 1;
        char[] cArr = this.items;
        while (i >= 0) {
            int i2 = i - 1;
            if (cArr[i] == c) {
                return true;
            }
            i = i2;
        }
        return false;
    }

    public int indexOf(char c) {
        char[] cArr = this.items;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (cArr[i2] == c) {
                return i2;
            }
        }
        return -1;
    }

    public int lastIndexOf(char c) {
        char[] cArr = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            if (cArr[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeValue(char c) {
        char[] cArr = this.items;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (cArr[i2] == c) {
                removeIndex(i2);
                return true;
            }
        }
        return false;
    }

    public char removeIndex(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.size);
        }
        char[] cArr = this.items;
        char c = cArr[i];
        int i3 = i2 - 1;
        this.size = i3;
        if (this.ordered) {
            System.arraycopy(cArr, i + 1, cArr, i, i3 - i);
        } else {
            cArr[i] = cArr[i3];
        }
        return c;
    }

    public void removeRange(int i, int i2) {
        int i3 = this.size;
        if (i2 >= i3) {
            throw new IndexOutOfBoundsException("end can't be >= size: " + i2 + " >= " + this.size);
        }
        if (i > i2) {
            throw new IndexOutOfBoundsException("start can't be > end: " + i + " > " + i2);
        }
        int i4 = (i2 - i) + 1;
        int i5 = i3 - i4;
        if (this.ordered) {
            char[] cArr = this.items;
            int i6 = i4 + i;
            System.arraycopy(cArr, i6, cArr, i, i3 - i6);
        } else {
            int iMax = Math.max(i5, i2 + 1);
            char[] cArr2 = this.items;
            System.arraycopy(cArr2, iMax, cArr2, i, i3 - iMax);
        }
        this.size = i5;
    }

    public boolean removeAll(CharArray charArray) {
        int i = this.size;
        char[] cArr = this.items;
        int i2 = charArray.size;
        int i3 = i;
        for (int i4 = 0; i4 < i2; i4++) {
            char c = charArray.get(i4);
            for (int i5 = 0; i5 < i3; i5++) {
                if (c == cArr[i5]) {
                    removeIndex(i5);
                    i3--;
                    break;
                }
            }
        }
        return i3 != i;
    }

    public char pop() {
        char[] cArr = this.items;
        int i = this.size - 1;
        this.size = i;
        return cArr[i];
    }

    public char peek() {
        return this.items[this.size - 1];
    }

    public char first() {
        if (this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }
        return this.items[0];
    }

    public boolean notEmpty() {
        return this.size > 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.size = 0;
    }

    public char[] shrink() {
        int length = this.items.length;
        int i = this.size;
        if (length != i) {
            resize(i);
        }
        return this.items;
    }

    public char[] ensureCapacity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("additionalCapacity must be >= 0: " + i);
        }
        int i2 = this.size + i;
        if (i2 > this.items.length) {
            resize(Math.max(Math.max(8, i2), (int) (this.size * 1.75f)));
        }
        return this.items;
    }

    public char[] setSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("newSize must be >= 0: " + i);
        }
        if (i > this.items.length) {
            resize(Math.max(8, i));
        }
        this.size = i;
        return this.items;
    }

    protected char[] resize(int i) {
        char[] cArr = new char[i];
        System.arraycopy(this.items, 0, cArr, 0, Math.min(this.size, i));
        this.items = cArr;
        return cArr;
    }

    public void sort() {
        Arrays.sort(this.items, 0, this.size);
    }

    public void reverse() {
        char[] cArr = this.items;
        int i = this.size;
        int i2 = i - 1;
        int i3 = i / 2;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i2 - i4;
            char c = cArr[i4];
            cArr[i4] = cArr[i5];
            cArr[i5] = c;
        }
    }

    public void shuffle() {
        char[] cArr = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            int iRandom = MathUtils.random(i);
            char c = cArr[i];
            cArr[i] = cArr[iRandom];
            cArr[iRandom] = c;
        }
    }

    public void truncate(int i) {
        if (this.size > i) {
            this.size = i;
        }
    }

    public char random() {
        int i = this.size;
        if (i == 0) {
            return (char) 0;
        }
        return this.items[MathUtils.random(0, i - 1)];
    }

    public char[] toArray() {
        int i = this.size;
        char[] cArr = new char[i];
        System.arraycopy(this.items, 0, cArr, 0, i);
        return cArr;
    }

    public int hashCode() {
        if (!this.ordered) {
            return super.hashCode();
        }
        char[] cArr = this.items;
        int i = this.size;
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 * 31) + cArr[i3];
        }
        return i2;
    }

    public boolean equals(Object obj) {
        int i;
        if (obj == this) {
            return true;
        }
        if (!this.ordered || !(obj instanceof CharArray)) {
            return false;
        }
        CharArray charArray = (CharArray) obj;
        if (!charArray.ordered || (i = this.size) != charArray.size) {
            return false;
        }
        char[] cArr = this.items;
        char[] cArr2 = charArray.items;
        for (int i2 = 0; i2 < i; i2++) {
            if (cArr[i2] != cArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (this.size == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        char[] cArr = this.items;
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append('[');
        stringBuilder.append(cArr[0]);
        for (int i = 1; i < this.size; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(cArr[i]);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public String toString(String str) {
        if (this.size == 0) {
            return "";
        }
        char[] cArr = this.items;
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(cArr[0]);
        for (int i = 1; i < this.size; i++) {
            stringBuilder.append(str);
            stringBuilder.append(cArr[i]);
        }
        return stringBuilder.toString();
    }

    public static CharArray with(char... cArr) {
        return new CharArray(cArr);
    }
}
