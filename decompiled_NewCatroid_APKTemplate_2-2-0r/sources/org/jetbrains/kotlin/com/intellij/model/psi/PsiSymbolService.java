package org.jetbrains.kotlin.com.intellij.model.psi;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiSymbolService {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/model/psi/PsiSymbolService", "getInstance"));
    }

    PsiSymbolReference asSymbolReference(PsiReference psiReference);

    static PsiSymbolService getInstance() {
        PsiSymbolService psiSymbolService = (PsiSymbolService) ApplicationManager.getApplication().getService(PsiSymbolService.class);
        if (psiSymbolService == null) {
            $$$reportNull$$$0(0);
        }
        return psiSymbolService;
    }
}
