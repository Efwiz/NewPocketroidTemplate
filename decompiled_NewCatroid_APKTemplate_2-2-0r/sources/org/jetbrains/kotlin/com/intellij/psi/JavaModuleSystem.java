package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaModuleSystem {
    public static final ExtensionPointName<JavaModuleSystem> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.javaModuleSystem");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "target";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JavaModuleSystem";
        objArr[2] = "isAccessible";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    String getName();

    boolean isAccessible(String str, PsiFile psiFile, PsiElement psiElement);

    default boolean isAccessible(PsiClass psiClass, PsiElement psiElement) {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        PsiFile containingFile = psiClass.getContainingFile();
        if (containingFile == null) {
            return true;
        }
        PsiUtilCore.ensureValid(containingFile);
        String packageName = PsiUtil.getPackageName(psiClass);
        return packageName == null || isAccessible(packageName, containingFile, psiElement);
    }
}
