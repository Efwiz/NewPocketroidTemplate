package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public abstract class JavaCodeStyleSettingsFacade {
    @Deprecated
    public abstract boolean isGenerateFinalParameters();

    @Deprecated
    public abstract boolean useFQClassNames();

    @Deprecated
    public static JavaCodeStyleSettingsFacade getInstance(Project project) {
        return (JavaCodeStyleSettingsFacade) project.getService(JavaCodeStyleSettingsFacade.class);
    }
}
