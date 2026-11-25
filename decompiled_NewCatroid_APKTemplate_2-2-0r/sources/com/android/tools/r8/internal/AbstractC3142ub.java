package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ub, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3142ub {
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.Spliterator$OfInt] */
    static Spliterator a(int i, int i2, IntFunction intFunction, Comparator comparator) {
        if (comparator != null) {
            AbstractC3123uM.a((i2 & 4) != 0);
        }
        return new C2861qb(IntStream.range(0, i).spliterator(), intFunction, i2, comparator);
    }

    static Spliterator a(Spliterator spliterator, Function function) {
        int i = AbstractC3123uM.a;
        spliterator.getClass();
        function.getClass();
        return new C2713ob(spliterator, function);
    }

    static Spliterator a(Spliterator spliterator, Function function, int i, long j) {
        AbstractC3123uM.a("flatMap does not support SUBSIZED characteristic", (i & 16384) == 0);
        AbstractC3123uM.a("flatMap does not support SORTED characteristic", (i & 4) == 0);
        spliterator.getClass();
        function.getClass();
        return new C3071tb(null, spliterator, function, i, j);
    }
}
