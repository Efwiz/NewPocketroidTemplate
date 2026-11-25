package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface ConcurrentList<E> extends List<E> {
    boolean addIfAbsent(E e);
}
