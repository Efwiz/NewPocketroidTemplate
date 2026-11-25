package org.jetbrains.kotlin.psi;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: KtQualifiedExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "operationSign", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "getOperationSign", "()Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "operationTokenNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getOperationTokenNode", "()Lcom/intellij/lang/ASTNode;", "receiverExpression", "getReceiverExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "selectorExpression", "getSelectorExpression", "getExpression", "afterOperation", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtQualifiedExpression extends KtExpression {
    default KtExpression getReceiverExpression() {
        KtExpression expression = getExpression(false);
        if (expression != null) {
            return expression;
        }
        throw new AssertionError("No receiver found: " + PsiUtilsKt.getElementTextWithContext(this));
    }

    default KtExpression getSelectorExpression() {
        return getExpression(true);
    }

    default ASTNode getOperationTokenNode() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.OPERATIONS);
        if (aSTNodeFindChildByType != null) {
            return aSTNodeFindChildByType;
        }
        throw new IllegalStateException(("No operation node for " + getNode().getElementType() + ". Children: " + Arrays.toString(getChildren())).toString());
    }

    default KtSingleValueToken getOperationSign() {
        IElementType elementType = getOperationTokenNode().getElementType();
        Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtSingleValueToken");
        return (KtSingleValueToken) elementType;
    }

    private default KtExpression getExpression(boolean afterOperation) {
        Sequence<PsiElement> sequenceSiblings;
        PsiElement psi = getOperationTokenNode().getPsi();
        PsiElement psiElement = null;
        if (psi == null || (sequenceSiblings = PsiUtilsKt.siblings(psi, afterOperation, false)) == null) {
            return null;
        }
        for (PsiElement psiElement2 : sequenceSiblings) {
            if (psiElement2 instanceof KtExpression) {
                psiElement = psiElement2;
                break;
            }
        }
        return (KtExpression) psiElement;
    }
}
