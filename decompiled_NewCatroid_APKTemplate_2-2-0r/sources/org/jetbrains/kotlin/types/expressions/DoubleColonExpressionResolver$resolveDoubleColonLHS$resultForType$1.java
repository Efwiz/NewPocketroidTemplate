package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$1 extends FunctionReference implements Function1<KtDoubleColonExpression, Boolean> {
    DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "shouldTryResolveLHSAsType(Lorg/jetbrains/kotlin/psi/KtDoubleColonExpression;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "shouldTryResolveLHSAsType";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(DoubleColonExpressionResolver.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(KtDoubleColonExpression p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(((DoubleColonExpressionResolver) this.receiver).shouldTryResolveLHSAsType(p0));
    }
}
