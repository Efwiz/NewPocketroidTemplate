package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class ClsPrefixExpressionImpl extends ClsElementImpl implements PsiPrefixExpression {
    private final PsiExpression myOperand;
    private final PsiJavaToken myOperator;
    private final ClsElementImpl myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 3 : 2];
        if (i == 4) {
            objArr[0] = "buffer";
        } else if (i == 5) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsPrefixExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        switch (i) {
            case 1:
                objArr[1] = "getOperationSign";
                break;
            case 2:
                objArr[1] = "getOperationTokenType";
                break;
            case 3:
                objArr[1] = "getChildren";
                break;
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsPrefixExpressionImpl";
                break;
            default:
                objArr[1] = "getOperand";
                break;
        }
        if (i == 4) {
            objArr[2] = "appendMirrorText";
        } else if (i == 5) {
            objArr[2] = "setMirror";
        } else if (i == 6) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    ClsPrefixExpressionImpl(ClsElementImpl clsElementImpl, PsiJavaToken psiJavaToken, PsiExpression psiExpression) {
        this.myParent = clsElementImpl;
        this.myOperator = new ClsJavaTokenImpl(this, psiJavaToken.getTokenType(), psiJavaToken.getText());
        this.myOperand = ClsParsingUtil.psiToClsExpression(psiExpression, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public PsiExpression getOperand() {
        PsiExpression psiExpression = this.myOperand;
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public PsiJavaToken getOperationSign() {
        PsiJavaToken psiJavaToken = this.myOperator;
        if (psiJavaToken == null) {
            $$$reportNull$$$0(1);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = this.myOperator.getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(2);
        }
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return this.myOperand.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myOperator, this.myOperand};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return StringUtil.join(this.myOperator.getText(), this.myOperand.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(4);
        }
        sb.append(getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(5);
        }
        setMirrorCheckingType(treeElement, JavaElementType.PREFIX_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPrefixExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPrefixExpression:" + getText();
    }
}
