package org.jf.dexlib2.dexbacked.util;

import java.util.AbstractSequentialList;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;

/* JADX INFO: loaded from: classes6.dex */
public abstract class VariableSizeList<T> extends AbstractSequentialList<T> {
    private final DexBuffer buffer;
    private final int offset;
    private final int size;

    protected abstract T readNextItem(DexReader dexReader, int i);

    public VariableSizeList(DexBuffer dexBuffer, int i, int i2) {
        this.buffer = dexBuffer;
        this.offset = i;
        this.size = i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public VariableSizeListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public VariableSizeListIterator<T> listIterator(int i) {
        VariableSizeListIterator<T> variableSizeListIterator = new VariableSizeListIterator<T>(this.buffer, this.offset, this.size) { // from class: org.jf.dexlib2.dexbacked.util.VariableSizeList.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
            protected T readNextItem(DexReader dexReader, int i2) {
                return (T) VariableSizeList.this.readNextItem(dexReader, i2);
            }
        };
        for (int i2 = 0; i2 < i; i2++) {
            variableSizeListIterator.next();
        }
        return variableSizeListIterator;
    }
}
