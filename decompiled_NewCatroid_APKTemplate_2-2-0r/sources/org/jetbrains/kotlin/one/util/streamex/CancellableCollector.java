package org.jetbrains.kotlin.one.util.streamex;

import java.util.function.Predicate;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes2.dex */
abstract class CancellableCollector<T, A, R> implements Collector<T, A, R> {
    abstract Predicate<A> finished();

    CancellableCollector() {
    }
}
