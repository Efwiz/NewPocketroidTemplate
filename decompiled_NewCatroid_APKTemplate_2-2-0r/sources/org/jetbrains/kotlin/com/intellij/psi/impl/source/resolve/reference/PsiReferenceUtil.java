package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ImmutableList;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiReferenceUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/PsiReferenceUtil";
        } else {
            objArr[0] = "maybeMultiReference";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "unwrapMultiReference";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/PsiReferenceUtil";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "unwrapMultiReference";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private PsiReferenceUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends PsiReference> T findReferenceOfClass(PsiReference psiReference, Class<T> cls) {
        if (cls.isInstance(psiReference)) {
            return psiReference;
        }
        if (!(psiReference instanceof PsiMultiReference)) {
            return null;
        }
        for (PsiReference psiReference2 : ((PsiMultiReference) psiReference).getReferences()) {
            T t = (T) psiReference2;
            if (cls.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    public static List<PsiReference> unwrapMultiReference(PsiReference psiReference) {
        if (psiReference == null) {
            $$$reportNull$$$0(0);
        }
        if (psiReference instanceof PsiMultiReference) {
            ImmutableList immutableList = ContainerUtil.immutableList(((PsiMultiReference) psiReference).getReferences());
            if (immutableList == null) {
                $$$reportNull$$$0(1);
            }
            return immutableList;
        }
        ImmutableList immutableListImmutableSingletonList = ContainerUtil.immutableSingletonList(psiReference);
        if (immutableListImmutableSingletonList == null) {
            $$$reportNull$$$0(2);
        }
        return immutableListImmutableSingletonList;
    }
}
