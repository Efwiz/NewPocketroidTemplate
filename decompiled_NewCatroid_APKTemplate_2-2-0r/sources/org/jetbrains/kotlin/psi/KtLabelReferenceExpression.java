package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtLabelReferenceExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtLabelReferenceExpression;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpressionImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "getReferencedNameElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLabelReferenceExpression extends KtSimpleNameExpressionImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLabelReferenceExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getReferencedNameElement() {
        PsiElement identifier = getIdentifier();
        return identifier == null ? this : identifier;
    }
}
