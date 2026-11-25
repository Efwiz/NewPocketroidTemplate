package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.data.DataItem;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DataSectionArray<T extends DataItem> extends SectionArray<T> {
    private int mEnd;
    private int mStart;

    public DataSectionArray(IntegerPair integerPair, Creator<T> creator) {
        super(integerPair, creator);
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void onChanged() {
        super.onChanged();
        this.mStart = 0;
    }

    public T getAt(int i) {
        if (i <= 0) {
            return null;
        }
        int i2 = this.mStart;
        if (i2 == 0 || i < i2 || i > this.mEnd) {
            updateBounds();
        }
        return (T) getAt(i, estimateBeginPosition(i));
    }

    public T getAt(int i, int i2) {
        if (i <= 0) {
            return null;
        }
        T t = (T) spiderSearch(i, i2);
        if (t != null) {
            return t;
        }
        T t2 = (T) lazySearch(i);
        if (t2 != null) {
            updateBounds();
        }
        return t2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T[] getAt(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        int length = iArr.length;
        T[] tArrNewArrayInstance = newArrayInstance(iArr.length);
        for (int i = 0; i < length; i++) {
            tArrNewArrayInstance[i] = getAt(iArr[i]);
        }
        return tArrNewArrayInstance;
    }

    @Override // com.reandroid.dex.base.BlockListArray
    public void readChild(BlockReader blockReader, T t) throws IOException {
        t.setPosition(blockReader.getPosition());
        t.onReadBytes(blockReader);
    }

    @Override // com.reandroid.dex.sections.SectionArray
    public void onPreRemove(T t) {
        super.onPreRemove(t);
        IntegerReference offsetReference = t.getOffsetReference();
        if (offsetReference != null) {
            offsetReference.set(0);
        }
    }

    private T lazySearch(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            T t = (T) get(i2);
            if (i == t.getOffset()) {
                return t;
            }
        }
        return null;
    }

    private T spiderSearch(int i, int i2) {
        int i3;
        int i4;
        int size = size();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i5 = 1;
        int i6 = 1;
        while (i2 >= 0 && i2 < size) {
            T t = (T) get(i2);
            int offset = t.getOffset();
            if (offset == i) {
                return t;
            }
            if (i < offset) {
                if (z2) {
                    if (z3) {
                        return null;
                    }
                    z2 = false;
                    z3 = true;
                    i5 = 1;
                } else if (z && i2 == 0) {
                    return null;
                }
                if (!z3 && i5 > (i4 = (i2 / 3) + 1)) {
                    i5 = i4;
                }
                i2 -= i5;
                if (i2 < 0) {
                    i2 = 0;
                }
                z = true;
            } else {
                if (z) {
                    if (z3) {
                        return null;
                    }
                    z = false;
                    z3 = true;
                    i5 = 1;
                }
                if (!z3 && i5 > (i3 = ((size - i2) / 3) + 1)) {
                    i5 = i3;
                }
                i2 += i5;
                z2 = true;
            }
            if (!z3) {
                int i7 = i6 + 1;
                i5 += i7;
                if (i5 % 2 == 1) {
                    i6 = i7;
                }
            }
        }
        return null;
    }

    private int estimateBeginPosition(int i) {
        int i2 = this.mEnd;
        int i3 = this.mStart;
        int i4 = i2 - i3;
        if (i4 == 0) {
            i4 = 1;
        }
        int size = ((int) ((((long) (i - i3)) * ((long) size())) / ((long) i4))) - 1;
        int size2 = size();
        if (size >= size2) {
            size = size2 - 1;
        }
        if (size < 0) {
            return 0;
        }
        return size;
    }

    private void updateBounds() {
        this.mStart = 0;
        T first = getFirst();
        if (first != null) {
            this.mStart = first.getOffset();
        }
        this.mEnd = this.mStart;
        T last = getLast();
        if (last != null) {
            this.mEnd = last.getOffset();
        }
    }
}
