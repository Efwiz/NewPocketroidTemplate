package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmModifier;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiModifierListOwner extends PsiElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "fqn";
        } else if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner";
        } else {
            objArr[0] = "modifier";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner";
        } else {
            objArr[1] = "getAnnotations";
        }
        if (i == 1) {
            objArr[2] = "getAnnotation";
        } else if (i == 2) {
            objArr[2] = "hasAnnotation";
        } else if (i == 3) {
            objArr[2] = "hasModifier";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    /* JADX INFO: renamed from: getModifierList */
    PsiModifierList mo6095getModifierList();

    boolean hasModifierProperty(String str);

    /* JADX INFO: renamed from: getAnnotations */
    default PsiAnnotation[] mo6531getAnnotations() {
        PsiAnnotation[] listAnnotations = PsiJvmConversionHelper.getListAnnotations(this);
        if (listAnnotations == null) {
            $$$reportNull$$$0(0);
        }
        return listAnnotations;
    }

    /* JADX INFO: renamed from: getAnnotation */
    default PsiAnnotation mo6530getAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return PsiJvmConversionHelper.getListAnnotation(this, str);
    }

    default boolean hasAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return PsiJvmConversionHelper.hasListAnnotation(this, str);
    }

    default boolean hasModifier(JvmModifier jvmModifier) {
        if (jvmModifier == null) {
            $$$reportNull$$$0(3);
        }
        return PsiJvmConversionHelper.hasListModifier(this, jvmModifier);
    }
}
