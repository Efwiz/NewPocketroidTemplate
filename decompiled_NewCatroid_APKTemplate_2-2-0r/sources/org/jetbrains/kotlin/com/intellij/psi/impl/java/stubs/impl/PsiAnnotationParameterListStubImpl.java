package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnnotationParameterListStubImpl extends StubBase<PsiAnnotationParameterList> implements PsiAnnotationParameterListStub {
    public PsiAnnotationParameterListStubImpl(StubElement stubElement) {
        super(stubElement, JavaStubElementTypes.ANNOTATION_PARAMETER_LIST);
    }
}
