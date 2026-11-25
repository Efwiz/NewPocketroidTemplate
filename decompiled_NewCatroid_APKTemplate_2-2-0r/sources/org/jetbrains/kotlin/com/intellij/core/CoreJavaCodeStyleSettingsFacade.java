package org.jetbrains.kotlin.com.intellij.core;

import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade;

/* JADX INFO: loaded from: classes3.dex */
public class CoreJavaCodeStyleSettingsFacade extends JavaCodeStyleSettingsFacade {
    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade
    public boolean isGenerateFinalParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade
    public boolean useFQClassNames() {
        return false;
    }
}
