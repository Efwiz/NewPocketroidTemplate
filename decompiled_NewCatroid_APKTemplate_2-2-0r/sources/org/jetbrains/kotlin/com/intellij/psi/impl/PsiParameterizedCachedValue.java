package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValueProvider;

/* JADX INFO: loaded from: classes3.dex */
public class PsiParameterizedCachedValue<T, P> extends PsiCachedValue<T> implements ParameterizedCachedValue<T, P> {
    private final ParameterizedCachedValueProvider<T, P> myProvider;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else if (i != 2) {
            objArr[0] = "manager";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiParameterizedCachedValue";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiParameterizedCachedValue";
        } else {
            objArr[1] = "getValueProvider";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiParameterizedCachedValue(PsiManager psiManager, ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z) {
        super(psiManager, z);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.myProvider = parameterizedCachedValueProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue
    public T getValue(P p) {
        return getValueWithLock(p);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    public ParameterizedCachedValueProvider<T, P> getValueProvider() {
        ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider = this.myProvider;
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(2);
        }
        return parameterizedCachedValueProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    /* JADX INFO: renamed from: doCompute */
    public <X> CachedValueProvider.Result<T> m6701x9c9e63ff(X x) {
        return this.myProvider.compute(x);
    }
}
