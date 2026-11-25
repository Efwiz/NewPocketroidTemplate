package org.jetbrains.kotlin.fir.analysis.cfa;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirCallsEffectAnalyzer$LambdaInvocationInfo$constructor$1 extends FunctionReference implements Function1<PersistentMap<FirBasedSymbol<?>, ? extends EventOccurrencesRange>, FirCallsEffectAnalyzer.LambdaInvocationInfo> {
    public static final FirCallsEffectAnalyzer$LambdaInvocationInfo$constructor$1 INSTANCE = new FirCallsEffectAnalyzer$LambdaInvocationInfo$constructor$1();

    FirCallsEffectAnalyzer$LambdaInvocationInfo$constructor$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lkotlinx/collections/immutable/PersistentMap;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirCallsEffectAnalyzer.LambdaInvocationInfo.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final FirCallsEffectAnalyzer.LambdaInvocationInfo invoke(PersistentMap<FirBasedSymbol<?>, ? extends EventOccurrencesRange> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new FirCallsEffectAnalyzer.LambdaInvocationInfo(p0);
    }
}
