package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: loaded from: classes6.dex */
public interface ExpressionTypingFacade {
    KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext);

    KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, boolean z);

    KotlinTypeInfo safeGetTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext);
}
