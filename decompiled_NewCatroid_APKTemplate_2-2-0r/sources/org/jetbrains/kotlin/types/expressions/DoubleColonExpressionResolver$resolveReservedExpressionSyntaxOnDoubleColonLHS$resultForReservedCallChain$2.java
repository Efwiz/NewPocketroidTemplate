package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedCallChain$2 extends FunctionReference implements Function2<KtExpression, ExpressionTypingContext, DoubleColonLHS.Expression> {
    DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedCallChain$2(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "resolveReservedCallChainOnLHS(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;)Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Expression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "resolveReservedCallChainOnLHS";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(DoubleColonExpressionResolver.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final DoubleColonLHS.Expression invoke(KtExpression p0, ExpressionTypingContext p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((DoubleColonExpressionResolver) this.receiver).resolveReservedCallChainOnLHS(p0, p1);
    }
}
