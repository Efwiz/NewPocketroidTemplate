package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaParameterType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiConditionalExpressionImpl extends ExpressionPsiElement implements PsiConditionalExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiConditionalExpressionImpl.class);

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
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiConditionalExpressionImpl";
        } else {
            objArr[0] = "place";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiConditionalExpressionImpl";
        } else {
            objArr[1] = "getCondition";
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

    public PsiConditionalExpressionImpl() {
        super(JavaElementType.CONDITIONAL_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression
    public PsiExpression getCondition() {
        PsiExpression psiExpression = (PsiExpression) findChildByRoleAsPsiElement(32);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression
    public PsiExpression getThenExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(112);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression
    public PsiExpression getElseExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(113);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType leastUpperBound;
        PsiExpression thenExpression = getThenExpression();
        PsiExpression elseExpression = getElseExpression();
        PsiType type = thenExpression == null ? null : thenExpression.getType();
        PsiType type2 = elseExpression == null ? null : elseExpression.getType();
        if (Objects.equals(type, type2)) {
            return type;
        }
        if (PsiUtil.isLanguageLevel8OrHigher(this) && PsiPolyExpressionUtil.isPolyExpression(this)) {
            PsiType targetType = InferenceSession.getTargetType(this);
            if (MethodCandidateInfo.isOverloadCheck()) {
                if (targetType == null || type == null || type2 == null || !targetType.isAssignableFrom(type) || !targetType.isAssignableFrom(type2)) {
                    return null;
                }
                return targetType;
            }
            if (targetType != null) {
                return targetType;
            }
        }
        if (type == null) {
            return type2;
        }
        if (type2 == null) {
            return type;
        }
        int typeRank = TypeConversionUtil.getTypeRank(type);
        int typeRank2 = TypeConversionUtil.getTypeRank(type2);
        if ((type instanceof PsiClassType) && type2.equals(PsiPrimitiveType.getUnboxedType(type))) {
            return type2;
        }
        if ((type2 instanceof PsiClassType) && type.equals(PsiPrimitiveType.getUnboxedType(type2))) {
            return type;
        }
        if (TypeConversionUtil.isNumericType(typeRank) && TypeConversionUtil.isNumericType(typeRank2)) {
            if (typeRank == 1 && typeRank2 == 2) {
                return type2 instanceof PsiPrimitiveType ? type2 : PsiPrimitiveType.getUnboxedType(type2);
            }
            if (typeRank == 2 && typeRank2 == 1) {
                return type instanceof PsiPrimitiveType ? type : PsiPrimitiveType.getUnboxedType(type);
            }
            if (typeRank2 == 4 && ((typeRank == 1 || typeRank == 2 || typeRank == 3) && TypeConversionUtil.areTypesAssignmentCompatible(type, elseExpression))) {
                return type;
            }
            return (typeRank == 4 && (typeRank2 == 1 || typeRank2 == 2 || typeRank2 == 3) && TypeConversionUtil.areTypesAssignmentCompatible(type2, thenExpression)) ? type2 : TypeConversionUtil.binaryNumericPromotion(type, type2);
        }
        if (TypeConversionUtil.isNullType(type) && !(type2 instanceof PsiPrimitiveType)) {
            return type2;
        }
        if ((TypeConversionUtil.isNullType(type2) && !(type instanceof PsiPrimitiveType)) || TypeConversionUtil.isAssignable(type, type2, false)) {
            return type;
        }
        if (TypeConversionUtil.isAssignable(type2, type, false)) {
            return type2;
        }
        if (!PsiUtil.isLanguageLevel5OrHigher(this)) {
            return null;
        }
        if (TypeConversionUtil.isPrimitiveAndNotNull(type) && (type = ((PsiPrimitiveType) type).getBoxedType(this)) == null) {
            return null;
        }
        if ((TypeConversionUtil.isPrimitiveAndNotNull(type2) && (type2 = ((PsiPrimitiveType) type2).getBoxedType(this)) == null) || (type instanceof PsiLambdaParameterType) || (type2 instanceof PsiLambdaParameterType) || (leastUpperBound = GenericsUtil.getLeastUpperBound(type, type2, getManager())) == null) {
            return null;
        }
        return PsiUtil.captureToplevelWildcards(leastUpperBound, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 32) {
            return getFirstChildNode();
        }
        if (i != 87) {
            switch (i) {
                case 112:
                    for (ASTNode treeNext = findChildByRole(114).getTreeNext(); treeNext != null; treeNext = treeNext.getTreeNext()) {
                        if (ElementType.EXPRESSION_BIT_SET.contains(treeNext.getElementType())) {
                            return treeNext;
                        }
                    }
                    return null;
                case 113:
                    if (findChildByRole(87) != null && ElementType.EXPRESSION_BIT_SET.contains(getLastChildNode().getElementType())) {
                        return getLastChildNode();
                    }
                    return null;
                case 114:
                    return findChildByType(JavaTokenType.QUEST);
                default:
                    return null;
            }
        }
        return findChildByType(JavaTokenType.COLON);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            int childRole = getChildRole(aSTNode, 32);
            if (childRole != 0) {
                return childRole;
            }
            int childRole2 = getChildRole(aSTNode, 112);
            return childRole2 != 0 ? childRole2 : getChildRole(aSTNode, 113);
        }
        if (aSTNode.getElementType() == JavaTokenType.QUEST) {
            return 114;
        }
        return aSTNode.getElementType() == JavaTokenType.COLON ? 87 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitConditionalExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == null) {
            return true;
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return true;
        }
        PsiExpression condition = getCondition();
        if (psiElement == getThenExpression()) {
            return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_TRUE.putInto(resolveState), null, psiElement2);
        }
        if (psiElement == getElseExpression()) {
            return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_FALSE.putInto(resolveState), null, psiElement2);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiConditionalExpression:" + getText();
    }
}
