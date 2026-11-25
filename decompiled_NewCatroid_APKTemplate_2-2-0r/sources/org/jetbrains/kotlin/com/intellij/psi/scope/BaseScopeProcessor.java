package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public abstract class BaseScopeProcessor implements PsiScopeProcessor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "hintKey", "org/jetbrains/kotlin/com/intellij/psi/scope/BaseScopeProcessor", "getHint"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }
}
