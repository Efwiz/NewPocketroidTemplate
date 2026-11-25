package org.jetbrains.kotlin.psi;

import kotlin.Metadata;

/* JADX INFO: compiled from: KtAnonymousInitializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/psi/KtStatementExpression;", "body", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getBody", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "containingDeclaration", "getContainingDeclaration", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtAnonymousInitializer extends KtDeclaration, KtStatementExpression {
    KtExpression getBody();

    KtDeclaration getContainingDeclaration();
}
