package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class FieldElement extends CompositeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "child";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/FieldElement";
        if (i == 1) {
            objArr[2] = "deleteChildInternal";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public FieldElement() {
        super(JavaElementType.FIELD);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FieldElement(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return findChildByRole(9).getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        ASTNode aSTNodeFindChildByRole;
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (getChildRole(aSTNode) == 21 && (aSTNodeFindChildByRole = findChildByRole(20)) != null) {
            deleteChildInternal(aSTNodeFindChildByRole);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        switch (i) {
            case 7:
                return PsiImplUtil.findDocComment(this);
            case 8:
                return findChildByType(JavaElementType.MODIFIER_LIST);
            case 9:
                return findChildByType(JavaTokenType.IDENTIFIER);
            case 10:
                return findChildByType(JavaElementType.TYPE);
            default:
                switch (i) {
                    case 20:
                        return findChildByType(JavaTokenType.EQ);
                    case 21:
                        return findChildByType(ElementType.EXPRESSION_BIT_SET);
                    case 22:
                        return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
                    default:
                        return null;
                }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaDocElementType.DOC_COMMENT) {
            return getChildRole(aSTNode, 7);
        }
        if (elementType != JavaTokenType.C_STYLE_COMMENT && elementType != JavaTokenType.END_OF_LINE_COMMENT) {
            if (elementType == JavaElementType.MODIFIER_LIST) {
                return 8;
            }
            if (elementType == JavaElementType.TYPE) {
                return getChildRole(aSTNode, 10);
            }
            if (elementType == JavaTokenType.IDENTIFIER) {
                return getChildRole(aSTNode, 9);
            }
            if (elementType == JavaTokenType.EQ) {
                return getChildRole(aSTNode, 20);
            }
            if (elementType == JavaTokenType.SEMICOLON) {
                return getChildRole(aSTNode, 22);
            }
            if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
                return 21;
            }
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode copyElement() {
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        PsiClass containingClass = ((PsiField) getPsi()).getContainingClass();
        return containingClass != null ? ChangeUtil.copyElement(this, containingClass.mo6093getTypeParameterList(), charTableFindCharTableByTree) : super.copyElement();
    }
}
