package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: UnusedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class UnusedChecker$VariableStatusInfo$constructor$1 extends FunctionReference implements Function1<PersistentMap<FirPropertySymbol, ? extends UnusedChecker.VariableStatus>, UnusedChecker.VariableStatusInfo> {
    public static final UnusedChecker$VariableStatusInfo$constructor$1 INSTANCE = new UnusedChecker$VariableStatusInfo$constructor$1();

    UnusedChecker$VariableStatusInfo$constructor$1() {
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
        return Reflection.getOrCreateKotlinClass(UnusedChecker.VariableStatusInfo.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final UnusedChecker.VariableStatusInfo invoke(PersistentMap<FirPropertySymbol, ? extends UnusedChecker.VariableStatus> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new UnusedChecker.VariableStatusInfo(p0);
    }
}
