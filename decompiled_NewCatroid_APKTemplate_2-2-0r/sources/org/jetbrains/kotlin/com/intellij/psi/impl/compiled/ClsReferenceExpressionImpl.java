package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class ClsReferenceExpressionImpl extends ClsElementImpl implements PsiReferenceExpression {
    private final String myName;
    private final PsiIdentifier myNameElement;
    private final ClsElementImpl myParent;
    private final PsiReferenceExpression myPatternExpression;
    private final PsiReferenceExpression myQualifier;

    /* JADX WARN: Code duplicated, block: B:7:0x000e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 10 && i != 15) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 10 && i != 15) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceExpressionImpl";
                break;
            case 7:
                objArr[0] = "newElementName";
                break;
            case 8:
            case 9:
            case 13:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "processor";
                break;
            case 12:
                objArr[0] = "buffer";
                break;
            case 14:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        if (i == 10) {
            objArr[1] = "getVariants";
        } else if (i != 15) {
            switch (i) {
                case 1:
                case 2:
                    objArr[1] = "getChildren";
                    break;
                case 3:
                    objArr[1] = "advancedResolve";
                    break;
                case 4:
                    objArr[1] = "multiResolve";
                    break;
                case 5:
                    objArr[1] = "getElement";
                    break;
                case 6:
                    objArr[1] = "getCanonicalText";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceExpressionImpl";
                    break;
            }
        } else {
            objArr[1] = "getTypeParameters";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
            case 15:
                break;
            case 7:
                objArr[2] = "handleElementRename";
                break;
            case 8:
                objArr[2] = "bindToElement";
                break;
            case 9:
                objArr[2] = "isReferenceTo";
                break;
            case 11:
                objArr[2] = "processVariants";
                break;
            case 12:
                objArr[2] = "appendMirrorText";
                break;
            case 13:
                objArr[2] = "setMirror";
                break;
            case 14:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "bindToElementViaStaticImport";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 15) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public ClsReferenceExpressionImpl(ClsElementImpl clsElementImpl, PsiReferenceExpression psiReferenceExpression) {
        this.myParent = clsElementImpl;
        this.myPatternExpression = psiReferenceExpression;
        PsiReferenceExpression psiReferenceExpression2 = (PsiReferenceExpression) psiReferenceExpression.getQualifierExpression();
        if (psiReferenceExpression2 != null) {
            this.myQualifier = new ClsReferenceExpressionImpl(this, psiReferenceExpression2);
        } else {
            this.myQualifier = null;
        }
        String referenceName = psiReferenceExpression.getCustomReferenceName();
        this.myName = referenceName;
        this.myNameElement = new ClsIdentifierImpl(this, referenceName);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiExpression getQualifierExpression() {
        return this.myQualifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiElement bindToElementViaStaticImport(PsiClass psiClass) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public void setQualifierExpression(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException("This method should not be called for compiled elements");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return this.myNameElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiReferenceExpression psiReferenceExpression = this.myQualifier;
        if (psiReferenceExpression != null) {
            return new PsiElement[]{psiReferenceExpression, this.myNameElement};
        }
        return new PsiElement[]{this.myNameElement};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myQualifier != null ? this.myQualifier.getText() + "." + this.myName : this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return this.myQualifier != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return this.myPatternExpression.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return this.myPatternExpression.resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult javaResolveResultAdvancedResolve = this.myPatternExpression.advancedResolve(z);
        if (javaResolveResultAdvancedResolve == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResultAdvancedResolve;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult javaResolveResultAdvancedResolve = advancedResolve(z);
        JavaResolveResult[] javaResolveResultArr = javaResolveResultAdvancedResolve != JavaResolveResult.EMPTY ? new JavaResolveResult[]{javaResolveResultAdvancedResolve} : JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(4);
        }
        return javaResolveResultArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String canonicalText = this.myPatternExpression.getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(6);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        return this.myPatternExpression.getCustomReferenceName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        return this.myPatternExpression.isReferenceTo(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        Object[] variants = this.myPatternExpression.getVariants();
        if (variants == null) {
            $$$reportNull$$$0(10);
        }
        return variants;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(11);
        }
        this.myPatternExpression.processVariants(psiScopeProcessor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(12);
        }
        sb.append(getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(13);
        }
        setMirrorCheckingType(treeElement, JavaElementType.REFERENCE_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(15);
        }
        return psiTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return getQualifierExpression();
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceExpression:" + getText();
    }
}
