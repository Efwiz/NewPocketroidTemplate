package org.jetbrains.kotlin.psi.stubs.elements;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;

/* JADX INFO: loaded from: classes6.dex */
public class KtDotQualifiedExpressionElementType extends KtPlaceHolderStubElementType<KtDotQualifiedExpression> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/psi/stubs/elements/KtDotQualifiedExpressionElementType", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDotQualifiedExpressionElementType(String str) {
        super(str, KtDotQualifiedExpression.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static boolean checkNodeTypesTraversal(ASTNode aSTNode) {
        IElementType elementType = aSTNode.getElementType();
        if (elementType != KtStubElementTypes.DOT_QUALIFIED_EXPRESSION && elementType != KtStubElementTypes.REFERENCE_EXPRESSION && elementType != KtTokens.IDENTIFIER && elementType != KtTokens.DOT) {
            return false;
        }
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (!checkNodeTypesTraversal(firstChildNode)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode aSTNode) {
        ASTNode treeParent = aSTNode.getTreeParent();
        if (treeParent == null) {
            return false;
        }
        IElementType elementType = treeParent.getElementType();
        return (elementType == KtStubElementTypes.IMPORT_DIRECTIVE || elementType == KtStubElementTypes.PACKAGE_DIRECTIVE || elementType == KtStubElementTypes.VALUE_ARGUMENT || elementType == KtStubElementTypes.CLASS_LITERAL_EXPRESSION || elementType == KtStubElementTypes.DOT_QUALIFIED_EXPRESSION) && checkNodeTypesTraversal(aSTNode) && super.shouldCreateStub(aSTNode);
    }
}
