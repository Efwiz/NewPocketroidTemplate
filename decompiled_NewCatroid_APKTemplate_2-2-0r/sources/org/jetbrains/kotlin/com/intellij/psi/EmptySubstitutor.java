package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class EmptySubstitutor implements PsiSubstitutor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 8 || i == 5 || i == 6 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 8 || i == 5 || i == 6 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "classParameter";
                break;
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/EmptySubstitutor";
                break;
            case 4:
                objArr[0] = "parentClass";
                break;
            case 7:
                objArr[0] = "another";
                break;
            case 9:
                objArr[0] = "map";
                break;
            default:
                objArr[0] = "typeParameter";
                break;
        }
        if (i == 3) {
            objArr[1] = "put";
        } else if (i == 8 || i == 5 || i == 6 || i == 10) {
            objArr[1] = "putAll";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/EmptySubstitutor";
        } else {
            objArr[1] = "getSubstitutionMap";
        }
        switch (i) {
            case 1:
                objArr[2] = "substituteWithBoundsPromotion";
                break;
            case 2:
                objArr[2] = "put";
                break;
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
                break;
            case 4:
            case 7:
            case 9:
                objArr[2] = "putAll";
                break;
            default:
                objArr[2] = "substitute";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 8 && i != 5 && i != 6 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public void ensureValid() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public boolean isValid() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substitute(PsiType psiType) {
        return psiType;
    }

    public static EmptySubstitutor getInstance() {
        return Holder.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substitute(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        return JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiTypeParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiType substituteWithBoundsPromotion(PsiTypeParameter psiTypeParameter) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(1);
        }
        return JavaPsiFacade.getElementFactory(psiTypeParameter.getProject()).createType(psiTypeParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor put(PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(2);
        }
        if (psiType != null) {
            PsiUtil.ensureValidType(psiType);
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = PsiSubstitutorFactory.getInstance().createSubstitutor(psiTypeParameter, psiType);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(PsiClass psiClass, PsiType[] psiTypeArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (!psiClass.hasTypeParameters()) {
            return this;
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = PsiSubstitutorFactory.getInstance().createSubstitutor(psiClass, psiTypeArr);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(6);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(8);
        }
        return psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public PsiSubstitutor putAll(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(9);
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = map.isEmpty() ? EMPTY : PsiSubstitutorFactory.getInstance().createSubstitutor(map);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(10);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor
    public Map<PsiTypeParameter, PsiType> getSubstitutionMap() {
        Map<PsiTypeParameter, PsiType> mapEmptyMap = Collections.emptyMap();
        if (mapEmptyMap == null) {
            $$$reportNull$$$0(11);
        }
        return mapEmptyMap;
    }

    public String toString() {
        return "EmptySubstitutor";
    }

    private static class Holder {
        private static final EmptySubstitutor INSTANCE = new EmptySubstitutor();

        private Holder() {
        }
    }
}
