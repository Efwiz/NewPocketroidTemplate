package org.jetbrains.kotlin.com.intellij.core;

import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;

/* JADX INFO: loaded from: classes3.dex */
public class CoreLanguageLevelProjectExtension extends LanguageLevelProjectExtension {
    private LanguageLevel myLanguageLevel = LanguageLevel.HIGHEST;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreLanguageLevelProjectExtension";
        } else {
            objArr[0] = "languageLevel";
        }
        if (i != 1) {
            objArr[1] = "getLanguageLevel";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreLanguageLevelProjectExtension";
        }
        if (i == 1) {
            objArr[2] = "setLanguageLevel";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension
    public LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel = this.myLanguageLevel;
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
        return languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension
    public void setLanguageLevel(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        this.myLanguageLevel = languageLevel;
    }
}
