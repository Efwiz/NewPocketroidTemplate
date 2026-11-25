package com.android.tools.r8.internal;

import java.util.Spliterator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3071tb extends AbstractC3000sb {
    C3071tb(Spliterator spliterator, Spliterator spliterator2, Function function, int i, long j) {
        super(spliterator, spliterator2, function, new InterfaceC2933rb() { // from class: com.android.tools.r8.internal.tb$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2933rb
            public final Spliterator a(Spliterator spliterator3, Spliterator spliterator4, Function function2, int i2, long j2) {
                return new C3071tb(spliterator3, spliterator4, function2, i2, j2);
            }
        }, i, j);
    }
}
