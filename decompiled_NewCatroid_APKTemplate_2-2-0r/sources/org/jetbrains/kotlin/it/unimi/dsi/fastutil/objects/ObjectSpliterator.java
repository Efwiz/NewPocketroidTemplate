package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Spliterator;

/* JADX INFO: loaded from: classes5.dex */
public interface ObjectSpliterator<K> extends Spliterator<K> {
    @Override // java.util.Spliterator
    ObjectSpliterator<K> trySplit();
}
