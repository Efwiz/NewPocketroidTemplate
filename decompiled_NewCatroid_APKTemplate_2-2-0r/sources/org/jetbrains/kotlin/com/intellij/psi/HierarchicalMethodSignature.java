package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;

/* JADX INFO: loaded from: classes3.dex */
public abstract class HierarchicalMethodSignature extends MethodSignatureBackedByPsiMethod {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature";
        } else {
            objArr[0] = "signature";
        }
        if (i == 1) {
            objArr[1] = "getParameterTypes";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature";
        } else {
            objArr[1] = "getInaccessibleSuperSignatures";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract List<HierarchicalMethodSignature> getSuperSignatures();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HierarchicalMethodSignature(MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod) {
        super(methodSignatureBackedByPsiMethod.getMethod(), methodSignatureBackedByPsiMethod.getSubstitutor(), methodSignatureBackedByPsiMethod.isRaw(), getParameterTypes(methodSignatureBackedByPsiMethod.getMethod()), methodSignatureBackedByPsiMethod.getTypeParameters());
        if (methodSignatureBackedByPsiMethod == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static PsiType[] getParameterTypes(PsiMethod psiMethod) {
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(parameters.length);
        for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
            psiTypeArrCreateArray[i] = parameters[i].getType();
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeArrCreateArray;
    }

    public List<HierarchicalMethodSignature> getInaccessibleSuperSignatures() {
        List<HierarchicalMethodSignature> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }
}
