package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class EnumConstantElement extends CompositeElement implements Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) EnumConstantElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/EnumConstantElement", "getChildRole"));
    }

    public EnumConstantElement() {
        super(ENUM_CONSTANT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return findChildByRole(9).getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 7) {
            return PsiImplUtil.findDocComment(this);
        }
        if (i == 8) {
            return findChildByType(JavaElementType.MODIFIER_LIST);
        }
        if (i == 9) {
            return findChildByType(JavaTokenType.IDENTIFIER);
        }
        if (i == 70) {
            return findChildByType(ENUM_CONSTANT_INITIALIZER);
        }
        if (i != 73) {
            return null;
        }
        return findChildByType(EXPRESSION_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaDocElementType.DOC_COMMENT) {
            return getChildRole(aSTNode, 7);
        }
        if (elementType != JavaTokenType.C_STYLE_COMMENT && elementType != JavaTokenType.END_OF_LINE_COMMENT) {
            if (elementType == JavaTokenType.IDENTIFIER) {
                return getChildRole(aSTNode, 9);
            }
            if (elementType == ENUM_CONSTANT_INITIALIZER) {
                return 70;
            }
            if (elementType == EXPRESSION_LIST) {
                return 73;
            }
            if (elementType == MODIFIER_LIST) {
                return 8;
            }
        }
        return 0;
    }
}
