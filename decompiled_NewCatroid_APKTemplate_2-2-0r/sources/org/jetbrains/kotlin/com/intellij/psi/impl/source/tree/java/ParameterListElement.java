package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class ParameterListElement extends CompositeElement implements Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) ParameterListElement.class);
    private static final TokenSet PARAMETER_SET = TokenSet.create(JavaElementType.PARAMETER, JavaElementType.RECEIVER_PARAMETER);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ParameterListElement";
        if (i != 1) {
            objArr[2] = "deleteChildInternal";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ParameterListElement() {
        super(PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        ensureParenthesisAroundParameterList();
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                aSTNode2 = findChildByRole(25);
                bool = Boolean.TRUE;
            } else {
                aSTNode2 = findChildByRole(24);
                bool = Boolean.FALSE;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement == aSTNode) {
            TokenSet tokenSet = PARAMETER_SET;
            if (tokenSet.contains(treeElement.getElementType())) {
                JavaSourceUtil.addSeparatingComma(this, treeElement, tokenSet);
            }
        }
        try {
            CodeStyleManager.getInstance(getManager().getProject()).reformat(getPsi());
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
        }
        return treeElementAddInternal;
    }

    private void ensureParenthesisAroundParameterList() {
        if (findChildByRole(24) == null) {
            addLeaf(JavaTokenType.LPARENTH, "(", getFirstChildNode());
            addLeaf(JavaTokenType.RPARENTH, Constant.AFTER_QUTO, null);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        TreeElement lastNodeInsideParens = getLastNodeInsideParens();
        TreeElement firstNodeInsideParens = getFirstNodeInsideParens();
        if (PARAMETER_SET.contains(aSTNode.getElementType())) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
            ensureParenthesisAroundParameterList();
        }
        super.deleteChildInternal(aSTNode);
        TreeElement lastNodeInsideParens2 = getLastNodeInsideParens();
        if (lastNodeInsideParens2 != null && lastNodeInsideParens != null && lastNodeInsideParens2.getElementType() == WHITE_SPACE) {
            if (lastNodeInsideParens.getElementType() != WHITE_SPACE) {
                deleteChildInternal(lastNodeInsideParens2);
            } else {
                replaceChild(lastNodeInsideParens2, (ASTNode) lastNodeInsideParens.clone());
            }
        }
        TreeElement firstNodeInsideParens2 = getFirstNodeInsideParens();
        if (firstNodeInsideParens2 != null && firstNodeInsideParens2.getElementType() == WHITE_SPACE) {
            if (firstNodeInsideParens == null || firstNodeInsideParens.getElementType() != WHITE_SPACE) {
                deleteChildInternal(firstNodeInsideParens2);
            } else {
                replaceChild(firstNodeInsideParens2, (ASTNode) firstNodeInsideParens.clone());
            }
        }
        try {
            CodeStyleManager.getInstance(getManager().getProject()).reformat(getPsi());
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 24) {
            TreeElement firstChildNode = getFirstChildNode();
            if (firstChildNode.getElementType() == LPARENTH) {
                return firstChildNode;
            }
            return null;
        }
        if (i != 25) {
            return null;
        }
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode.getElementType() == RPARENTH) {
            return lastChildNode;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (PARAMETER_SET.contains(elementType)) {
            return 15;
        }
        if (elementType == COMMA) {
            return 23;
        }
        if (elementType == LPARENTH) {
            return getChildRole(aSTNode, 24);
        }
        if (elementType == RPARENTH) {
            return getChildRole(aSTNode, 25);
        }
        return 0;
    }

    private TreeElement getLastNodeInsideParens() {
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode.getElementType() == RPARENTH) {
            return lastChildNode.getTreePrev();
        }
        return null;
    }

    private TreeElement getFirstNodeInsideParens() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode.getElementType() == LPARENTH) {
            return firstChildNode.getTreeNext();
        }
        return null;
    }
}
