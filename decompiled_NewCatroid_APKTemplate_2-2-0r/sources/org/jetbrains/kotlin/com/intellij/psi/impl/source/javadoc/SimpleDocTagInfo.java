package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
class SimpleDocTagInfo implements JavadocTagInfo {
    private final Class[] myContexts;
    private final boolean myInline;
    private final LanguageLevel myLanguageLevel;
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = FirebaseAnalytics.Param.LEVEL;
        } else if (i != 2) {
            objArr[0] = "name";
        } else {
            objArr[0] = "contexts";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/SimpleDocTagInfo";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    SimpleDocTagInfo(String str, LanguageLevel languageLevel, boolean z, Class... clsArr) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myName = str;
        this.myContexts = clsArr;
        this.myInline = z;
        this.myLanguageLevel = languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return this.myInline;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        if (psiElement != null && PsiUtil.getLanguageLevel(psiElement).compareTo(this.myLanguageLevel) < 0) {
            return false;
        }
        for (Class cls : this.myContexts) {
            if (cls.isInstance(psiElement)) {
                return true;
            }
        }
        return false;
    }
}
