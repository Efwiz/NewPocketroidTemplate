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

/* JADX INFO: loaded from: classes3.dex */
public abstract class StubPsiFactory {
    public abstract PsiAnnotation createAnnotation(PsiAnnotationStub psiAnnotationStub);

    public abstract PsiAnnotationParameterList createAnnotationParameterList(PsiAnnotationParameterListStub psiAnnotationParameterListStub);

    public abstract PsiClass createClass(PsiClassStub psiClassStub);

    public abstract PsiClassInitializer createClassInitializer(PsiClassInitializerStub psiClassInitializerStub);

    public abstract PsiReferenceList createClassReferenceList(PsiClassReferenceListStub psiClassReferenceListStub);

    public abstract PsiField createField(PsiFieldStub psiFieldStub);

    public abstract PsiImportList createImportList(PsiImportListStub psiImportListStub);

    public abstract PsiImportStatementBase createImportStatement(PsiImportStatementStub psiImportStatementStub);

    public abstract PsiMethod createMethod(PsiMethodStub psiMethodStub);

    public abstract PsiModifierList createModifierList(PsiModifierListStub psiModifierListStub);

    public PsiJavaModule createModule(PsiJavaModuleStub psiJavaModuleStub) {
        return null;
    }

    public abstract PsiNameValuePair createNameValuePair(PsiNameValuePairStub psiNameValuePairStub);

    public PsiPackageAccessibilityStatement createPackageAccessibilityStatement(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        return null;
    }

    public abstract PsiParameter createParameter(PsiParameterStub psiParameterStub);

    public abstract PsiParameterList createParameterList(PsiParameterListStub psiParameterListStub);

    public PsiProvidesStatement createProvidesStatement(PsiProvidesStatementStub psiProvidesStatementStub) {
        return null;
    }

    public PsiRecordComponent createRecordComponent(PsiRecordComponentStub psiRecordComponentStub) {
        return null;
    }

    public PsiRecordHeader createRecordHeader(PsiRecordHeaderStub psiRecordHeaderStub) {
        return null;
    }

    public PsiRequiresStatement createRequiresStatement(PsiRequiresStatementStub psiRequiresStatementStub) {
        return null;
    }

    public abstract PsiTypeParameter createTypeParameter(PsiTypeParameterStub psiTypeParameterStub);

    public abstract PsiTypeParameterList createTypeParameterList(PsiTypeParameterListStub psiTypeParameterListStub);

    public PsiUsesStatement createUsesStatement(PsiUsesStatementStub psiUsesStatementStub) {
        return null;
    }
}
