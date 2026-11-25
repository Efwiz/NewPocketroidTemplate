package org.jetbrains.kotlin.com.intellij.psi.scope;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DelegatingScopeProcessor implements PsiScopeProcessor {
    private final PsiScopeProcessor myDelegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/DelegatingScopeProcessor";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3) {
            objArr[0] = "state";
        } else if (i == 4) {
            objArr[0] = "hintKey";
        } else if (i != 5) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "event";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/DelegatingScopeProcessor";
        } else {
            objArr[1] = "getDelegate";
        }
        if (i != 1) {
            if (i == 2 || i == 3) {
                objArr[2] = "execute";
            } else if (i == 4) {
                objArr[2] = "getHint";
            } else if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "handleEvent";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public DelegatingScopeProcessor(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(0);
        }
        this.myDelegate = psiScopeProcessor;
    }

    protected final PsiScopeProcessor getDelegate() {
        PsiScopeProcessor psiScopeProcessor = this.myDelegate;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(1);
        }
        return psiScopeProcessor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        return this.myDelegate.execute(psiElement, resolveState);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        return (T) this.myDelegate.getHint(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(5);
        }
        this.myDelegate.handleEvent(event, obj);
    }
}
