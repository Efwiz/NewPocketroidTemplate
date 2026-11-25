package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
class ClsNameValuePairImpl extends ClsElementImpl implements PsiNameValuePair {
    private final PsiAnnotationMemberValue myMemberValue;
    private final ClsIdentifierImpl myNameIdentifier;
    private final ClsElementImpl myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "value";
                break;
            case 2:
                objArr[0] = "buffer";
                break;
            case 3:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsNameValuePairImpl";
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            case 7:
                objArr[0] = "newValue";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "getChildren";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsNameValuePairImpl";
        }
        switch (i) {
            case 2:
                objArr[2] = "appendMirrorText";
                break;
            case 3:
                objArr[2] = "setMirror";
                break;
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "accept";
                break;
            case 7:
                objArr[2] = "setValue";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public String getLiteralValue() {
        return null;
    }

    ClsNameValuePairImpl(ClsElementImpl clsElementImpl, String str, PsiAnnotationMemberValue psiAnnotationMemberValue) {
        if (clsElementImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (psiAnnotationMemberValue == null) {
            $$$reportNull$$$0(1);
        }
        this.myParent = clsElementImpl;
        this.myNameIdentifier = str != null ? new ClsIdentifierImpl(this, str) : null;
        this.myMemberValue = ClsParsingUtil.getMemberValue(psiAnnotationMemberValue, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        appendText(this.myNameIdentifier, 0, sb, " = ");
        appendText(this.myMemberValue, 0, sb);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        setMirrorCheckingType(treeElement, null);
        PsiNameValuePair psiNameValuePair = (PsiNameValuePair) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirrorIfPresent(getNameIdentifier(), psiNameValuePair.getNameIdentifier());
        setMirrorIfPresent(getValue(), psiNameValuePair.getValue());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ClsIdentifierImpl clsIdentifierImpl = this.myNameIdentifier;
        if (clsIdentifierImpl != null) {
            return new PsiElement[]{clsIdentifierImpl, this.myMemberValue};
        }
        return new PsiElement[]{this.myMemberValue};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitNameValuePair(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiIdentifier getNameIdentifier() {
        return this.myNameIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        ClsIdentifierImpl clsIdentifierImpl = this.myNameIdentifier;
        if (clsIdentifierImpl != null) {
            return clsIdentifierImpl.getText();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiAnnotationMemberValue getValue() {
        return this.myMemberValue;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    /* JADX INFO: renamed from: setValue */
    public PsiAnnotationMemberValue mo6100setValue(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        if (psiAnnotationMemberValue == null) {
            $$$reportNull$$$0(7);
        }
        throw cannotModifyException(this);
    }
}
