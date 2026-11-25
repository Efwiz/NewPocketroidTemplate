package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimaryPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiPatternUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PsiInstanceOfExpressionImpl extends ExpressionPsiElement implements PsiInstanceOfExpression, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiInstanceOfExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "child";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "processor";
        } else if (i == 4) {
            objArr[0] = "state";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiInstanceOfExpressionImpl";
        } else {
            objArr[0] = "place";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiInstanceOfExpressionImpl";
        } else {
            objArr[1] = "getOperand";
        }
        if (i == 1) {
            objArr[2] = "getChildRole";
        } else if (i == 2) {
            objArr[2] = "accept";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "processDeclarations";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiInstanceOfExpressionImpl() {
        super(INSTANCE_OF_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression
    public PsiExpression getOperand() {
        PsiExpression psiExpression = (PsiExpression) findChildByRoleAsPsiElement(67);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression
    public PsiTypeElement getCheckType() {
        PsiPrimaryPattern pattern = getPattern();
        if (pattern == null) {
            return (PsiTypeElement) findChildByRoleAsPsiElement(10);
        }
        PsiTypeTestPattern psiTypeTestPattern = (PsiTypeTestPattern) ObjectUtils.tryCast(JavaPsiPatternUtil.skipParenthesizedPatternDown(pattern), PsiTypeTestPattern.class);
        if (psiTypeTestPattern == null) {
            return null;
        }
        return psiTypeTestPattern.getCheckType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return PsiType.BOOLEAN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 10) {
            return findChildByType(TYPE);
        }
        if (i == 67) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i != 68) {
            return null;
        }
        return findChildByType(INSTANCEOF_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == TYPE) {
            return 10;
        }
        if (elementType == INSTANCEOF_KEYWORD) {
            return 68;
        }
        return EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 67 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression
    public PsiPrimaryPattern getPattern() {
        return (PsiPrimaryPattern) PsiTreeUtil.getChildOfType(this, PsiPrimaryPattern.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitInstanceOfExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PsiPrimaryPattern pattern;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement != null) {
            return true;
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if ((elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) || resolveState.get(PatternResolveState.KEY) == PatternResolveState.WHEN_FALSE || (pattern = getPattern()) == null) {
            return true;
        }
        return pattern.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiInstanceofExpression";
    }
}
