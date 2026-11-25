package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import io.netty.util.internal.StringUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.ModifierFlags;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class ClsModifierListImpl extends ClsRepositoryPsiElement<PsiModifierListStub> implements PsiModifierList {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 5:
            case 6:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                i2 = 3;
                break;
            case 5:
            case 6:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[0] = "name";
                break;
            case 5:
            case 6:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsModifierListImpl";
                break;
            case 7:
            case 8:
                objArr[0] = "qualifiedName";
                break;
            case 9:
                objArr[0] = "buffer";
                break;
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsModifierListImpl";
                break;
            case 5:
                objArr[1] = "getAnnotations";
                break;
            case 6:
                objArr[1] = "getApplicableAnnotations";
                break;
            default:
                objArr[1] = "getChildren";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "hasModifierProperty";
                break;
            case 2:
                objArr[2] = "hasExplicitModifier";
                break;
            case 3:
                objArr[2] = "setModifierProperty";
                break;
            case 4:
                objArr[2] = "checkSetModifierProperty";
                break;
            case 7:
                objArr[2] = "findAnnotation";
                break;
            case 8:
                objArr[2] = "addAnnotation";
                break;
            case 9:
                objArr[2] = "appendMirrorText";
                break;
            case 10:
                objArr[2] = "setMirror";
                break;
            case 11:
                objArr[2] = "accept";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                throw new IllegalArgumentException(str2);
            case 5:
            case 6:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public ClsModifierListImpl(PsiModifierListStub psiModifierListStub) {
        super(psiModifierListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(0);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return ModifierFlags.hasModifierProperty(str, getStub().getModifiersMask());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasExplicitModifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void setModifierProperty(String str, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void checkSetModifierProperty(String str, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) getStub().getChildrenByType(JavaStubElementTypes.ANNOTATION, PsiAnnotation.ARRAY_FACTORY);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(6);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return PsiImplUtil.findAnnotation(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(9);
        }
        PsiElement parent = getLightParent();
        PsiAnnotation[] annotations = getAnnotations();
        boolean z = parent instanceof PsiClass;
        boolean z2 = false;
        boolean z3 = z || (parent instanceof PsiMethod) || (parent instanceof PsiField) || (parent instanceof PsiJavaModule);
        for (PsiAnnotation psiAnnotation : annotations) {
            appendText(psiAnnotation, i, sb, z3 ? "go_to_next_line_and_indent" : " ");
        }
        boolean z4 = z && ((PsiClass) parent).isInterface();
        boolean z5 = z && ((PsiClass) parent).isEnum();
        boolean z6 = z && (parent.getLightParent() instanceof PsiClass) && ((PsiClass) parent.getLightParent()).isInterface();
        boolean z7 = (parent instanceof PsiMethod) && (parent.getLightParent() instanceof PsiClass) && ((PsiClass) parent.getLightParent()).isInterface();
        if ((parent instanceof PsiField) && (parent.getLightParent() instanceof PsiClass) && ((PsiClass) parent.getLightParent()).isInterface()) {
            z2 = true;
        }
        boolean z8 = parent instanceof PsiEnumConstant;
        if (hasModifierProperty("public") && !z7 && !z2 && !z6 && !z8) {
            sb.append("public").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("protected")) {
            sb.append("protected").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("private")) {
            sb.append("private").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("static") && !z2 && !z8) {
            sb.append("static").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("abstract") && !z4 && !z7) {
            sb.append("abstract").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("final") && !z5 && !z2 && !z8) {
            sb.append("final").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("native")) {
            sb.append("native").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("synchronized")) {
            sb.append("synchronized").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("transient")) {
            sb.append("transient").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("volatile")) {
            sb.append("volatile").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("strictfp")) {
            sb.append("strictfp").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("default")) {
            sb.append("default").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("open")) {
            sb.append("open").append(StringUtil.SPACE);
        }
        if (hasModifierProperty("transitive")) {
            sb.append("transitive").append(StringUtil.SPACE);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(10);
        }
        setMirrorCheckingType(treeElement, JavaElementType.MODIFIER_LIST);
        PsiAnnotation[] annotations = getAnnotations();
        PsiAnnotation[] annotations2 = ((PsiModifierList) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getAnnotations();
        if (annotations.length == annotations2.length) {
            setMirrors(annotations, annotations2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModifierList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiModifierList";
    }
}
