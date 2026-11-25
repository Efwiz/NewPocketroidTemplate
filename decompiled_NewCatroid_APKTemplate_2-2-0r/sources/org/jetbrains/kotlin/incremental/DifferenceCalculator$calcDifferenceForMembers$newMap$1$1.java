package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DifferenceCalculator$calcDifferenceForMembers$newMap$1$1 extends FunctionReference implements Function1<Integer, Integer> {
    DifferenceCalculator$calcDifferenceForMembers$newMap$1$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "newGetIndexOfString(I)I";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "newGetIndexOfString";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ProtoCompareGenerated.class);
    }

    public final Integer invoke(int i) {
        return Integer.valueOf(((ProtoCompareGenerated) this.receiver).newGetIndexOfString(i));
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Integer invoke(Integer num) {
        return invoke(num.intValue());
    }
}
