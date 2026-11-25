package org.jetbrains.kotlin.com.intellij.psi.templateLanguages;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface TemplateLanguageFileViewProvider extends FileViewProvider {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "language", "org/jetbrains/kotlin/com/intellij/psi/templateLanguages/TemplateLanguageFileViewProvider", "getContentElementType"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    Language getBaseLanguage();

    default IElementType getContentElementType(Language language) {
        if (language != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    Language getTemplateDataLanguage();
}
