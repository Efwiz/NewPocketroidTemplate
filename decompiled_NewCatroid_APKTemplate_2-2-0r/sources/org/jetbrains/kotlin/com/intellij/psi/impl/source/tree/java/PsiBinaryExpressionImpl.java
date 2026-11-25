package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBinaryExpressionImpl extends ExpressionPsiElement implements PsiBinaryExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiBinaryExpressionImpl.class);
    private static final Function<PsiBinaryExpressionImpl, PsiType> MY_TYPE_EVALUATOR = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiBinaryExpressionImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return PsiBinaryExpressionImpl.doGetType((PsiBinaryExpressionImpl) obj);
        }
    };
    private static final TokenSet OUR_OPERATIONS_BIT_SET = TokenSet.create(JavaTokenType.OROR, JavaTokenType.ANDAND, JavaTokenType.OR, JavaTokenType.XOR, JavaTokenType.AND, JavaTokenType.EQEQ, JavaTokenType.NE, JavaTokenType.LT, JavaTokenType.GT, JavaTokenType.LE, JavaTokenType.GE, JavaTokenType.LTLT, JavaTokenType.GTGT, JavaTokenType.GTGTGT, JavaTokenType.PLUS, JavaTokenType.MINUS, JavaTokenType.ASTERISK, JavaTokenType.DIV, JavaTokenType.PERC);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiBinaryExpressionImpl";
                break;
            case 4:
                objArr[0] = "operand";
                break;
            case 5:
                objArr[0] = "child";
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            case 7:
                objArr[0] = "processor";
                break;
            case 8:
                objArr[0] = "state";
                break;
            case 9:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "elementType";
                break;
        }
        if (i == 1) {
            objArr[1] = "getLOperand";
        } else if (i == 2) {
            objArr[1] = "getOperationSign";
        } else if (i == 3) {
            objArr[1] = "getOperationTokenType";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiBinaryExpressionImpl";
        } else {
            objArr[1] = "getOperands";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 10:
                break;
            case 4:
                objArr[2] = "getTokenBeforeOperand";
                break;
            case 5:
                objArr[2] = "getChildRole";
                break;
            case 6:
                objArr[2] = "accept";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiBinaryExpressionImpl() {
        this(JavaElementType.BINARY_EXPRESSION);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiBinaryExpressionImpl(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiExpression getLOperand() {
        PsiExpression psiExpression = (PsiExpression) findChildByRoleAsPsiElement(61);
        if (psiExpression == null) {
            $$$reportNull$$$0(1);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiExpression getROperand() {
        return (PsiExpression) findChildByRoleAsPsiElement(62);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiJavaToken getOperationSign() {
        PsiJavaToken psiJavaToken = (PsiJavaToken) findChildByRoleAsPsiElement(63);
        if (psiJavaToken == null) {
            $$$reportNull$$$0(2);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression, org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = getOperationSign().getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(3);
        }
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiJavaToken getTokenBeforeOperand(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (psiExpression != getROperand()) {
            return null;
        }
        return getOperationSign();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType doGetType(PsiBinaryExpressionImpl psiBinaryExpressionImpl) {
        PsiExpression lOperand = psiBinaryExpressionImpl.getLOperand();
        PsiExpression rOperand = psiBinaryExpressionImpl.getROperand();
        if (rOperand == null) {
            return null;
        }
        PsiType type = rOperand.getType();
        IElementType operationTokenType = psiBinaryExpressionImpl.getOperationTokenType();
        PsiType psiTypeCalcTypeForBinaryExpression = TypeConversionUtil.calcTypeForBinaryExpression(null, type, operationTokenType, false);
        return psiTypeCalcTypeForBinaryExpression != TypeConversionUtil.NULL_TYPE ? psiTypeCalcTypeForBinaryExpression : TypeConversionUtil.calcTypeForBinaryExpression(lOperand.getType(), type, operationTokenType, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return JavaResolveCache.getInstance(getProject()).getType(this, MY_TYPE_EVALUATOR);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        switch (i) {
            case 61:
                return getFirstChildNode();
            case 62:
                if (ElementType.EXPRESSION_BIT_SET.contains(getLastChildNode().getElementType())) {
                    return getLastChildNode();
                }
                return null;
            case 63:
                return findChildByType(OUR_OPERATIONS_BIT_SET);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (!ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return OUR_OPERATIONS_BIT_SET.contains(aSTNode.getElementType()) ? 63 : 0;
        }
        if (aSTNode == getFirstChildNode()) {
            return 61;
        }
        return aSTNode == getLastChildNode() ? 62 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitBinaryExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiBinaryExpression:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(7);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(9);
        }
        return PsiPolyadicExpressionImpl.processDeclarations(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiExpression[] getOperands() {
        PsiExpression rOperand = getROperand();
        return rOperand == null ? new PsiExpression[]{getLOperand()} : new PsiExpression[]{getLOperand(), rOperand};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        checkPersistentElementRemoval(psiElement, psiElement2);
        super.deleteChildRange(psiElement, psiElement2);
    }

    private void checkPersistentElementRemoval(PsiElement psiElement, PsiElement psiElement2) {
        int childRole;
        boolean z = false;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getPsi() == psiElement) {
                z = true;
            }
            if (z && ((childRole = getChildRole(firstChildNode)) == 63 || childRole == 61)) {
                LOG.error("Unable to remove child with role " + childRole + "(" + firstChildNode.getElementType() + Constant.AFTER_QUTO);
            }
            if (firstChildNode.getPsi() == psiElement2) {
                z = false;
            }
        }
    }
}
