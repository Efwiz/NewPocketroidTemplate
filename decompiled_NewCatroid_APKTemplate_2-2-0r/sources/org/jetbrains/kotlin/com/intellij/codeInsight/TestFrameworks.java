package org.jetbrains.kotlin.com.intellij.codeInsight;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TestFrameworks {
    public abstract boolean isTestClass(PsiClass psiClass);

    public static TestFrameworks getInstance() {
        return (TestFrameworks) ApplicationManager.getApplication().getService(TestFrameworks.class);
    }
}
