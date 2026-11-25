package org.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import org.apache.xerces.xs.LSInputList;
import org.w3c.dom.ls.LSInput;

/* JADX INFO: loaded from: classes13.dex */
public final class LSInputListImpl extends AbstractList implements LSInputList {
    public static final LSInputListImpl EMPTY_LIST = new LSInputListImpl(new LSInput[0], 0);
    private final LSInput[] fArray;
    private final int fLength;

    public LSInputListImpl(LSInput[] lSInputArr, int i) {
        this.fArray = lSInputArr;
        this.fLength = i;
    }

    private void toArray0(Object[] objArr) {
        int i = this.fLength;
        if (i > 0) {
            System.arraycopy(this.fArray, 0, objArr, 0, i);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        if (i < 0 || i >= this.fLength) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return this.fArray[i];
    }

    @Override // org.apache.xerces.xs.LSInputList
    public int getLength() {
        return this.fLength;
    }

    @Override // org.apache.xerces.xs.LSInputList
    public LSInput item(int i) {
        if (i < 0 || i >= this.fLength) {
            return null;
        }
        return this.fArray[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return getLength();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] objArr = new Object[this.fLength];
        toArray0(objArr);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray(Object[] objArr) {
        if (objArr.length < this.fLength) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.fLength);
        }
        toArray0(objArr);
        int length = objArr.length;
        int i = this.fLength;
        if (length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
