package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiNamePatternCondition<T extends PsiElement> extends PropertyPatternCondition<T, String> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "org/jetbrains/kotlin/com/intellij/patterns/PsiNamePatternCondition", "getPropertyValue"));
    }

    public ElementPattern<String> getNamePattern() {
        return getValuePattern();
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.PropertyPatternCondition
    public String getPropertyValue(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        if (obj instanceof PsiNamedElement) {
            return ((PsiNamedElement) obj).getName();
        }
        return null;
    }
}
