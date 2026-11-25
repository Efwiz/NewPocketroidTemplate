package org.jetbrains.kotlin.com.intellij.psi.augment;

import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiExtensionMethod extends PsiMethod {
    PsiMethod getTargetMethod();

    PsiParameter getTargetParameter(int i);

    PsiParameter getTargetReceiverParameter();
}
