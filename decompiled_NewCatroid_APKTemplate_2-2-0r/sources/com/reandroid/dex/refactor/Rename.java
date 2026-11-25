package com.reandroid.dex.refactor;

import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.KeyPair;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Rename<T extends Key, R extends Key> {
    private final Set<KeyPair<T, R>> keyPairSet = new HashSet();

    public abstract int apply(DexClassRepository dexClassRepository);

    public void add(T t, R r) {
        add(new KeyPair<>(t, r));
    }

    public void add(KeyPair<T, R> keyPair) {
        addToSet(keyPair);
    }

    public void addAll(Collection<KeyPair<T, R>> collection) {
        addAll(collection.iterator());
    }

    public void addAll(Iterator<KeyPair<T, R>> it) {
        while (it.hasNext()) {
            addToSet(it.next());
        }
    }

    private void addToSet(KeyPair<T, R> keyPair) {
        if (keyPair == null || !keyPair.isValid()) {
            return;
        }
        this.keyPairSet.add(keyPair);
    }

    public int size() {
        return this.keyPairSet.size();
    }

    public List<KeyPair<T, R>> sortedList() {
        ArrayCollection arrayCollection = new ArrayCollection(this.keyPairSet);
        arrayCollection.sort(CompareUtil.getComparableComparator());
        return arrayCollection;
    }

    public Set<KeyPair<T, R>> getKeyPairSet() {
        return this.keyPairSet;
    }

    public String toString() {
        return StringsUtil.join((Iterable<?>) sortedList(), (Object) '\n');
    }
}
