package org.jetbrains.kotlin.com.intellij.psi.scope;

import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ProcessorWithHints implements PsiScopeProcessor {
    private final Map<Key<?>, Object> myHints = new HashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "hint";
        } else if (i != 2) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "hintKey";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/ProcessorWithHints";
        if (i != 2) {
            objArr[2] = "hint";
        } else {
            objArr[2] = "getHint";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected final <H> void hint(Key<H> key, H h) {
        if (key == null) {
            $$$reportNull$$$0(0);
        }
        if (h == null) {
            $$$reportNull$$$0(1);
        }
        this.myHints.put(key, h);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        return (T) this.myHints.get(key);
    }
}
