package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: compiled from: KtSafeQualifiedExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtSafeQualifiedExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionImpl;", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtSafeQualifiedExpression extends KtExpressionImpl implements KtQualifiedExpression {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSafeQualifiedExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSafeQualifiedExpression(this, data);
    }
}
