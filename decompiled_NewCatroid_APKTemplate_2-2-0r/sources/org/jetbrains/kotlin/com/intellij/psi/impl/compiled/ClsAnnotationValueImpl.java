package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
abstract class ClsAnnotationValueImpl extends ClsElementImpl implements Navigatable, PsiAnnotation {
    private final ClsAnnotationParameterListImpl myParameterList;
    private final ClsElementImpl myParent;
    private final ClsJavaCodeReferenceElementImpl myReferenceElement;

    /* JADX WARN: Code duplicated, block: B:20:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "buffer";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationValueImpl";
        } else if (i == 4) {
            objArr[0] = "visitor";
        } else if (i != 5) {
            objArr[0] = "parent";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationValueImpl";
        }
        if (i == 3) {
            objArr[1] = "getChildren";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnnotationValueImpl";
        } else {
            objArr[1] = "getParameterList";
        }
        if (i == 1) {
            objArr[2] = "appendMirrorText";
        } else if (i == 2) {
            objArr[2] = "setMirror";
        } else if (i != 3) {
            if (i == 4) {
                objArr[2] = "accept";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract ClsAnnotationParameterListImpl createParameterList();

    protected abstract ClsJavaCodeReferenceElementImpl createReference();

    ClsAnnotationValueImpl(ClsElementImpl clsElementImpl) {
        if (clsElementImpl == null) {
            $$$reportNull$$$0(0);
        }
        this.myParent = clsElementImpl;
        this.myReferenceElement = createReference();
        this.myParameterList = createParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(this.myReferenceElement.getCanonicalText());
        this.myParameterList.appendMirrorText(i, sb);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        setMirrorCheckingType(treeElement, null);
        PsiAnnotation psiAnnotation = (PsiAnnotation) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirror(getNameReferenceElement(), psiAnnotation.getNameReferenceElement());
        setMirror(getParameterList(), psiAnnotation.getParameterList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myReferenceElement, this.myParameterList};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotation(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        ClsAnnotationParameterListImpl clsAnnotationParameterListImpl = this.myParameterList;
        if (clsAnnotationParameterListImpl == null) {
            $$$reportNull$$$0(5);
        }
        return clsAnnotationParameterListImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: getQualifiedName */
    public String getAnnotationFqName() {
        ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl = this.myReferenceElement;
        if (clsJavaCodeReferenceElementImpl != null) {
            return clsJavaCodeReferenceElementImpl.getCanonicalText();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        return this.myReferenceElement;
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
}
