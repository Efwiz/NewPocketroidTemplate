package org.jetbrains.kotlin.com.google.common.collect;

import java.util.SortedSet;

/* JADX INFO: loaded from: classes3.dex */
interface SortedMultisetBridge<E> extends Multiset<E> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    SortedSet<E> elementSet();
}
