package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsAnnotationParameterListImpl extends ClsElementImpl implements PsiAnnotationParameterList {
    private final ClsNameValuePairImpl[] myAttributes;
    private final PsiAnnotation myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "psiAttributes";
                break;
            case 2:
                objArr[0] = "buffer";
                break;
            case 3:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationParameterListImpl";
                break;
            case 5:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 4) {
            objArr[1] = "getChildren";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationParameterListImpl";
        } else {
            objArr[1] = "getAttributes";
        }
        if (i == 2) {
            objArr[2] = "appendMirrorText";
        } else if (i == 3) {
            objArr[2] = "setMirror";
        } else if (i != 4) {
            if (i == 5) {
                objArr[2] = "accept";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ClsAnnotationParameterListImpl(PsiAnnotation psiAnnotation, PsiNameValuePair[] psiNameValuePairArr) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(0);
        }
        if (psiNameValuePairArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myParent = psiAnnotation;
        this.myAttributes = new ClsNameValuePairImpl[psiNameValuePairArr.length];
        for (int i = 0; i < psiNameValuePairArr.length; i++) {
            String name2 = psiNameValuePairArr[i].getName();
            PsiAnnotationMemberValue value = psiNameValuePairArr[i].getValue();
            if (value == null) {
                Logger.getInstance(getClass()).error("name=" + name2 + " anno=[" + (psiAnnotation instanceof ClsAnnotationImpl ? ((ClsAnnotationImpl) psiAnnotation).getStub().getText() : psiAnnotation.getText()) + "] file=" + psiAnnotation.getContainingFile());
                value = new ClsLiteralExpressionImpl(this, "null", PsiType.NULL, null);
            }
            if (psiNameValuePairArr.length == 1 && "value".equals(name2)) {
                name2 = null;
            }
            this.myAttributes[i] = new ClsNameValuePairImpl(this, name2, value);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        if (this.myAttributes.length != 0) {
            sb.append("(");
            for (int i2 = 0; i2 < this.myAttributes.length; i2++) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                this.myAttributes[i2].appendMirrorText(i, sb);
            }
            sb.append(Constant.AFTER_QUTO);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        setMirrorCheckingType(treeElement, null);
        setMirrors(this.myAttributes, ((PsiAnnotationParameterList) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getAttributes());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ClsNameValuePairImpl[] clsNameValuePairImplArr = this.myAttributes;
        if (clsNameValuePairImplArr == null) {
            $$$reportNull$$$0(4);
        }
        return clsNameValuePairImplArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotationParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList
    public PsiNameValuePair[] getAttributes() {
        ClsNameValuePairImpl[] clsNameValuePairImplArr = this.myAttributes;
        if (clsNameValuePairImplArr == null) {
            $$$reportNull$$$0(6);
        }
        return clsNameValuePairImplArr;
    }
}
