package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public interface ProviderBinding {
    void addAcceptableReferenceProviders(PsiElement psiElement, List<? super ProviderInfo<ProcessingContext>> list, PsiReferenceService.Hints hints);

    void unregisterProvider(PsiReferenceProvider psiReferenceProvider);

    public static class ProviderInfo<Context> {
        public final double priority;
        public final Context processingContext;
        public final PsiReferenceProvider provider;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = Constants.EXTRA_PROVIDER;
            } else {
                objArr[0] = "processingContext";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ProviderBinding$ProviderInfo";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public ProviderInfo(PsiReferenceProvider psiReferenceProvider, Context context, double d) {
            if (psiReferenceProvider == null) {
                $$$reportNull$$$0(0);
            }
            if (context == null) {
                $$$reportNull$$$0(1);
            }
            this.provider = psiReferenceProvider;
            this.processingContext = context;
            this.priority = d;
        }

        public String toString() {
            return "ProviderInfo{provider=" + this.provider + ", priority=" + this.priority + '}';
        }
    }
}
