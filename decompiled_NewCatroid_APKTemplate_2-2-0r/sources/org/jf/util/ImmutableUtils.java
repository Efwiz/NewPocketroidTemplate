package org.jf.util;

import java.util.List;
import java.util.Set;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.ListUtil;
import org.jf.util.collection.SetUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableUtils {
    public static <T> List<T> nullToEmptyList(List<T> list) {
        return ListUtil.nullToEmptyList(list);
    }

    public static <T> Set<T> nullToEmptySet(Set<T> set) {
        return SetUtil.nullToEmptySet(set);
    }

    public static <T> Set<T> nullToEmptySortedSet(Set<T> set) {
        return set == null ? ArraySet.of() : set;
    }
}
