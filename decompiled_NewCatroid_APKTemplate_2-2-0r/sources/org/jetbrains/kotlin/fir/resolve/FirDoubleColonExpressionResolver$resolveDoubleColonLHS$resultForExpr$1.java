package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;

/* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$1 extends FunctionReference implements Function1<FirCallableReferenceAccess, Boolean> {
    FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "shouldTryResolveLHSAsExpression(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "shouldTryResolveLHSAsExpression";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirDoubleColonExpressionResolver.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(FirCallableReferenceAccess p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(((FirDoubleColonExpressionResolver) this.receiver).shouldTryResolveLHSAsExpression(p0));
    }
}
