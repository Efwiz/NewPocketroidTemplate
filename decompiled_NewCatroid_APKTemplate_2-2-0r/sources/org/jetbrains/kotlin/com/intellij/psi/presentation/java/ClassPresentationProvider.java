package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.navigation.ColoredItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider;
import org.jetbrains.kotlin.com.intellij.openapi.editor.colors.CodeInsightColors;
import org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public class ClassPresentationProvider implements ItemPresentationProvider<PsiClass> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiClass", "org/jetbrains/kotlin/com/intellij/psi/presentation/java/ClassPresentationProvider", "getPresentation"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider
    public ItemPresentation getPresentation(final PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        return new ColoredItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.ClassPresentationProvider.1
            public String getPresentableText() {
                return ClassPresentationUtil.getNameForClass(psiClass, false);
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                PsiFile containingFile = psiClass.getContainingFile();
                if (!(containingFile instanceof PsiClassOwner)) {
                    return null;
                }
                String packageName = ((PsiClassOwner) containingFile).getPackageName();
                if (packageName.isEmpty()) {
                    return null;
                }
                return "(" + packageName + Constant.AFTER_QUTO;
            }

            public TextAttributesKey getTextAttributesKey() {
                try {
                    if (psiClass.isDeprecated()) {
                        return CodeInsightColors.DEPRECATED_ATTRIBUTES;
                    }
                    return null;
                } catch (IndexNotReadyException unused) {
                    return null;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return psiClass.getIcon(3);
            }
        };
    }
}
