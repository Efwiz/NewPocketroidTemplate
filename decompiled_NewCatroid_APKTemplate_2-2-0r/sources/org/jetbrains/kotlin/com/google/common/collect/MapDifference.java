package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Map;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use Maps.difference")
public interface MapDifference<K, V> {

    @DoNotMock("Use Maps.difference")
    public interface ValueDifference<V> {
        boolean equals(Object obj);

        int hashCode();

        V leftValue();

        V rightValue();
    }

    boolean areEqual();

    Map<K, ValueDifference<V>> entriesDiffering();

    Map<K, V> entriesInCommon();

    Map<K, V> entriesOnlyOnLeft();

    Map<K, V> entriesOnlyOnRight();

    boolean equals(Object obj);

    int hashCode();
}
