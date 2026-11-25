package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ResolveVariableUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "ref", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveVariableUtil", "resolveVariable"));
    }

    public static PsiVariable resolveVariable(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, boolean[] zArr, boolean[] zArr2) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        VariableResolverProcessor variableResolverProcessor = new VariableResolverProcessor(psiJavaCodeReferenceElement, psiJavaCodeReferenceElement.getContainingFile());
        PsiScopesUtil.resolveAndWalk(variableResolverProcessor, psiJavaCodeReferenceElement, null);
        JavaResolveResult[] result = variableResolverProcessor.getResult();
        if (result.length != 1) {
            return null;
        }
        PsiVariable psiVariable = (PsiVariable) result[0].getElement();
        if (zArr != null) {
            zArr[0] = !result[0].isAccessible();
        }
        if (zArr2 != null) {
            zArr2[0] = !result[0].isStaticsScopeCorrect();
        }
        return psiVariable;
    }
}
