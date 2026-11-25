package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
interface SortedIterable<T> extends Iterable<T> {
    Comparator<? super T> comparator();

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    Iterator<T> iterator();
}
