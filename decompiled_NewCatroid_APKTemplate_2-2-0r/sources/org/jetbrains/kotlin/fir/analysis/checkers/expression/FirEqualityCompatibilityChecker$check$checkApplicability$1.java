package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;

/* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirEqualityCompatibilityChecker$check$checkApplicability$1 extends FunctionReference implements Function3<TypeInfo, TypeInfo, CheckerContext, FirEqualityCompatibilityChecker.Applicability> {
    FirEqualityCompatibilityChecker$check$checkApplicability$1(Object obj) {
        super(3, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "checkEqualityApplicability(Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityCompatibilityChecker$Applicability;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "checkEqualityApplicability";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirEqualityCompatibilityChecker.class);
    }

    @Override // kotlin.jvm.functions.Function3
    public final FirEqualityCompatibilityChecker.Applicability invoke(TypeInfo p0, TypeInfo p1, CheckerContext p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return ((FirEqualityCompatibilityChecker) this.receiver).checkEqualityApplicability(p0, p1, p2);
    }
}
