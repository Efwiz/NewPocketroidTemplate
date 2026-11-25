package com.reandroid.arsc.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.common.ArraySupplier;
import com.reandroid.utils.NumbersUtil;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public abstract class BlockArray<T extends Block> extends BlockList<T> implements Creator<T>, ArraySupplier<T> {
    @Override // com.reandroid.common.ArraySupplier
    public /* bridge */ /* synthetic */ Object get(int i) {
        return super.get(i);
    }

    public BlockArray() {
        setCreator(this);
    }

    public BlockArray(Creator<? extends T> creator) {
        super(creator);
    }

    @Override // com.reandroid.arsc.base.Block
    protected void onReadBytes(BlockReader blockReader) throws IOException {
        super.readChildes(blockReader);
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void setSize(int i) {
        int size = size();
        for (int i2 = i; i2 < size; i2++) {
            onPreRemove(get(i2));
        }
        super.setSize(i);
    }

    public void removeAllNull(int i) {
        int size = size() - i;
        if (size <= 0) {
            return;
        }
        setSize(size() - NumbersUtil.min(size, countFromLast(nullPredicate())));
    }

    public Iterable<T> listItems() {
        return listItems(false);
    }

    public Iterable<T> listItems(final boolean z) {
        return new Iterable() { // from class: com.reandroid.arsc.base.BlockArray$$ExternalSyntheticLambda0
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(z);
            }
        };
    }

    public final int countNonNull() {
        return countIf(nonNullPredicate());
    }

    public int indexOf(Object obj) {
        Iterator<T> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            if (obj == it.next()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(Object obj) {
        Iterator<T> it = iterator();
        int i = -1;
        int i2 = 0;
        while (it.hasNext()) {
            if (obj == it.next()) {
                i = i2;
            }
            i2++;
        }
        return i;
    }

    /* JADX INFO: renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public Iterator<T> m2755lambda$listItems$0$comreandroidarscbaseBlockArray(boolean z) {
        if (z) {
            return iterator(nonNullPredicate());
        }
        return super.iterator();
    }

    public Iterator<T> iterator(boolean z, int i, int i2) {
        Iterator<T> it = super.iterator(i, i2);
        return z ? FilterIterator.of(it, nonNullPredicate()) : it;
    }

    public void clear() {
        super.clearChildes();
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void onPreRemove(T t) {
        t.setParent(null);
        t.setIndex(-1);
    }

    public void trimLastIf(Predicate<? super T> predicate) {
        int size = size() - countFromLast(predicate);
        if (size != size()) {
            setSize(size);
        }
    }

    public void removeNullBlocks() {
        removeIf(nullPredicate());
    }

    private Predicate<? super T> nullPredicate() {
        return new Predicate() { // from class: com.reandroid.arsc.base.BlockArray$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Block) obj).isNull();
            }
        };
    }

    static /* synthetic */ boolean lambda$nonNullPredicate$1(Block block) {
        return !block.isNull();
    }

    private Predicate<? super T> nonNullPredicate() {
        return new Predicate() { // from class: com.reandroid.arsc.base.BlockArray$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BlockArray.lambda$nonNullPredicate$1((Block) obj);
            }
        };
    }
}
