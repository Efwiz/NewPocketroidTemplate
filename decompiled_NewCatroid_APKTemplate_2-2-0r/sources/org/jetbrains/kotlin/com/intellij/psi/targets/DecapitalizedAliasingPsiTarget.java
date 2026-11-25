package org.jetbrains.kotlin.com.intellij.psi.targets;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;

/* JADX INFO: loaded from: classes3.dex */
public class DecapitalizedAliasingPsiTarget extends AliasingPsiTarget {
    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "psiTargetName";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/targets/DecapitalizedAliasingPsiTarget";
        } else if (i == 3) {
            objArr[0] = "aliasName";
        } else if (i != 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/targets/DecapitalizedAliasingPsiTarget";
        }
        if (i == 2) {
            objArr[1] = "getNameAlias";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/targets/DecapitalizedAliasingPsiTarget";
        } else {
            objArr[1] = "getTargetName";
        }
        if (i == 1) {
            objArr[2] = "getNameAlias";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "getTargetName";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DecapitalizedAliasingPsiTarget(PsiNamedElement psiNamedElement) {
        super(psiNamedElement);
        if (psiNamedElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.targets.AliasingPsiTarget
    public String getNameAlias(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        String strDecapitalize = StringUtil.decapitalize(str);
        if (strDecapitalize == null) {
            $$$reportNull$$$0(2);
        }
        return strDecapitalize;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.targets.AliasingPsiTarget
    public String getTargetName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        String strCapitalize = StringUtil.capitalize(str);
        if (strCapitalize == null) {
            $$$reportNull$$$0(4);
        }
        return strCapitalize;
    }
}
