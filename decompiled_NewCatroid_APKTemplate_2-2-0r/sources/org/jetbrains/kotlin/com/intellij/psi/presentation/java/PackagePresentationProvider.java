package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.navigation.ColoredItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider;
import org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PackagePresentationProvider implements ItemPresentationProvider<PsiPackage> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "aPackage", "org/jetbrains/kotlin/com/intellij/psi/presentation/java/PackagePresentationProvider", "getPresentation"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider
    public ItemPresentation getPresentation(final PsiPackage psiPackage) {
        if (psiPackage == null) {
            $$$reportNull$$$0(0);
        }
        return new ColoredItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.PackagePresentationProvider.1
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }

            public String getPresentableText() {
                return psiPackage.getName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                return psiPackage.getQualifiedName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return PlatformIcons.PACKAGE_ICON;
            }
        };
    }
}
