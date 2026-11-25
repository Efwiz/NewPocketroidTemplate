package org.jetbrains.kotlin.com.intellij.util;

import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.IconProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiIconUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/util/PsiIconUtil", "getProvidersIcon"));
    }

    public static Icon getProvidersIcon(PsiElement psiElement, int i) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        boolean zIsDumb = DumbService.getInstance(psiElement.getProject()).isDumb();
        for (IconProvider iconProvider : IconProvider.EXTENSION_POINT_NAME.getIterable()) {
            if (!zIsDumb || DumbService.isDumbAware(iconProvider)) {
                Icon icon = iconProvider.getIcon(psiElement, i);
                if (icon != null) {
                    return icon;
                }
            }
        }
        return null;
    }
}
