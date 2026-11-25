package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;

/* JADX INFO: loaded from: classes3.dex */
public interface CachedValue<T> {
    Getter<T> getUpToDateOrNull();

    T getValue();

    CachedValueProvider<T> getValueProvider();

    boolean hasUpToDateValue();
}
