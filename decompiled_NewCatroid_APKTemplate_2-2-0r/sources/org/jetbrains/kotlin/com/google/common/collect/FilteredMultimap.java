package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Map;
import org.jetbrains.kotlin.com.google.common.base.Predicate;

/* JADX INFO: loaded from: classes3.dex */
interface FilteredMultimap<K, V> extends Multimap<K, V> {
    Predicate<? super Map.Entry<K, V>> entryPredicate();

    Multimap<K, V> unfiltered();
}
