package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiScopeProcessor {

    public interface Event {
        public static final Event SET_DECLARATION_HOLDER = new Event() { // from class: org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor.Event.1
        };
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "hintKey";
        } else {
            objArr[0] = "event";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor";
        if (i != 1) {
            objArr[2] = "getHint";
        } else {
            objArr[2] = "handleEvent";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    boolean execute(PsiElement psiElement, ResolveState resolveState);

    default <T> T getHint(Key<T> key) {
        if (key != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    default void handleEvent(Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(1);
        }
    }
}
