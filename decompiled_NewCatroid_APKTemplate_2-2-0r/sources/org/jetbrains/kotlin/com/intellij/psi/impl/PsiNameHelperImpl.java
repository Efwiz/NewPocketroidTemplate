package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;

/* JADX INFO: loaded from: classes3.dex */
public class PsiNameHelperImpl extends PsiNameHelper {
    private final LanguageLevelProjectExtension myLanguageLevelExtension;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiNameHelperImpl";
        } else {
            objArr[0] = "languageLevel";
        }
        if (i != 1) {
            objArr[1] = "getLanguageLevel";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiNameHelperImpl";
        }
        if (i == 1) {
            objArr[2] = "isIdentifier";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    public PsiNameHelperImpl(Project project) {
        this.myLanguageLevelExtension = LanguageLevelProjectExtension.getInstance(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper
    public boolean isIdentifier(String str) {
        return isIdentifier(str, getLanguageLevel());
    }

    protected LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel = this.myLanguageLevelExtension.getLanguageLevel();
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
        return languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper
    public boolean isIdentifier(String str, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        return (str == null || !StringUtil.isJavaIdentifier(str) || JavaLexer.isKeyword(str, languageLevel)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper
    public boolean isKeyword(String str) {
        return str != null && JavaLexer.isKeyword(str, getLanguageLevel());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper
    public boolean isQualifiedName(String str) {
        if (str == null) {
            return false;
        }
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(46, i);
            if (iIndexOf < 0) {
                iIndexOf = str.length();
            }
            if (!isIdentifier(str.substring(i, iIndexOf))) {
                return false;
            }
            if (iIndexOf == str.length()) {
                return true;
            }
            i = iIndexOf + 1;
        }
    }

    public static PsiNameHelper getInstance() {
        return new PsiNameHelperImpl() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiNameHelperImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/PsiNameHelperImpl$1", "getLanguageLevel"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiNameHelperImpl
            protected LanguageLevel getLanguageLevel() {
                LanguageLevel languageLevel = LanguageLevel.HIGHEST;
                if (languageLevel == null) {
                    $$$reportNull$$$0(0);
                }
                return languageLevel;
            }
        };
    }

    private PsiNameHelperImpl() {
        this.myLanguageLevelExtension = null;
    }
}
