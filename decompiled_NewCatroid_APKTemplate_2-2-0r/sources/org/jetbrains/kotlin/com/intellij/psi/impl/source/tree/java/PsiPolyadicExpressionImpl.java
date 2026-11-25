package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.NullableFunction;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPolyadicExpressionImpl extends ExpressionPsiElement implements PsiPolyadicExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiPolyadicExpressionImpl.class);
    private static final Function<PsiPolyadicExpressionImpl, PsiType> MY_TYPE_EVALUATOR = new NullableFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPolyadicExpressionImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return PsiPolyadicExpressionImpl.doGetType((PsiPolyadicExpressionImpl) obj);
        }
    };
    private static final TokenSet OUR_OPERATIONS_BIT_SET = TokenSet.create(JavaTokenType.OROR, JavaTokenType.ANDAND, JavaTokenType.OR, JavaTokenType.XOR, JavaTokenType.AND, JavaTokenType.EQEQ, JavaTokenType.NE, JavaTokenType.LT, JavaTokenType.GT, JavaTokenType.LE, JavaTokenType.GE, JavaTokenType.LTLT, JavaTokenType.GTGT, JavaTokenType.GTGTGT, JavaTokenType.PLUS, JavaTokenType.MINUS, JavaTokenType.ASTERISK, JavaTokenType.DIV, JavaTokenType.PERC);
    private volatile PsiExpression[] cachedOperands;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                i2 = 3;
                break;
            case 4:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "operand";
                break;
            case 2:
                objArr[0] = "child";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPolyadicExpressionImpl";
                break;
            case 5:
            case 9:
                objArr[0] = "processor";
                break;
            case 6:
            case 10:
                objArr[0] = "state";
                break;
            case 7:
            case 11:
                objArr[0] = "place";
                break;
            case 8:
                objArr[0] = "expression";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPolyadicExpressionImpl";
                break;
            case 4:
                objArr[1] = "getOperands";
                break;
            default:
                objArr[1] = "getOperationTokenType";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getTokenBeforeOperand";
                break;
            case 2:
                objArr[2] = "getChildRole";
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "processDeclarations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                throw new IllegalArgumentException(str2);
            case 4:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public PsiPolyadicExpressionImpl() {
        super(JavaElementType.POLYADIC_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = ((PsiJavaToken) findChildByRoleAsPsiElement(63)).getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(0);
        }
        return tokenType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.com.intellij.psi.PsiExpression] */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r3v4, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiJavaToken getTokenBeforeOperand(PsiExpression psiExpression) {
        if (psiExpression == 0) {
            $$$reportNull$$$0(1);
        }
        while (psiExpression != 0) {
            if (getChildRole(psiExpression.getNode()) == 63) {
                return (PsiJavaToken) psiExpression;
            }
            psiExpression = psiExpression.getPrevSibling();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return JavaResolveCache.getInstance(getProject()).getType(this, MY_TYPE_EVALUATOR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType doGetType(PsiPolyadicExpressionImpl psiPolyadicExpressionImpl) {
        PsiExpression[] operands = psiPolyadicExpressionImpl.getOperands();
        IElementType operationTokenType = psiPolyadicExpressionImpl.getOperationTokenType();
        PsiType psiTypeCalcTypeForBinaryExpression = null;
        for (int i = 1; i < operands.length; i++) {
            PsiType type = operands[i].getType();
            PsiType psiTypeCalcTypeForBinaryExpression2 = TypeConversionUtil.calcTypeForBinaryExpression(null, type, operationTokenType, false);
            if (psiTypeCalcTypeForBinaryExpression2 != TypeConversionUtil.NULL_TYPE) {
                return psiTypeCalcTypeForBinaryExpression2;
            }
            if (psiTypeCalcTypeForBinaryExpression == null) {
                psiTypeCalcTypeForBinaryExpression = operands[0].getType();
            }
            psiTypeCalcTypeForBinaryExpression = TypeConversionUtil.calcTypeForBinaryExpression(psiTypeCalcTypeForBinaryExpression, type, operationTokenType, true);
        }
        return psiTypeCalcTypeForBinaryExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 63) {
            return findChildByType(OUR_OPERATIONS_BIT_SET);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        return OUR_OPERATIONS_BIT_SET.contains(aSTNode.getElementType()) ? 63 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPolyadicExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiExpression[] getOperands() {
        PsiExpression[] psiExpressionArr = this.cachedOperands;
        if (psiExpressionArr == null) {
            psiExpressionArr = (PsiExpression[]) getChildrenAsPsiElements(ElementType.EXPRESSION_BIT_SET, PsiExpression.ARRAY_FACTORY);
            this.cachedOperands = psiExpressionArr;
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiExpressionArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(5);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(7);
        }
        return processDeclarations(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    static boolean processDeclarations(PsiPolyadicExpression psiPolyadicExpression, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiPolyadicExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(9);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(11);
        }
        IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
        boolean zEquals = operationTokenType.equals(JavaTokenType.ANDAND);
        boolean zEquals2 = operationTokenType.equals(JavaTokenType.OROR);
        if (!zEquals && !zEquals2) {
            return true;
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return true;
        }
        PatternResolveState patternResolveStateFromBoolean = PatternResolveState.fromBoolean(zEquals);
        if (resolveState.get(PatternResolveState.KEY) == patternResolveStateFromBoolean.invert()) {
            return true;
        }
        return PsiScopesUtil.walkChildrenScopes(psiPolyadicExpression, psiScopeProcessor, patternResolveStateFromBoolean.putInto(resolveState), psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        this.cachedOperands = null;
        super.clearCaches();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPolyadicExpression: " + getText();
    }
}
