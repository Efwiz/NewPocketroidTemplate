package org.apache.xerces.impl.xs;

import java.util.AbstractList;
import org.apache.xerces.xs.StringList;

/* JADX INFO: loaded from: classes13.dex */
final class PSVIErrorList extends AbstractList implements StringList {
    private final String[] fArray;
    private final int fLength;
    private final int fOffset;

    public PSVIErrorList(String[] strArr, boolean z) {
        this.fArray = strArr;
        this.fLength = strArr.length >> 1;
        this.fOffset = !z ? 1 : 0;
    }

    @Override // org.apache.xerces.xs.StringList
    public boolean contains(String str) {
        if (str == null) {
            for (int i = 0; i < this.fLength; i++) {
                if (this.fArray[(i << 1) + this.fOffset] == null) {
                    return true;
                }
            }
        } else {
            for (int i2 = 0; i2 < this.fLength; i2++) {
                if (str.equals(this.fArray[(i2 << 1) + this.fOffset])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        if (i < 0 || i >= this.fLength) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return this.fArray[(i << 1) + this.fOffset];
    }

    @Override // org.apache.xerces.xs.StringList
    public int getLength() {
        return this.fLength;
    }

    @Override // org.apache.xerces.xs.StringList
    public String item(int i) {
        if (i < 0 || i >= this.fLength) {
            return null;
        }
        return this.fArray[(i << 1) + this.fOffset];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return getLength();
    }
}
