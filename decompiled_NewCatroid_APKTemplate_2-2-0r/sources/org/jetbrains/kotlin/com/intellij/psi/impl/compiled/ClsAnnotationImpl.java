package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsAnnotationImpl extends ClsRepositoryPsiElement<PsiAnnotationStub> implements Navigatable, PsiAnnotation {
    private final NotNullLazyValue<ClsAnnotationParameterListImpl> myParameterList;
    private final NotNullLazyValue<ClsJavaCodeReferenceElementImpl> myReferenceElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationImpl";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 5:
                objArr[0] = "qualifiedName";
                break;
            default:
                objArr[0] = "buffer";
                break;
        }
        if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i == 4) {
            objArr[1] = "getParameterList";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationImpl";
        } else {
            objArr[1] = "getQualifiedName";
        }
        switch (i) {
            case 1:
                objArr[2] = "setMirror";
                break;
            case 2:
            case 4:
            case 6:
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 5:
                objArr[2] = "hasQualifiedName";
                break;
            default:
                objArr[2] = "appendMirrorText";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ClsAnnotationImpl(PsiAnnotationStub psiAnnotationStub) {
        super(psiAnnotationStub);
        this.myReferenceElement = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6582xdf42cc30();
            }
        });
        this.myParameterList = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6583xc26e7f71();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsAnnotationImpl, reason: not valid java name */
    /* synthetic */ ClsJavaCodeReferenceElementImpl m6582xdf42cc30() {
        String text = getStub().getText();
        int iIndexOf = text.indexOf(40);
        return new ClsJavaCodeReferenceElementImpl(this, iIndexOf > 0 ? text.substring(1, iIndexOf) : text.substring(1));
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsAnnotationImpl, reason: not valid java name */
    /* synthetic */ ClsAnnotationParameterListImpl m6583xc26e7f71() {
        PsiNameValuePair[] attributes;
        if (getStub().getText().indexOf(40) > 0) {
            attributes = ((PsiAnnotationParameterList) PsiTreeUtil.getRequiredChildOfType(getStub().getPsiElement(), PsiAnnotationParameterList.class)).getAttributes();
        } else {
            attributes = PsiNameValuePair.EMPTY_ARRAY;
        }
        return new ClsAnnotationParameterListImpl(this, attributes);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        sb.append('@').append(this.myReferenceElement.getValue().getCanonicalText());
        appendText(getParameterList(), i, sb);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, null);
        PsiAnnotation psiAnnotation = (PsiAnnotation) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirror(getNameReferenceElement(), psiAnnotation.getNameReferenceElement());
        setMirror(getParameterList(), psiAnnotation.getParameterList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myReferenceElement.getValue(), getParameterList()};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotation(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        ClsAnnotationParameterListImpl value = this.myParameterList.getValue();
        if (value == null) {
            $$$reportNull$$$0(4);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public boolean hasQualifiedName(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        String text = getStub().getText();
        if (!text.startsWith(str, 1)) {
            return false;
        }
        int length = str.length() + 1;
        return text.length() == length || text.charAt(length) == '(';
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: getQualifiedName */
    public String getAnnotationFqName() {
        String canonicalText = this.myReferenceElement.getValue().getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(6);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        return this.myReferenceElement.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findAttributeValue(String str) {
        return PsiImplUtil.findAttributeValue(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findDeclaredAttributeValue(String str) {
        return PsiImplUtil.findDeclaredAttributeValue(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: setDeclaredAttributeValue */
    public <T extends PsiAnnotationMemberValue> T mo6097setDeclaredAttributeValue(String str, T t) {
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationOwner getOwner() {
        return (PsiAnnotationOwner) getMyParent();
    }
}
