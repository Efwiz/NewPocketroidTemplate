package org.jetbrains.kotlin.com.intellij.psi.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ParameterizedCachedValueProvider<ResultType, ParameterType> {
    CachedValueProvider.Result<ResultType> compute(ParameterType parametertype);
}
