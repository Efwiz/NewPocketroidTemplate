package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.PairFunction;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnnotationImpl extends JavaStubPsiElement<PsiAnnotationStub> implements PsiAnnotation {
    private static final PairFunction<Project, String, PsiAnnotation> ANNOTATION_CREATOR = new PairFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.PairFunction
        public final Object fun(Object obj, Object obj2) {
            return JavaPsiFacade.getElementFactory((Project) obj).createAnnotationFromText((String) obj2, null);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "qualifiedName";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAnnotationImpl";
        } else {
            objArr[0] = "annoText";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAnnotationImpl";
        } else if (i != 4) {
            objArr[1] = "getParameterList";
        } else {
            objArr[1] = "getAnnotationShortName";
        }
        if (i == 1) {
            objArr[2] = "hasQualifiedName";
        } else if (i == 2) {
            objArr[2] = "accept";
        } else if (i == 3) {
            objArr[2] = "getAnnotationShortName";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiAnnotationImpl(PsiAnnotationStub psiAnnotationStub) {
        super(psiAnnotationStub, JavaStubElementTypes.ANNOTATION);
    }

    public PsiAnnotationImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        PsiAnnotationStub psiAnnotationStub = (PsiAnnotationStub) getStub();
        return (PsiJavaCodeReferenceElement) PsiTreeUtil.getChildOfType(psiAnnotationStub != null ? psiAnnotationStub.getPsiElement() : this, PsiJavaCodeReferenceElement.class);
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
        return (T) PsiImplUtil.setDeclaredAttributeValue(this, str, t, ANNOTATION_CREATOR);
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAnnotation";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        PsiAnnotationParameterList psiAnnotationParameterList = (PsiAnnotationParameterList) getRequiredStubOrPsiChild(JavaStubElementTypes.ANNOTATION_PARAMETER_LIST);
        if (psiAnnotationParameterList == null) {
            $$$reportNull$$$0(0);
        }
        return psiAnnotationParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: getQualifiedName */
    public String getAnnotationFqName() {
        PsiJavaCodeReferenceElement nameReferenceElement = getNameReferenceElement();
        if (nameReferenceElement == null) {
            return null;
        }
        return nameReferenceElement.getCanonicalText();
    }

    private String getShortName() {
        PsiAnnotationStub psiAnnotationStub = (PsiAnnotationStub) getStub();
        if (psiAnnotationStub != null) {
            return getAnnotationShortName(psiAnnotationStub.getText());
        }
        PsiJavaCodeReferenceElement nameReferenceElement = getNameReferenceElement();
        if (nameReferenceElement == null) {
            return null;
        }
        return nameReferenceElement.getCustomReferenceName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public boolean hasQualifiedName(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return StringUtil.getShortName(str).equals(getShortName()) && super.hasQualifiedName(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotation(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationOwner getOwner() {
        PsiTypeElement typeElement;
        PsiElement psiElementMo7392getNameIdentifier;
        PsiElement parent = getMyParent();
        if (parent instanceof PsiTypeElementImpl) {
            PsiTypeElement psiTypeElement = (PsiTypeElement) parent;
            PsiType type = psiTypeElement.getType();
            if (type instanceof PsiClassType) {
                PsiJavaCodeReferenceElement innermostComponentReferenceElement = psiTypeElement.getInnermostComponentReferenceElement();
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = innermostComponentReferenceElement;
                while (psiJavaCodeReferenceElement != null && psiJavaCodeReferenceElement.isQualified()) {
                    psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) ObjectUtils.tryCast(psiJavaCodeReferenceElement.getQualifier(), PsiJavaCodeReferenceElement.class);
                }
                if (psiJavaCodeReferenceElement != null && psiJavaCodeReferenceElement != innermostComponentReferenceElement) {
                    return new PsiClassReferenceType(psiJavaCodeReferenceElement, null).annotate(type.getAnnotationProvider());
                }
            } else if (type instanceof PsiArrayType) {
                for (PsiElement prevSibling = getPrevSibling(); prevSibling != null; prevSibling = prevSibling.getPrevSibling()) {
                    if (PsiUtil.isJavaToken(prevSibling, JavaTokenType.LBRACKET)) {
                        type = ((PsiArrayType) type).getComponentType();
                    }
                }
            }
            return type;
        }
        if (parent instanceof PsiAnnotationOwner) {
            return (PsiAnnotationOwner) parent;
        }
        if (parent instanceof PsiNewExpression) {
            return ((PsiNewExpression) parent).getOwner(this);
        }
        if (parent instanceof PsiReferenceExpression) {
            if (parent.getMyParent() instanceof PsiMethodReferenceExpression) {
                return new PsiClassReferenceType((PsiJavaCodeReferenceElement) parent, null);
            }
        } else if (parent instanceof PsiJavaCodeReferenceElement) {
            PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(parent, PsiJavaCodeReferenceElement.class);
            if ((psiElementSkipParentsOfType instanceof PsiReferenceList) || (psiElementSkipParentsOfType instanceof PsiNewExpression) || (psiElementSkipParentsOfType instanceof PsiTypeElement) || (psiElementSkipParentsOfType instanceof PsiAnonymousClass)) {
                return new PsiClassReferenceType((PsiJavaCodeReferenceElement) parent, null);
            }
        }
        if (parent instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) parent;
            typeElement = psiMethod.getReturnTypeElement();
            psiElementMo7392getNameIdentifier = psiMethod.getParameterList();
        } else if ((parent instanceof PsiField) || (parent instanceof PsiParameter) || (parent instanceof PsiLocalVariable)) {
            PsiVariable psiVariable = (PsiVariable) parent;
            typeElement = psiVariable.getTypeElement();
            psiElementMo7392getNameIdentifier = psiVariable.mo7392getNameIdentifier();
        } else {
            psiElementMo7392getNameIdentifier = null;
            typeElement = null;
        }
        if (typeElement == null || psiElementMo7392getNameIdentifier == null) {
            return null;
        }
        return JavaSharedImplUtil.getType(typeElement, psiElementMo7392getNameIdentifier, this);
    }

    public static String getAnnotationShortName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        int iIndexOf = str.indexOf(64);
        int iIndexOf2 = str.indexOf(40);
        int i = iIndexOf + 1;
        if (iIndexOf2 <= 0) {
            iIndexOf2 = str.length();
        }
        String shortName = StringUtil.getShortName(PsiNameHelper.getQualifiedClassName(str.substring(i, iIndexOf2), true));
        if (shortName == null) {
            $$$reportNull$$$0(4);
        }
        return shortName;
    }
}
