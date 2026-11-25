package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiAnnotationMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiAnonymousClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassInitializerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiEnumConstantImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiEnumConstantInitializerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFieldImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStaticStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiModifierListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiPackageAccessibilityStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiParameterListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiProvidesStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiRecordComponentImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiRecordHeaderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiReferenceListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiRequiresStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiUsesStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiTypeParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiTypeParameterListImpl;

/* JADX INFO: loaded from: classes3.dex */
public class SourceStubPsiFactory extends StubPsiFactory {
    public static final SourceStubPsiFactory INSTANCE = new SourceStubPsiFactory();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClass createClass(PsiClassStub psiClassStub) {
        if (psiClassStub.isEnumConstantInitializer()) {
            return new PsiEnumConstantInitializerImpl(psiClassStub);
        }
        return psiClassStub.isAnonymous() ? new PsiAnonymousClassImpl(psiClassStub) : new PsiClassImpl(psiClassStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotation createAnnotation(PsiAnnotationStub psiAnnotationStub) {
        return new PsiAnnotationImpl(psiAnnotationStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClassInitializer createClassInitializer(PsiClassInitializerStub psiClassInitializerStub) {
        return new PsiClassInitializerImpl(psiClassInitializerStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiReferenceList createClassReferenceList(PsiClassReferenceListStub psiClassReferenceListStub) {
        return new PsiReferenceListImpl(psiClassReferenceListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiField createField(PsiFieldStub psiFieldStub) {
        return psiFieldStub.isEnumConstant() ? new PsiEnumConstantImpl(psiFieldStub) : new PsiFieldImpl(psiFieldStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportList createImportList(PsiImportListStub psiImportListStub) {
        return new PsiImportListImpl(psiImportListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportStatementBase createImportStatement(PsiImportStatementStub psiImportStatementStub) {
        return psiImportStatementStub.isStatic() ? new PsiImportStaticStatementImpl(psiImportStatementStub) : new PsiImportStatementImpl(psiImportStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiMethod createMethod(PsiMethodStub psiMethodStub) {
        return psiMethodStub.isAnnotationMethod() ? new PsiAnnotationMethodImpl(psiMethodStub) : new PsiMethodImpl(psiMethodStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiModifierList createModifierList(PsiModifierListStub psiModifierListStub) {
        return new PsiModifierListImpl(psiModifierListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameter createParameter(PsiParameterStub psiParameterStub) {
        return new PsiParameterImpl(psiParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameterList createParameterList(PsiParameterListStub psiParameterListStub) {
        return new PsiParameterListImpl(psiParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameter createTypeParameter(PsiTypeParameterStub psiTypeParameterStub) {
        return new PsiTypeParameterImpl(psiTypeParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameterList createTypeParameterList(PsiTypeParameterListStub psiTypeParameterListStub) {
        return new PsiTypeParameterListImpl(psiTypeParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotationParameterList createAnnotationParameterList(PsiAnnotationParameterListStub psiAnnotationParameterListStub) {
        return new PsiAnnotationParamListImpl(psiAnnotationParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiNameValuePair createNameValuePair(PsiNameValuePairStub psiNameValuePairStub) {
        return new PsiNameValuePairImpl(psiNameValuePairStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiJavaModule createModule(PsiJavaModuleStub psiJavaModuleStub) {
        return new PsiJavaModuleImpl(psiJavaModuleStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRequiresStatement createRequiresStatement(PsiRequiresStatementStub psiRequiresStatementStub) {
        return new PsiRequiresStatementImpl(psiRequiresStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiPackageAccessibilityStatement createPackageAccessibilityStatement(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        return new PsiPackageAccessibilityStatementImpl(psiPackageAccessibilityStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiUsesStatement createUsesStatement(PsiUsesStatementStub psiUsesStatementStub) {
        return new PsiUsesStatementImpl(psiUsesStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiProvidesStatement createProvidesStatement(PsiProvidesStatementStub psiProvidesStatementStub) {
        return new PsiProvidesStatementImpl(psiProvidesStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRecordComponent createRecordComponent(PsiRecordComponentStub psiRecordComponentStub) {
        return new PsiRecordComponentImpl(psiRecordComponentStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRecordHeader createRecordHeader(PsiRecordHeaderStub psiRecordHeaderStub) {
        return new PsiRecordHeaderImpl(psiRecordHeaderStub);
    }
}
