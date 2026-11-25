package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class ClsBinaryExpressionImpl extends ClsElementImpl implements PsiBinaryExpression {
    private final PsiExpression myLOperand;
    private final PsiJavaToken myOperator;
    private final ClsElementImpl myParent;
    private final PsiExpression myROperand;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 9 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 9 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsBinaryExpressionImpl";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 8:
                objArr[0] = "operand";
                break;
            default:
                objArr[0] = "buffer";
                break;
        }
        if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i == 9) {
            objArr[1] = "getOperands";
        } else if (i == 4) {
            objArr[1] = "getLOperand";
        } else if (i == 5) {
            objArr[1] = "getROperand";
        } else if (i == 6) {
            objArr[1] = "getOperationSign";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsBinaryExpressionImpl";
        } else {
            objArr[1] = "getOperationTokenType";
        }
        switch (i) {
            case 1:
                objArr[2] = "setMirror";
                break;
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 8:
                objArr[2] = "getTokenBeforeOperand";
                break;
            default:
                objArr[2] = "appendMirrorText";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 9 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    ClsBinaryExpressionImpl(ClsElementImpl clsElementImpl, PsiJavaToken psiJavaToken, PsiExpression psiExpression, PsiExpression psiExpression2) {
        this.myParent = clsElementImpl;
        this.myOperator = new ClsJavaTokenImpl(this, psiJavaToken.getTokenType(), psiJavaToken.getText());
        this.myLOperand = ClsParsingUtil.psiToClsExpression(psiExpression, this);
        this.myROperand = ClsParsingUtil.psiToClsExpression(psiExpression2, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        sb.append(getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, JavaElementType.BINARY_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return StringUtil.join(this.myLOperand.getText(), " ", this.myOperator.getText(), " ", this.myROperand.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myLOperand, this.myOperator, this.myROperand};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitBinaryExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiExpression getLOperand() {
        PsiExpression psiExpression = this.myLOperand;
        if (psiExpression == null) {
            $$$reportNull$$$0(4);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiExpression getROperand() {
        PsiExpression psiExpression = this.myROperand;
        if (psiExpression == null) {
            $$$reportNull$$$0(5);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression
    public PsiJavaToken getOperationSign() {
        PsiJavaToken psiJavaToken = this.myOperator;
        if (psiJavaToken == null) {
            $$$reportNull$$$0(6);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression, org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = this.myOperator.getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(7);
        }
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiJavaToken getTokenBeforeOperand(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(8);
        }
        return getOperationSign();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return this.myLOperand.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    public PsiExpression[] getOperands() {
        return new PsiExpression[]{getLOperand(), getROperand()};
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiBinaryExpression:" + getText();
    }
}
