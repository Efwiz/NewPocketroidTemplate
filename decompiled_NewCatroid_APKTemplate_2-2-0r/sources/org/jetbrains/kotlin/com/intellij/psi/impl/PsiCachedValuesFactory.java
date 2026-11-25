package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValueProvider;
import org.jetbrains.kotlin.com.intellij.util.CachedValuesFactory;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiCachedValuesFactory implements CachedValuesFactory {
    private final PsiManager myManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "project";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValuesFactory";
        if (i == 1) {
            objArr[2] = "createCachedValue";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "createParameterizedCachedValue";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiCachedValuesFactory(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = PsiManager.getInstance(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValuesFactory
    public <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider, boolean z) {
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiCachedValueImpl(this.myManager, cachedValueProvider, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValuesFactory
    public <T, P> ParameterizedCachedValue<T, P> createParameterizedCachedValue(ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z) {
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiParameterizedCachedValue(this.myManager, parameterizedCachedValueProvider, z);
    }
}
