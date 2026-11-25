package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.Comparator;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiClassUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "scope";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiClassUtil";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiClassUtil";
        } else {
            objArr[1] = "createScopeComparator";
        }
        if (i != 1) {
            objArr[2] = "createScopeComparator";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private PsiClassUtil() {
    }

    public static boolean isRunnableClass(PsiClass psiClass, boolean z) {
        return isRunnableClass(psiClass, z, true);
    }

    public static boolean isRunnableClass(PsiClass psiClass, boolean z, boolean z2) {
        if (psiClass instanceof PsiAnonymousClass) {
            return false;
        }
        if ((z && !psiClass.hasModifierProperty("public") && (z2 || !psiClass.hasModifierProperty("abstract"))) || psiClass.hasModifierProperty("private")) {
            return false;
        }
        if (z2 && psiClass.hasModifierProperty("abstract")) {
            return false;
        }
        return psiClass.getContainingClass() == null || psiClass.hasModifierProperty("static");
    }

    public static Comparator<PsiClass> createScopeComparator(final GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(0);
        }
        Comparator<PsiClass> comparatorComparing = Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiClassUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PsiUtilCore.getVirtualFile((PsiClass) obj);
            }
        }, Comparator.nullsFirst(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiClassUtil$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return globalSearchScope.compare((VirtualFile) obj2, (VirtualFile) obj);
            }
        }));
        if (comparatorComparing == null) {
            $$$reportNull$$$0(1);
        }
        return comparatorComparing;
    }
}
