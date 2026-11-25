package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public class VariablePresentationProvider<T extends PsiVariable & NavigationItem> implements ItemPresentationProvider<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "variable", "org/jetbrains/kotlin/com/intellij/psi/presentation/java/VariablePresentationProvider", "getPresentation"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider
    public ItemPresentation getPresentation(final T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return new ItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.VariablePresentationProvider.1
            public String getPresentableText() {
                return t.getName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                return "";
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return t.getIcon(0);
            }
        };
    }
}
