package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESEffect;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"isReturns", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESReturns;", "Lkotlin/ExtensionFunctionType;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EffectsKt {
    public static final boolean isReturns(ESEffect eSEffect, Function1<? super ESReturns, Boolean> block) {
        Intrinsics.checkNotNullParameter(eSEffect, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        return (eSEffect instanceof ESReturns) && block.invoke(eSEffect).booleanValue();
    }
}
