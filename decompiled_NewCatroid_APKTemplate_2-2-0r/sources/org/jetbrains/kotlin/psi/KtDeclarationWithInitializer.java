package org.jetbrains.kotlin.psi;

/* JADX INFO: loaded from: classes6.dex */
public interface KtDeclarationWithInitializer extends KtDeclaration {
    KtExpression getInitializer();

    boolean hasInitializer();
}
