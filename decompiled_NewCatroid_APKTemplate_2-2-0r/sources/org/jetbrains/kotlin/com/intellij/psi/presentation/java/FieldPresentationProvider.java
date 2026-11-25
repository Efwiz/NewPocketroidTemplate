package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;

/* JADX INFO: loaded from: classes3.dex */
public class FieldPresentationProvider implements ItemPresentationProvider<PsiField> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "item", "org/jetbrains/kotlin/com/intellij/psi/presentation/java/FieldPresentationProvider", "getPresentation"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider
    public ItemPresentation getPresentation(PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(0);
        }
        return JavaPresentationUtil.getFieldPresentation(psiField);
    }
}
