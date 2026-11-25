package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAnnotationStub extends StubElement<PsiAnnotation> {
    public static final PsiAnnotationStub[] EMPTY_ARRAY = new PsiAnnotationStub[0];

    PsiAnnotation getPsiElement();

    String getText();
}
