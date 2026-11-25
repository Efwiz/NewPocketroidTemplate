package org.jetbrains.kotlin.com.intellij.psi.util;

/* JADX INFO: loaded from: classes3.dex */
public interface ParameterizedCachedValue<T, P> {
    T getValue(P p);

    ParameterizedCachedValueProvider<T, P> getValueProvider();

    boolean hasUpToDateValue();
}
