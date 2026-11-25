package org.jetbrains.kotlin.com.intellij.openapi.projectRoots;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JavaVersionService {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 4) {
            objArr[0] = "version";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/projectRoots/JavaVersionService";
        if (i == 2) {
            objArr[2] = "getJavaSdkVersion";
        } else if (i == 3 || i == 4) {
            objArr[2] = "isCompilerVersionAtLeast";
        } else {
            objArr[2] = "isAtLeast";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static JavaVersionService getInstance() {
        return (JavaVersionService) ApplicationManager.getApplication().getService(JavaVersionService.class);
    }

    public boolean isAtLeast(PsiElement psiElement, JavaSdkVersion javaSdkVersion) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (javaSdkVersion == null) {
            $$$reportNull$$$0(1);
        }
        return PsiUtil.getLanguageLevel(psiElement).isAtLeast(javaSdkVersion.getMaxLanguageLevel());
    }
}
