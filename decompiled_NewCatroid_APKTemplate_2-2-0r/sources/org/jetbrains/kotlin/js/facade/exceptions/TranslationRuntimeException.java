package org.jetbrains.kotlin.js.facade.exceptions;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: loaded from: classes5.dex */
public class TranslationRuntimeException extends RuntimeException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/js/facade/exceptions/TranslationRuntimeException", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TranslationRuntimeException(PsiElement psiElement, Throwable th) {
        super("Unexpected error occurred compiling the following fragment: " + PsiUtilsKt.getTextWithLocation(psiElement), th);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
    }
}
