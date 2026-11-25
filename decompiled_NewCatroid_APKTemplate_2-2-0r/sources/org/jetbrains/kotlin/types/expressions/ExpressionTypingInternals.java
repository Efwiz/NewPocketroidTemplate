package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.ValueArgument;

/* JADX INFO: loaded from: classes6.dex */
interface ExpressionTypingInternals extends ExpressionTypingFacade {
    KotlinTypeInfo checkInExpression(KtElement ktElement, KtSimpleNameExpression ktSimpleNameExpression, ValueArgument valueArgument, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext);

    void checkStatementType(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext);

    ExpressionTypingComponents getComponents();
}
