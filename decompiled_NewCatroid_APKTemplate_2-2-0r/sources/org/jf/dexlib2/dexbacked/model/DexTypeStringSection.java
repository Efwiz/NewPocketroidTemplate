package org.jf.dexlib2.dexbacked.model;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.util.collection.ArrayIterator;
import org.jf.util.collection.ComputingList;

/* JADX INFO: loaded from: classes6.dex */
public class DexTypeStringSection extends AbstractList<DexTypeString> implements Function<DexTypeString, String> {
    private final DexTypeString[] array;
    private final List<String> stringList = new ComputingList(this, this);

    public DexTypeStringSection(int i) {
        this.array = new DexTypeString[i];
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    @Override // java.util.AbstractList, java.util.List
    public DexTypeString get(int i) {
        if (i < 0) {
            return null;
        }
        return this.array[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.array.length;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<DexTypeString> iterator() {
        return new ArrayIterator(this.array);
    }

    public void load(DexBuffer dexBuffer, int i, DexStringSection dexStringSection) {
        DexTypeString[] dexTypeStringArr = this.array;
        int length = dexTypeStringArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            dexTypeStringArr[i2] = new DexTypeString(dexStringSection.get(dexBuffer.readSmallUint((i2 * 4) + i)), i2);
        }
    }

    @Override // java.util.function.Function
    public String apply(DexTypeString dexTypeString) {
        if (dexTypeString != null) {
            return dexTypeString.getValue();
        }
        return null;
    }
}
