package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaFileCodeStyleFacade extends CodeStyleSettingsFacade {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiFile", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaFileCodeStyleFacade", "forContext"));
    }

    int getNamesCountToUseImportOnDemand();

    boolean isGenerateFinalLocals();

    boolean isGenerateFinalParameters();

    boolean isJavaDocLeadingAsterisksEnabled();

    boolean isToImportOnDemand(String str);

    boolean useFQClassNames();

    static JavaFileCodeStyleFacade forContext(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        return ((JavaFileCodeStyleFacadeFactory) ApplicationManager.getApplication().getService(JavaFileCodeStyleFacadeFactory.class)).createFacade(psiFile);
    }
}
