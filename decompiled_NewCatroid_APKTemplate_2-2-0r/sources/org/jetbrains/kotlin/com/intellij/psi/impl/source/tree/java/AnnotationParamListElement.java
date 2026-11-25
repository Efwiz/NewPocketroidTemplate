package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class AnnotationParamListElement extends CompositeElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) AnnotationParamListElement.class);
    private static final TokenSet NAME_VALUE_PAIR_BIT_SET = TokenSet.create(JavaElementType.NAME_VALUE_PAIR);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/AnnotationParamListElement";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "deleteChildInternal";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public AnnotationParamListElement() {
        super(JavaElementType.ANNOTATION_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.COMMA) {
            return 23;
        }
        if (elementType == JavaTokenType.LPARENTH) {
            return 24;
        }
        if (elementType == JavaTokenType.RPARENTH) {
            return 25;
        }
        if (ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET.contains(elementType)) {
            return 249;
        }
        return (elementType == JavaElementType.NAME_VALUE_PAIR && aSTNode.getFirstChildNode() != null && aSTNode.getFirstChildNode().getElementType() == JavaElementType.ANNOTATION_ARRAY_INITIALIZER) ? 249 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        if (i == 24) {
            return findChildByType(JavaTokenType.LPARENTH);
        }
        if (i != 25) {
            LOG.assertTrue(false);
            return null;
        }
        return findChildByType(JavaTokenType.RPARENTH);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        PsiAnnotationMemberValue value;
        if (findChildByType(JavaTokenType.LPARENTH) == null) {
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.LPARENTH, "(", 0, 1, SharedImplUtil.findCharTableByTree(this), getManager());
            super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, getFirstChildNode(), true);
        }
        if (findChildByType(JavaTokenType.RPARENTH) == null) {
            LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.RPARENTH, Constant.AFTER_QUTO, 0, 1, SharedImplUtil.findCharTableByTree(this), getManager());
            super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, getLastChildNode(), false);
        }
        ASTNode[] children = getChildren(NAME_VALUE_PAIR_BIT_SET);
        if (children.length == 1) {
            ASTNode aSTNode3 = children[0];
            if (aSTNode3 instanceof PsiNameValuePair) {
                PsiNameValuePair psiNameValuePair = (PsiNameValuePair) aSTNode3;
                if (psiNameValuePair.getName() == null && (value = psiNameValuePair.getValue()) != null) {
                    try {
                        replaceChild(aSTNode3, JavaPsiFacade.getElementFactory(getPsi().getProject()).createAnnotationFromText("@AAA(value = " + value.getText() + Constant.AFTER_QUTO, null).getParameterList().getAttributes()[0].getNode());
                    } catch (IncorrectOperationException e) {
                        LOG.error((Throwable) e);
                    }
                }
            }
        }
        if (aSTNode2 == null) {
            if (bool == null) {
                bool = Boolean.TRUE;
            }
            aSTNode2 = findChildByType(bool.booleanValue() ? JavaTokenType.RPARENTH : JavaTokenType.LPARENTH);
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement.getElementType() == JavaElementType.NAME_VALUE_PAIR && aSTNode.getElementType() == JavaElementType.NAME_VALUE_PAIR) {
            JavaSourceUtil.addSeparatingComma(this, treeElement, NAME_VALUE_PAIR_BIT_SET);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode.getElementType() == JavaElementType.NAME_VALUE_PAIR) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
    }
}
