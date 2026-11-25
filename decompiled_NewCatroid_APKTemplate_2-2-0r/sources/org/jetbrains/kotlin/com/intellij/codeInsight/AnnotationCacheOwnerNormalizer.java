package org.jetbrains.kotlin.com.intellij.codeInsight;

import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AnnotationCacheOwnerNormalizer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "listOwner";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationCacheOwnerNormalizer";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationCacheOwnerNormalizer";
        } else {
            objArr[1] = "normalize";
        }
        if (i != 1) {
            objArr[2] = "normalize";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    protected abstract PsiModifierListOwner doNormalize(PsiModifierListOwner psiModifierListOwner);

    public static PsiModifierListOwner normalize(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(0);
        }
        AnnotationCacheOwnerNormalizer annotationCacheOwnerNormalizer = (AnnotationCacheOwnerNormalizer) psiModifierListOwner.getProject().getService(AnnotationCacheOwnerNormalizer.class);
        if (annotationCacheOwnerNormalizer != null) {
            psiModifierListOwner = annotationCacheOwnerNormalizer.doNormalize(psiModifierListOwner);
        }
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(1);
        }
        return psiModifierListOwner;
    }
}
