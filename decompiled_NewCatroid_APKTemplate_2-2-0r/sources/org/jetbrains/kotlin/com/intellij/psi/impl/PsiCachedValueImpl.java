package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiCachedValueImpl<T> extends PsiCachedValue<T> implements CachedValue<T> {
    private final CachedValueProvider<T> myProvider;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else if (i != 4) {
            objArr[0] = "manager";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValueImpl";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValueImpl";
        } else {
            objArr[1] = "getValueProvider";
        }
        if (i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValue
    public /* bridge */ /* synthetic */ Getter getUpToDateOrNull() {
        return super.getUpToDateOrNull();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiCachedValueImpl(PsiManager psiManager, CachedValueProvider<T> cachedValueProvider) {
        this(psiManager, cachedValueProvider, false);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiCachedValueImpl(PsiManager psiManager, CachedValueProvider<T> cachedValueProvider, boolean z) {
        super(psiManager, z);
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(3);
        }
        this.myProvider = cachedValueProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValue
    public T getValue() {
        return getValueWithLock(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    public CachedValueProvider<T> getValueProvider() {
        CachedValueProvider<T> cachedValueProvider = this.myProvider;
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(4);
        }
        return cachedValueProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    /* JADX INFO: renamed from: doCompute */
    public <P> CachedValueProvider.Result<T> m6701x9c9e63ff(P p) {
        return this.myProvider.compute();
    }
}
