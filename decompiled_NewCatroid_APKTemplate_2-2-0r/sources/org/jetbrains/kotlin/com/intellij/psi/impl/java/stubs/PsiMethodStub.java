package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMethodStub extends PsiMemberStub<PsiMethod> {
    PsiParameterStub findParameter(int i);

    String getDefaultValueText();

    TypeInfo getReturnTypeText();

    boolean isAnnotationMethod();

    boolean isConstructor();

    boolean isVarArgs();
}
