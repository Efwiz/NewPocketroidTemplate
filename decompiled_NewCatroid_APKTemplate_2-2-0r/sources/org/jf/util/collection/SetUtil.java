package org.jf.util.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public class SetUtil {
    public static <T> Set<T> nullToEmptySet(Set<T> set) {
        return set == null ? of() : set;
    }

    public static <E> HashSet<E> of() {
        return newHashSet(null);
    }

    public static <E> HashSet<E> of(E... eArr) {
        return newHashSet(eArr);
    }

    public static <E> HashSet<E> newHashSet(E... eArr) {
        int length = eArr != null ? eArr.length : 0;
        if (length == 0) {
            length = 1;
        }
        HashSet<E> hashSet = new HashSet<>(length);
        if (eArr != null) {
            Collections.addAll(hashSet, eArr);
        }
        return hashSet;
    }
}
