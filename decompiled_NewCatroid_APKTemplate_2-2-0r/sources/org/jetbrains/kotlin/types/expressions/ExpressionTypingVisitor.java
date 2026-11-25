package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.psi.KtVisitor;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ExpressionTypingVisitor extends KtVisitor<KotlinTypeInfo, ExpressionTypingContext> {
    protected final ExpressionTypingComponents components;
    protected final ExpressionTypingInternals facade;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "facade", "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitor", "<init>"));
    }

    protected ExpressionTypingVisitor(ExpressionTypingInternals expressionTypingInternals) {
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(0);
        }
        this.facade = expressionTypingInternals;
        this.components = expressionTypingInternals.getComponents();
    }
}
