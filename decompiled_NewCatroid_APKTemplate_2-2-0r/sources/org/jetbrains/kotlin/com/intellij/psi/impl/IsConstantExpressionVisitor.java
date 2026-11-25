package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFieldImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
final class IsConstantExpressionVisitor extends JavaElementVisitor {
    private boolean myIsConstant;
    private final Map<PsiVariable, Boolean> varIsConst = new HashMap();

    IsConstantExpressionVisitor() {
    }

    public boolean isConstant() {
        return this.myIsConstant;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpression(PsiExpression psiExpression) {
        this.myIsConstant = false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLiteralExpression(PsiLiteralExpression psiLiteralExpression) {
        this.myIsConstant = !"null".equals(psiLiteralExpression.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitClassObjectAccessExpression(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        this.myIsConstant = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitParenthesizedExpression(PsiParenthesizedExpression psiParenthesizedExpression) {
        PsiExpression expression = psiParenthesizedExpression.getExpression();
        if (expression != null) {
            expression.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitTypeCastExpression(PsiTypeCastExpression psiTypeCastExpression) {
        PsiExpression operand = psiTypeCastExpression.getOperand();
        if (operand == null) {
            this.myIsConstant = false;
            return;
        }
        operand.accept(this);
        if (this.myIsConstant) {
            PsiTypeElement castType = psiTypeCastExpression.getCastType();
            if (castType == null) {
                this.myIsConstant = false;
                return;
            }
            PsiType type = castType.getType();
            if ((type instanceof PsiPrimitiveType) || type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                return;
            }
            this.myIsConstant = false;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPrefixExpression(PsiPrefixExpression psiPrefixExpression) {
        IElementType operationTokenType;
        PsiExpression operand = psiPrefixExpression.getOperand();
        if (operand == null) {
            this.myIsConstant = false;
            return;
        }
        operand.accept(this);
        if (!this.myIsConstant || (operationTokenType = psiPrefixExpression.getOperationTokenType()) == JavaTokenType.PLUS || operationTokenType == JavaTokenType.MINUS || operationTokenType == JavaTokenType.TILDE || operationTokenType == JavaTokenType.EXCL) {
            return;
        }
        this.myIsConstant = false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPolyadicExpression(PsiPolyadicExpression psiPolyadicExpression) {
        for (PsiExpression psiExpression : psiPolyadicExpression.getOperands()) {
            psiExpression.accept(this);
            if (!this.myIsConstant) {
                return;
            }
            PsiType type = psiExpression.getType();
            if (type != null && !(type instanceof PsiPrimitiveType) && !type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                this.myIsConstant = false;
                return;
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitConditionalExpression(PsiConditionalExpression psiConditionalExpression) {
        PsiExpression thenExpression = psiConditionalExpression.getThenExpression();
        PsiExpression elseExpression = psiConditionalExpression.getElseExpression();
        if (thenExpression == null || elseExpression == null) {
            this.myIsConstant = false;
            return;
        }
        psiConditionalExpression.getCondition().accept(this);
        if (this.myIsConstant) {
            thenExpression.accept(this);
            if (this.myIsConstant) {
                elseExpression.accept(this);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        if (qualifierExpression != null && !(qualifierExpression instanceof PsiReferenceExpression)) {
            this.myIsConstant = false;
            return;
        }
        PsiElement psiElementResolve = psiReferenceExpression.resolve();
        if (!(psiElementResolve instanceof PsiVariable)) {
            this.myIsConstant = false;
            return;
        }
        PsiVariable psiVariable = (PsiVariable) psiElementResolve;
        Boolean bool = this.varIsConst.get(psiVariable);
        if (bool != null) {
            this.myIsConstant &= bool.booleanValue();
            return;
        }
        if (psiVariable instanceof PsiEnumConstant) {
            this.myIsConstant = true;
            this.varIsConst.put(psiVariable, Boolean.TRUE);
            return;
        }
        this.varIsConst.put(psiVariable, Boolean.FALSE);
        if (!psiVariable.hasModifierProperty("final")) {
            this.myIsConstant = false;
            return;
        }
        PsiExpression detachedInitializer = PsiFieldImpl.getDetachedInitializer(psiVariable);
        if (detachedInitializer == null) {
            this.myIsConstant = false;
        } else {
            detachedInitializer.accept(this);
            this.varIsConst.put(psiVariable, Boolean.valueOf(this.myIsConstant));
        }
    }
}
