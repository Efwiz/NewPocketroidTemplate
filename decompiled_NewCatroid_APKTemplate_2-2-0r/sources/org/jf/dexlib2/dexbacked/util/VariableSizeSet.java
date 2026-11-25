package org.jf.dexlib2.dexbacked.util;

import java.util.AbstractSet;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;

/* JADX INFO: loaded from: classes6.dex */
public abstract class VariableSizeSet<T> extends AbstractSet<T> {
    private final DexBuffer buffer;
    private final int offset;
    private final int size;

    protected abstract T readNextItem(DexReader<?> dexReader, int i);

    public VariableSizeSet(DexBuffer dexBuffer, int i, int i2) {
        this.buffer = dexBuffer;
        this.offset = i;
        this.size = i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public VariableSizeIterator<T> iterator() {
        return new VariableSizeIterator<T>(this.buffer, this.offset, this.size) { // from class: org.jf.dexlib2.dexbacked.util.VariableSizeSet.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeIterator
            protected T readNextItem(DexReader<?> dexReader, int i) {
                return (T) VariableSizeSet.this.readNextItem(dexReader, i);
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }
}
