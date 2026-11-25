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
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsEnumConstantImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsJavaModuleImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsModifierListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsPackageAccessibilityStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParameterListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsProvidesStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRecordComponentImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRecordHeaderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsReferenceListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRequiresStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeParametersListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsUsesStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;

/* JADX INFO: loaded from: classes3.dex */
public class ClsStubPsiFactory extends StubPsiFactory {
    public static final ClsStubPsiFactory INSTANCE = new ClsStubPsiFactory();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotationParameterList createAnnotationParameterList(PsiAnnotationParameterListStub psiAnnotationParameterListStub) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiNameValuePair createNameValuePair(PsiNameValuePairStub psiNameValuePairStub) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClass createClass(PsiClassStub psiClassStub) {
        return ((psiClassStub instanceof PsiClassStubImpl) && ((PsiClassStubImpl) psiClassStub).isAnonymousInner()) ? new ClsAnonymousClass(psiClassStub) : new ClsClassImpl(psiClassStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiAnnotation createAnnotation(PsiAnnotationStub psiAnnotationStub) {
        return new ClsAnnotationImpl(psiAnnotationStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiClassInitializer createClassInitializer(PsiClassInitializerStub psiClassInitializerStub) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiReferenceList createClassReferenceList(PsiClassReferenceListStub psiClassReferenceListStub) {
        return new ClsReferenceListImpl(psiClassReferenceListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiField createField(PsiFieldStub psiFieldStub) {
        return psiFieldStub.isEnumConstant() ? new ClsEnumConstantImpl(psiFieldStub) : new ClsFieldImpl(psiFieldStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportList createImportList(PsiImportListStub psiImportListStub) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiImportStatementBase createImportStatement(PsiImportStatementStub psiImportStatementStub) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiMethod createMethod(PsiMethodStub psiMethodStub) {
        return new ClsMethodImpl(psiMethodStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiModifierList createModifierList(PsiModifierListStub psiModifierListStub) {
        return new ClsModifierListImpl(psiModifierListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameter createParameter(PsiParameterStub psiParameterStub) {
        return new ClsParameterImpl(psiParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiParameterList createParameterList(PsiParameterListStub psiParameterListStub) {
        return new ClsParameterListImpl(psiParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameter createTypeParameter(PsiTypeParameterStub psiTypeParameterStub) {
        return new ClsTypeParameterImpl(psiTypeParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiTypeParameterList createTypeParameterList(PsiTypeParameterListStub psiTypeParameterListStub) {
        return new ClsTypeParametersListImpl(psiTypeParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiJavaModule createModule(PsiJavaModuleStub psiJavaModuleStub) {
        return new ClsJavaModuleImpl(psiJavaModuleStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRequiresStatement createRequiresStatement(PsiRequiresStatementStub psiRequiresStatementStub) {
        return new ClsRequiresStatementImpl(psiRequiresStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiPackageAccessibilityStatement createPackageAccessibilityStatement(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        return new ClsPackageAccessibilityStatementImpl(psiPackageAccessibilityStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiUsesStatement createUsesStatement(PsiUsesStatementStub psiUsesStatementStub) {
        return new ClsUsesStatementImpl(psiUsesStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiProvidesStatement createProvidesStatement(PsiProvidesStatementStub psiProvidesStatementStub) {
        return new ClsProvidesStatementImpl(psiProvidesStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRecordComponent createRecordComponent(PsiRecordComponentStub psiRecordComponentStub) {
        return new ClsRecordComponentImpl(psiRecordComponentStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory
    public PsiRecordHeader createRecordHeader(PsiRecordHeaderStub psiRecordHeaderStub) {
        return new ClsRecordHeaderImpl(psiRecordHeaderStub);
    }
}
