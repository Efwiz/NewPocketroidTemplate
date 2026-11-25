package org.jetbrains.kotlin.asJava.builder;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsEnumConstantImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.ClsStubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassInitializerStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiNameValuePairStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes2.dex */
public class ClsWrapperStubPsiFactory extends StubPsiFactory {
    private final StubPsiFactory delegate = new ClsStubPsiFactory();
    public static final Key<LightElementOrigin> ORIGIN = Key.create("ORIGIN");
    public static final ClsWrapperStubPsiFactory INSTANCE = new ClsWrapperStubPsiFactory();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "stub";
        objArr[1] = "org/jetbrains/kotlin/asJava/builder/ClsWrapperStubPsiFactory";
        if (i != 1) {
            objArr[2] = "createClass";
        } else {
            objArr[2] = "getOriginalElement";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ClsWrapperStubPsiFactory() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClass createClass(PsiClassStub psiClassStub) {
        if (psiClassStub == null) {
            $$$reportNull$$$0(0);
        }
        final PsiElement originalElement = getOriginalElement(psiClassStub);
        return new ClsClassImpl(psiClassStub) { // from class: org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/asJava/builder/ClsWrapperStubPsiFactory$1", "getNavigationElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl
            public PsiClass getSourceMirrorClass() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public PsiElement getNavigationElement() {
                PsiElement psiElement = originalElement;
                if (psiElement != null) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    return psiElement;
                }
                PsiElement navigationElement = super.getNavigationElement();
                if (navigationElement == null) {
                    $$$reportNull$$$0(1);
                }
                return navigationElement;
            }
        };
    }

    public static PsiElement getOriginalElement(StubElement stubElement) {
        if (stubElement == null) {
            $$$reportNull$$$0(1);
        }
        LightElementOrigin lightElementOrigin = (LightElementOrigin) ((StubBase) stubElement).getUserData(ORIGIN);
        if (lightElementOrigin != null) {
            return lightElementOrigin.getOriginalElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotation createAnnotation(PsiAnnotationStub psiAnnotationStub) {
        return this.delegate.createAnnotation(psiAnnotationStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClassInitializer createClassInitializer(PsiClassInitializerStub psiClassInitializerStub) {
        return this.delegate.createClassInitializer(psiClassInitializerStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiReferenceList createClassReferenceList(PsiClassReferenceListStub psiClassReferenceListStub) {
        return this.delegate.createClassReferenceList(psiClassReferenceListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiField createField(PsiFieldStub psiFieldStub) {
        final PsiElement originalElement = getOriginalElement(psiFieldStub);
        if (originalElement == null) {
            return this.delegate.createField(psiFieldStub);
        }
        if (psiFieldStub.isEnumConstant()) {
            return new ClsEnumConstantImpl(psiFieldStub) { // from class: org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/asJava/builder/ClsWrapperStubPsiFactory$2", "getNavigationElement"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                public PsiElement getNavigationElement() {
                    PsiElement psiElement = originalElement;
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    return psiElement;
                }
            };
        }
        return new ClsFieldImpl(psiFieldStub) { // from class: org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/asJava/builder/ClsWrapperStubPsiFactory$3", "getNavigationElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public PsiElement getNavigationElement() {
                PsiElement psiElement = originalElement;
                if (psiElement == null) {
                    $$$reportNull$$$0(0);
                }
                return psiElement;
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportList createImportList(PsiImportListStub psiImportListStub) {
        return this.delegate.createImportList(psiImportListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportStatementBase createImportStatement(PsiImportStatementStub psiImportStatementStub) {
        return this.delegate.createImportStatement(psiImportStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiMethod createMethod(PsiMethodStub psiMethodStub) {
        return this.delegate.createMethod(psiMethodStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiModifierList createModifierList(PsiModifierListStub psiModifierListStub) {
        return this.delegate.createModifierList(psiModifierListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameter createParameter(PsiParameterStub psiParameterStub) {
        return this.delegate.createParameter(psiParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameterList createParameterList(PsiParameterListStub psiParameterListStub) {
        return this.delegate.createParameterList(psiParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameter createTypeParameter(PsiTypeParameterStub psiTypeParameterStub) {
        return this.delegate.createTypeParameter(psiTypeParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameterList createTypeParameterList(PsiTypeParameterListStub psiTypeParameterListStub) {
        return this.delegate.createTypeParameterList(psiTypeParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotationParameterList createAnnotationParameterList(PsiAnnotationParameterListStub psiAnnotationParameterListStub) {
        return this.delegate.createAnnotationParameterList(psiAnnotationParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiNameValuePair createNameValuePair(PsiNameValuePairStub psiNameValuePairStub) {
        return this.delegate.createNameValuePair(psiNameValuePairStub);
    }
}
