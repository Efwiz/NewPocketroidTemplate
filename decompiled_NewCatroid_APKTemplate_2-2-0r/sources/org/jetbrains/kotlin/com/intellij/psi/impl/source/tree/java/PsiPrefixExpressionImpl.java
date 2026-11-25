package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPrefixExpressionImpl extends ExpressionPsiElement implements PsiPrefixExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiPrefixExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "child";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i == 4) {
            objArr[0] = "processor";
        } else if (i == 5) {
            objArr[0] = "state";
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPrefixExpressionImpl";
        } else {
            objArr[0] = "place";
        }
        switch (i) {
            case 1:
                objArr[1] = "getOperationTokenType";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPrefixExpressionImpl";
                break;
            default:
                objArr[1] = "getOperationSign";
                break;
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3) {
            objArr[2] = "accept";
        } else if (i == 4 || i == 5 || i == 6) {
            objArr[2] = "processDeclarations";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiPrefixExpressionImpl() {
        super(JavaElementType.PREFIX_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public PsiExpression getOperand() {
        return (PsiExpression) findChildByRoleAsPsiElement(67);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public PsiJavaToken getOperationSign() {
        PsiJavaToken psiJavaToken = (PsiJavaToken) findChildByRoleAsPsiElement(63);
        if (psiJavaToken == null) {
            $$$reportNull$$$0(0);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = getOperationSign().getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(1);
        }
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiExpression operand = getOperand();
        if (operand == null) {
            return null;
        }
        PsiType type = operand.getType();
        IElementType operationTokenType = getOperationTokenType();
        if (operationTokenType == JavaTokenType.PLUS || operationTokenType == JavaTokenType.MINUS || operationTokenType == JavaTokenType.TILDE) {
            if (type == null) {
                return null;
            }
            if (type instanceof PsiClassType) {
                type = PsiPrimitiveType.getUnboxedType(type);
            }
            return (PsiType.BYTE.equals(type) || PsiType.CHAR.equals(type) || PsiType.SHORT.equals(type)) ? PsiType.INT : type;
        }
        if (operationTokenType == JavaTokenType.PLUSPLUS || operationTokenType == JavaTokenType.MINUSMINUS) {
            return type;
        }
        if (operationTokenType == JavaTokenType.EXCL) {
            return PsiType.BOOLEAN;
        }
        LOG.assertTrue(false);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 63) {
            return getFirstChildNode();
        }
        if (i == 67 && ElementType.EXPRESSION_BIT_SET.contains(getLastChildNode().getElementType())) {
            return getLastChildNode();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (aSTNode == getFirstChildNode()) {
            return 63;
        }
        return (aSTNode == getLastChildNode() && ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) ? 67 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPrefixExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PatternResolveState patternResolveState;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(4);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement != null || !getOperationTokenType().equals(JavaTokenType.EXCL)) {
            return true;
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) && (patternResolveState = (PatternResolveState) resolveState.get(PatternResolveState.KEY)) != null) {
            return PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, patternResolveState.invert().putInto(resolveState), null, psiElement2);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPrefixExpression:" + getText();
    }
}
