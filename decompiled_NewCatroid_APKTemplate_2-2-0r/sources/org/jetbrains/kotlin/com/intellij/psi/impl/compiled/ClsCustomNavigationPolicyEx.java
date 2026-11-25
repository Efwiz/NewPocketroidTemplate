package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public abstract class ClsCustomNavigationPolicyEx implements ClsCustomNavigationPolicy {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsCustomNavigationPolicyEx", "getFileNavigationElement"));
    }

    public PsiFile getFileNavigationElement(ClsFileImpl clsFileImpl) {
        if (clsFileImpl == null) {
            $$$reportNull$$$0(0);
        }
        return (PsiFile) getNavigationElement(clsFileImpl);
    }
}
