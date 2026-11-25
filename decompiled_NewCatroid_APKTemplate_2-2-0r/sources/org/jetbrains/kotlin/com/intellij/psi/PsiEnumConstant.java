package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmEnumField;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiEnumConstant extends JvmEnumField, PsiConstructorCall, PsiField {
    PsiExpressionList getArgumentList();

    PsiEnumConstantInitializer getInitializingClass();

    PsiEnumConstantInitializer getOrCreateInitializingClass();
}
