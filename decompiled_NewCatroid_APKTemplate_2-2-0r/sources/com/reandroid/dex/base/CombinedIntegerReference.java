package com.reandroid.dex.base;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.utils.collection.ArrayCollection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes7.dex */
public class CombinedIntegerReference implements IntegerReference, Iterable<IntegerReference>, BlockRefresh {
    private final ArrayCollection<IntegerReference> references = new ArrayCollection<>();

    public void add(IntegerReference integerReference) {
        if (integerReference == this) {
            throw new RuntimeException("Cyclic add reference");
        }
        if (integerReference == null || this.references.containsFast(integerReference)) {
            return;
        }
        this.references.add(integerReference);
    }

    public void remove(IntegerReference integerReference) {
        this.references.remove(integerReference);
    }

    public void clear() {
        this.references.clear();
    }

    public int size() {
        return this.references.size();
    }

    public boolean isEmpty() {
        return this.references.isEmpty();
    }

    public boolean contains(IntegerReference integerReference) {
        return this.references.contains(integerReference);
    }

    @Override // java.lang.Iterable
    public Iterator<IntegerReference> iterator() {
        return this.references.iterator();
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        IntegerReference first = this.references.getFirst();
        if (first != null) {
            return first.get();
        }
        return 0;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        Iterator<IntegerReference> it = iterator();
        while (it.hasNext()) {
            it.next().set(i);
        }
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        set(get());
    }

    public String toString() {
        return "size = " + size() + ", value = " + get();
    }
}
