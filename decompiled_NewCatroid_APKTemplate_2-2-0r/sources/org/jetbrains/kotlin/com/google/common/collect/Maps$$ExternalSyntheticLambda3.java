package org.jetbrains.kotlin.com.google.common.collect;

import java.util.function.BinaryOperator;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Maps$$ExternalSyntheticLambda3 implements BinaryOperator {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        return ((Maps.Accumulator) obj).combine((Maps.Accumulator) obj2);
    }
}
