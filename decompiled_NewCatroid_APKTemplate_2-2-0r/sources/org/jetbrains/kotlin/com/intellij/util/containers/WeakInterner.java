package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public class WeakInterner<T> extends Interner<T> {
    private final ConcurrentMap<T, T> myMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "name";
        } else if (i != 2) {
            objArr[0] = "strategy";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/WeakInterner";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/WeakInterner";
        } else {
            objArr[1] = "intern";
        }
        if (i == 1) {
            objArr[2] = "intern";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Deprecated
    public WeakInterner() {
        this.myMap = CollectionFactory.createConcurrentWeakKeyWeakValueMap();
    }

    public WeakInterner(HashingStrategy<? super T> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
        this.myMap = CollectionFactory.createConcurrentWeakKeyWeakValueMap(hashingStrategy);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public T intern(T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        T tPutIfAbsent = this.myMap.putIfAbsent(t, t);
        if (tPutIfAbsent != null) {
            t = tPutIfAbsent;
        }
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public void clear() {
        this.myMap.clear();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public Set<T> getValues() {
        return new HashSet(this.myMap.values());
    }
}
