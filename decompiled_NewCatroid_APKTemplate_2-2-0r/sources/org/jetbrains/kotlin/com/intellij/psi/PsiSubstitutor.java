package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyWithDefaultValue;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiSubstitutor {
    public static final PsiSubstitutor EMPTY;
    public static final Key<PsiSubstitutor> KEY;
    public static final PsiSubstitutor UNKNOWN;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor";
        } else {
            objArr[0] = "map";
        }
        if (i == 1) {
            objArr[1] = "putAll";
        } else if (i == 2 || i == 3) {
            objArr[1] = "createSubstitutor";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor";
        }
        if (i != 1 && i != 2 && i != 3) {
            objArr[2] = "putAll";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    void ensureValid();

    Map<PsiTypeParameter, PsiType> getSubstitutionMap();

    boolean isValid();

    PsiSubstitutor put(PsiTypeParameter psiTypeParameter, PsiType psiType);

    PsiSubstitutor putAll(PsiClass psiClass, PsiType[] psiTypeArr);

    PsiSubstitutor putAll(PsiSubstitutor psiSubstitutor);

    PsiType substitute(PsiType psiType);

    PsiType substitute(PsiTypeParameter psiTypeParameter);

    PsiType substituteWithBoundsPromotion(PsiTypeParameter psiTypeParameter);

    static {
        EmptySubstitutor emptySubstitutor = EmptySubstitutor.getInstance();
        EMPTY = emptySubstitutor;
        KEY = KeyWithDefaultValue.create("SUBSTITUTOR", emptySubstitutor);
        UNKNOWN = emptySubstitutor;
    }

    default PsiSubstitutor putAll(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        PsiSubstitutor psiSubstitutorPutAll = putAll(createSubstitutor(map));
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(1);
        }
        return psiSubstitutorPutAll;
    }

    static PsiSubstitutor createSubstitutor(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null || map.isEmpty()) {
            PsiSubstitutor psiSubstitutor = EMPTY;
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(2);
            }
            return psiSubstitutor;
        }
        PsiSubstitutor psiSubstitutorPutAll = EMPTY.putAll(map);
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(3);
        }
        return psiSubstitutorPutAll;
    }
}
