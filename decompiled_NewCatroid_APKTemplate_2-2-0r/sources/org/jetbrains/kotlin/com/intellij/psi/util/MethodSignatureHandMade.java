package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;

/* JADX INFO: loaded from: classes3.dex */
public class MethodSignatureHandMade extends MethodSignatureBase {
    private final boolean myIsConstructor;
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "substitutor";
        } else if (i == 4) {
            objArr[0] = "parameterTypes";
        } else if (i == 5) {
            objArr[0] = "typeParameters";
        } else if (i != 6) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureHandMade";
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureHandMade";
        } else {
            objArr[1] = "getName";
        }
        if (i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MethodSignatureHandMade(String str, PsiParameterList psiParameterList, PsiTypeParameterList psiTypeParameterList, PsiSubstitutor psiSubstitutor, boolean z) {
        super(psiSubstitutor, psiParameterList, psiTypeParameterList);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myName = str;
        this.myIsConstructor = z;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MethodSignatureHandMade(String str, PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr, PsiSubstitutor psiSubstitutor, boolean z) {
        super(psiSubstitutor, psiTypeArr, psiTypeParameterArr);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(5);
        }
        this.myName = str;
        this.myIsConstructor = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public boolean isRaw() {
        for (PsiTypeParameter psiTypeParameter : this.myTypeParameters) {
            if (getSubstitutor().substitute(psiTypeParameter) == null) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public boolean isConstructor() {
        return this.myIsConstructor;
    }
}
