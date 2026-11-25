package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValueProvider;

/* JADX INFO: loaded from: classes3.dex */
public interface CachedValuesFactory {
    <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider, boolean z);

    <T, P> ParameterizedCachedValue<T, P> createParameterizedCachedValue(ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z);
}
