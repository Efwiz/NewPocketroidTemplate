package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.parsing.KotlinExpressionParsing;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: KtOperationReferenceExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\r\u0010\t\u001a\u00070\n¢\u0006\u0002\b\u000bH\u0016J\u0006\u0010\f\u001a\u00020\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtOperationReferenceExpression;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpressionImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "operationSignTokenType", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "getOperationSignTokenType", "()Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "getReferencedNameElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/annotations/Nullable;", "isConventionOperator", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtOperationReferenceExpression extends KtSimpleNameExpressionImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtOperationReferenceExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getReferencedNameElement() {
        KtOperationReferenceExpression ktOperationReferenceExpressionFindChildByType = findChildByType(KotlinExpressionParsing.ALL_OPERATIONS);
        if (ktOperationReferenceExpressionFindChildByType == null) {
            ktOperationReferenceExpressionFindChildByType = this;
        }
        Intrinsics.checkNotNull(ktOperationReferenceExpressionFindChildByType);
        return ktOperationReferenceExpressionFindChildByType;
    }

    public final KtSingleValueToken getOperationSignTokenType() {
        UserDataHolder firstChild = getFirstChild();
        TreeElement treeElement = firstChild instanceof TreeElement ? (TreeElement) firstChild : null;
        IElementType elementType = treeElement != null ? treeElement.getElementType() : null;
        if (elementType instanceof KtSingleValueToken) {
            return (KtSingleValueToken) elementType;
        }
        return null;
    }

    public final boolean isConventionOperator() {
        KtSingleValueToken operationSignTokenType = getOperationSignTokenType();
        return (operationSignTokenType == null || OperatorConventions.getNameForOperationSymbol(operationSignTokenType) == null) ? false : true;
    }
}
