package javaslang.collection;

import java.util.Comparator;

/* JADX INFO: loaded from: classes11.dex */
final class Comparators {
    static <U> Comparator<U> naturalComparator() {
        return NaturalComparator.instance();
    }
}
